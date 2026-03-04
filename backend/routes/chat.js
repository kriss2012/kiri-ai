const express = require('express');
const router = express.Router();
const { GoogleGenerativeAI } = require('@google/generative-ai');
const { protect, checkRequestLimit } = require('../middleware/auth');
const Conversation = require('../models/Conversation');

const genAI = new GoogleGenerativeAI(process.env.GEMINI_API_KEY);

// @POST /api/chat/message - Send message to Gemini
router.post('/message', protect, checkRequestLimit, async (req, res) => {
  try {
    const { message, conversationId, model = 'gemini-1.5-flash' } = req.body;
    // Sanitize conversationId - treat 'undefined' string as null
    const safeConvId = conversationId && conversationId !== 'undefined' && conversationId !== 'null' ? conversationId : null;

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
      if (!conversation) {
        // If not found, create a new one instead of erroring
        conversation = new Conversation({ user: req.user._id, model, messages: [] });
      }
    } else {
      conversation = new Conversation({
        user: req.user._id,
        model,
        messages: []
      });
    }

    // Build chat history for Gemini
    const history = conversation.messages.map(msg => ({
      role: msg.role === 'assistant' ? 'model' : 'user',
      parts: [{ text: msg.content }]
    }));

    // Initialize Gemini model
    const geminiModel = genAI.getGenerativeModel({ model: model || 'gemini-1.5-pro' });

    // Prepare message parts (text + optional attachments)
    const { attachments } = req.body; // Array of { data: base64, mimeType: string }
    const messageParts = [{ text: message }];

    if (attachments && Array.isArray(attachments)) {
      attachments.forEach(file => {
        if (file.data && file.mimeType) {
          messageParts.push({
            inlineData: {
              data: file.data,
              mimeType: file.mimeType
            }
          });
        }
      });
    }

    // Start chat with history
    const chat = geminiModel.startChat({
      history,
      generationConfig: {
        maxOutputTokens: 2048,
        temperature: 0.7,
        topP: 0.8,
        topK: 40
      }
    });

    // Send message and get response
    const result = await chat.sendMessage(messageParts);
    const response = await result.response;
    const assistantMessage = response.text();

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

    // Get updated request count
    const updatedUser = req.user;

    res.json({
      success: true,
      message: assistantMessage,
      conversationId: conversation._id,
      title: conversation.title,
      requestsUsed: updatedUser.dailyRequests,
      requestsRemaining: updatedUser.isPremium()
        ? 'unlimited'
        : Math.max(0, parseInt(process.env.FREE_DAILY_REQUESTS || 50) - updatedUser.dailyRequests)
    });

  } catch (error) {
    const fs = require('fs');
    fs.appendFileSync('error.log', `\n[${new Date().toISOString()}] Chat error: ${error.stack || error.message}\n`);
    console.error('Chat error:', error);
    if (error.message && error.message.includes('API_KEY')) {
      return res.status(500).json({ success: false, message: 'AI service configuration error.' });
    }
    if (error.message && error.message.includes('SAFETY')) {
      return res.status(400).json({ success: false, message: 'Message blocked by safety filters. Please rephrase.' });
    }
    res.status(500).json({ success: false, message: 'AI Error: ' + (error.message || 'Unknown error') });
  }
});

// @POST /api/chat/stream - Streaming response
router.post('/stream', protect, checkRequestLimit, async (req, res) => {
  try {
    const { message, conversationId, model = 'gemini-1.5-flash' } = req.body;

    if (!message || !message.trim()) {
      return res.status(400).json({ success: false, message: 'Message is required.' });
    }

    // Set SSE headers
    res.setHeader('Content-Type', 'text/event-stream');
    res.setHeader('Cache-Control', 'no-cache');
    res.setHeader('Connection', 'keep-alive');
    res.setHeader('Access-Control-Allow-Origin', process.env.FRONTEND_URL || '*');

    let conversation;

    if (conversationId) {
      conversation = await Conversation.findOne({ _id: conversationId, user: req.user._id });
      if (!conversation) {
        res.write(`data: ${JSON.stringify({ error: 'Conversation not found' })}\n\n`);
        return res.end();
      }
    } else {
      conversation = new Conversation({ user: req.user._id, model, messages: [] });
    }

    const history = conversation.messages.map(msg => ({
      role: msg.role === 'assistant' ? 'model' : 'user',
      parts: [{ text: msg.content }]
    }));

    const geminiModel = genAI.getGenerativeModel({ model: model || 'gemini-1.5-flash' });
    const chat = geminiModel.startChat({
      history,
      generationConfig: { maxOutputTokens: 2048, temperature: 0.7 }
    });

    let fullResponse = '';

    const result = await chat.sendMessageStream(message);

    for await (const chunk of result.stream) {
      const chunkText = chunk.text();
      fullResponse += chunkText;
      res.write(`data: ${JSON.stringify({ chunk: chunkText, done: false })}\n\n`);
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
    res.write(`data: ${JSON.stringify({ error: 'Stream failed. Please try again.' })}\n\n`);
    res.end();
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
      lastMessage: c.messages.length > 0 ? c.messages[c.messages.length - 1].content.substring(0, 100) : '',
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
