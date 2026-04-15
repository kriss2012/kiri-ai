const express = require('express');
const router = express.Router();
const OpenAI = require('openai');
const mongoose = require('mongoose');
const { protect, checkRequestLimit } = require('../middleware/auth');
const Conversation = require('../models/Conversation');
const multer = require('multer');
const upload = multer({
  limits: { fileSize: 10 * 1024 * 1024 }, // 10MB limit
  storage: multer.memoryStorage()
});

// Safe OpenAI initialization to prevent crash on startup if key is missing
const getOpenAIClient = () => {
  const apiKey = process.env.OPENROUTER_API_KEY;
  if (!apiKey) {
    console.error('❌ CRITICAL: OPENROUTER_API_KEY is missing in environment variables!');
    throw new Error('OpenRouter API Key is not configured on the server.');
  }
  return new OpenAI({
    apiKey: apiKey,
    baseURL: 'https://openrouter.ai/api/v1',
    defaultHeaders: {
      'HTTP-Referer': 'https://kiri-ai.com',
      'X-Title': 'Kiri-AI',
    }
  });
};

// Helper to sanitize conversationId
const sanitizeConvId = (id) =>
  id && id !== 'undefined' && id !== 'null' && mongoose.Types.ObjectId.isValid(id) ? id : null;

// @POST /api/chat/message - Send message to Gemini
router.post('/message', protect, checkRequestLimit, async (req, res) => {
  try {
    const { message, conversationId, model = 'google/gemini-2.0-flash-001' } = req.body;
    const safeConvId = sanitizeConvId(conversationId);

    if (!message || !message.trim()) {
      return res.status(400).json({ success: false, message: 'Message is required.' });
    }

    let conversation;

    // Get or create conversation
    if (safeConvId) {
      conversation = await Conversation.findOne({
        _id: safeConvId,
        user: req.user._id
      });
    }

    if (!conversation) {
      conversation = new Conversation({
        user: req.user._id,
        model,
        messages: []
      });
    }

    // Build chat history for OpenAI format
    const history = conversation.messages.map(msg => ({
      role: msg.role === 'assistant' ? 'assistant' : 'user',
      content: msg.content
    }));

    // Add current message to history
    history.push({ role: 'user', content: message });

    // Get OpenAI client safely
    const openai = getOpenAIClient();

    // Send message and get response from OpenRouter
    const completion = await openai.chat.completions.create({
      model: model || 'google/gemini-2.0-flash-001',
      messages: history,
      temperature: 0.7,
      max_tokens: 2048
    });

    const assistantMessage = completion.choices[0].message.content;

    // Save messages to conversation
    conversation.messages.push({ role: 'user', content: message });
    conversation.messages.push({ role: 'assistant', content: assistantMessage });

    // Auto-generate title for new conversations
    if (conversation.messages.length === 2) {
      conversation.generateTitle();
    }

    await conversation.save();

    // Increment user request count
    await req.user.incrementRequest();

    res.json({
      success: true,
      message: assistantMessage,
      conversationId: conversation._id,
      title: conversation.title,
      requestsUsed: req.user.dailyRequests,
      requestsRemaining: req.user.isPremium()
        ? 'unlimited'
        : Math.max(0, parseInt(process.env.FREE_DAILY_REQUESTS || 50) - req.user.dailyRequests)
    });

  } catch (error) {
    console.error('Chat error:', error);
    res.status(500).json({ success: false, message: 'AI Error: ' + (error.message || 'Unknown error') });
  }
});

// @POST /api/chat/message/upload - Send message with file to Gemini
router.post('/message/upload', protect, checkRequestLimit, upload.single('file'), async (req, res) => {
  try {
    const { content, conversationId, model = 'google/gemini-2.0-flash-001' } = req.body;
    const safeConvId = sanitizeConvId(conversationId);
    const file = req.file;

    if (!file) {
      return res.status(400).json({ success: false, message: 'File is required.' });
    }

    let conversation;
    if (safeConvId) {
      conversation = await Conversation.findOne({ _id: safeConvId, user: req.user._id });
    }

    if (!conversation) {
      conversation = new Conversation({
        user: req.user._id,
        model,
        messages: []
      });
    }

    // Prepare message content for OpenAI/OpenRouter (Gemini supports multimodal via base64)
    const base64Image = file.buffer.toString('base64');
    const mimeType = file.mimetype;

    const userMessageContent = [
      { type: 'text', text: content || 'Analyze this image.' },
      {
        type: 'image_url',
        image_url: {
          url: `data:${mimeType};base64,${base64Image}`
        }
      }
    ];

    // Build chat history
    const history = conversation.messages.map(msg => ({
      role: msg.role === 'assistant' ? 'assistant' : 'user',
      content: msg.content
    }));

    // Add current multimodal message
    history.push({ role: 'user', content: userMessageContent });

    const openai = getOpenAIClient();

    const completion = await openai.chat.completions.create({
      model: model || 'google/gemini-2.0-flash-001',
      messages: history,
      temperature: 0.7,
      max_tokens: 2048
    });

    const assistantMessage = completion.choices[0].message.content;

    // Save messages to conversation. Store local reference for display.
    // Note: We store the text part + a placeholder in the DB for simplicity,
    // as storing massive base64 in MongoDB isn't ideal for large-scale.
    // The Android app looks for [IMAGE_URI: ...] which we'll simulate.
    const textContent = content || 'Analyze this image.';
    const savedUserContent = textContent + `\n[IMAGE_ATTACHMENT: ${file.originalname}]`;

    conversation.messages.push({ role: 'user', content: savedUserContent });
    conversation.messages.push({ role: 'assistant', content: assistantMessage });

    if (conversation.messages.length === 2) {
      conversation.generateTitle();
    }

    await conversation.save();
    await req.user.incrementRequest();

    res.json({
      success: true,
      message: assistantMessage,
      conversationId: conversation._id,
      title: conversation.title,
      requestsUsed: req.user.dailyRequests,
      requestsRemaining: req.user.isPremium()
        ? 'unlimited'
        : Math.max(0, parseInt(process.env.FREE_DAILY_REQUESTS || 50) - req.user.dailyRequests)
    });

  } catch (error) {
    console.error('Upload error:', error);
    res.status(500).json({ success: false, message: 'AI Upload Error: ' + (error.message || 'Unknown error') });
  }
});

// @POST /api/chat/stream - Streaming response
router.post('/stream', protect, checkRequestLimit, async (req, res) => {
  try {
    const { message, conversationId, model = 'google/gemini-2.0-flash-001' } = req.body;
    const safeConvId = sanitizeConvId(conversationId);

    if (!message || !message.trim()) {
      return res.status(400).json({ success: false, message: 'Message is required.' });
    }

    // Initialize OpenAI BEFORE setting SSE headers
    let openai;
    try {
      openai = getOpenAIClient();
    } catch (e) {
      return res.status(500).json({ success: false, message: 'AI service not configured.' });
    }

    // Set SSE headers
    res.setHeader('Content-Type', 'text/event-stream');
    res.setHeader('Cache-Control', 'no-cache');
    res.setHeader('Connection', 'keep-alive');
    res.setHeader('Access-Control-Allow-Origin', process.env.FRONTEND_URL || '*');

    let conversation;
    if (safeConvId) {
      conversation = await Conversation.findOne({ _id: safeConvId, user: req.user._id });
    }

    if (!conversation) {
      conversation = new Conversation({ user: req.user._id, model, messages: [] });
    }

    const history = conversation.messages.map(msg => ({
      role: msg.role === 'assistant' ? 'assistant' : 'user',
      content: msg.content
    }));
    history.push({ role: 'user', content: message });

    let fullResponse = '';
    const stream = await openai.chat.completions.create({
      model: model || 'google/gemini-2.0-flash-001',
      messages: history,
      stream: true,
      max_tokens: 2048
    });

    for await (const chunk of stream) {
      const chunkText = chunk.choices[0]?.delta?.content || '';
      if (chunkText) {
        fullResponse += chunkText;
        res.write(`data: ${JSON.stringify({ chunk: chunkText, done: false })}\n\n`);
      }
    }

    // Save to DB
    conversation.messages.push({ role: 'user', content: message });
    conversation.messages.push({ role: 'assistant', content: fullResponse });
    if (conversation.messages.length === 2) conversation.generateTitle();
    await conversation.save();
    await req.user.incrementRequest();

    res.write(`data: ${JSON.stringify({
      done: true,
      conversationId: conversation._id,
      title: conversation.title,
      requestsUsed: req.user.dailyRequests
    })}\n\n`);

    res.end();

  } catch (error) {
    console.error('Stream error:', error);
    try {
      res.write(`data: ${JSON.stringify({ error: 'Stream failed. Please try again.', done: true })}\n\n`);
      res.end();
    } catch (_) {}
  }
});

// @GET /api/chat/conversations - List user conversations
router.get('/conversations', protect, async (req, res) => {
  try {
    const { page = 1, limit = 20, archived = false } = req.query;

    const conversations = await Conversation.find({
      user: req.user._id,
      isArchived: archived === 'true'
    })
      .select('title model isPinned createdAt updatedAt messages')
      .sort({ isPinned: -1, updatedAt: -1 })
      .limit(limit * 1)
      .skip((page - 1) * limit)
      .lean();

    const formatted = conversations.map(c => ({
      id: c._id,
      title: c.title,
      model: c.model,
      isPinned: c.isPinned,
      messageCount: c.messages.length,
      lastMessage: c.messages.length > 0 ? (c.messages[c.messages.length - 1].content || '').substring(0, 100) : '',
      createdAt: c.createdAt,
      updatedAt: c.updatedAt
    }));

    res.json({ success: true, conversations: formatted });
  } catch (error) {
    res.status(500).json({ success: false, message: 'Failed to fetch conversations.' });
  }
});

// @GET /api/chat/conversations/:id - Get specific conversation
router.get('/conversations/:id', protect, async (req, res) => {
  try {
    const conversation = await Conversation.findOne({
      _id: req.params.id,
      user: req.user._id
    });

    if (!conversation) {
      return res.status(404).json({ success: false, message: 'Conversation not found.' });
    }

    res.json({ success: true, conversation });
  } catch (error) {
    res.status(500).json({ success: false, message: 'Failed to fetch conversation.' });
  }
});

// @DELETE /api/chat/conversations/:id
router.delete('/conversations/:id', protect, async (req, res) => {
  try {
    await Conversation.findOneAndDelete({ _id: req.params.id, user: req.user._id });
    res.json({ success: true, message: 'Conversation deleted.' });
  } catch (error) {
    res.status(500).json({ success: false, message: 'Failed to delete conversation.' });
  }
});

// @PUT /api/chat/conversations/:id/pin
router.put('/conversations/:id/pin', protect, async (req, res) => {
  try {
    const conversation = await Conversation.findOne({ _id: req.params.id, user: req.user._id });
    if (!conversation) return res.status(404).json({ success: false, message: 'Not found.' });

    conversation.isPinned = !conversation.isPinned;
    await conversation.save();

    res.json({ success: true, isPinned: conversation.isPinned });
  } catch (error) {
    res.status(500).json({ success: false, message: 'Failed to pin conversation.' });
  }
});

// @DELETE /api/chat/conversations - Delete all
router.delete('/conversations', protect, async (req, res) => {
  try {
    await Conversation.deleteMany({ user: req.user._id });
    res.json({ success: true, message: 'All conversations deleted.' });
  } catch (error) {
    res.status(500).json({ success: false, message: 'Failed to delete conversations.' });
  }
});

module.exports = router;
