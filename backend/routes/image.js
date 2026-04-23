const express = require('express');
const router = express.Router();
const OpenAI = require('openai');
const { protect, checkRequestLimit } = require('../middleware/auth');

const getOpenAIClient = () => {
  const apiKey = process.env.OPENROUTER_API_KEY;
  if (!apiKey) throw new Error('OpenRouter API Key not configured.');
  return new OpenAI({
    apiKey: apiKey,
    baseURL: 'https://openrouter.ai/api/v1',
  });
};

// @POST /api/image/generate - Generate image using DALL-E 3 or similar via OpenRouter
router.post('/generate', protect, checkRequestLimit, async (req, res) => {
  try {
    const { prompt, size = '1024x1024', model = 'openai/dall-e-3' } = req.body;

    if (!prompt) {
      return res.status(400).json({ success: false, message: 'Prompt is required.' });
    }

    const openai = getOpenAIClient();

    // OpenRouter uses the standard OpenAI images.generate format for some models, 
    // but for others it might be via chat completions if they are multimodal.
    // However, DALL-E 3 is usually a specific endpoint.
    
    // Note: OpenRouter's support for images.generate might vary. 
    // If it doesn't support the direct images endpoint, we might need a workaround.
    // Assuming it follows OpenAI SDK compatibility:
    
    const response = await openai.images.generate({
      model: model,
      prompt: prompt,
      n: 1,
      size: size,
    });

    const imageUrl = response.data[0].url;

    // Increment user request (maybe count image as 5 requests?)
    await req.user.incrementRequest();

    res.json({
      success: true,
      imageUrl,
      revisedPrompt: response.data[0].revised_prompt || prompt
    });

  } catch (error) {
    console.error('Image Gen Error:', error);
    res.status(500).json({ success: false, message: 'Image Generation Failed: ' + error.message });
  }
});

module.exports = router;
