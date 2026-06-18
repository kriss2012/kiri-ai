/**
 * Kiri-AI Smart Model Router
 * Logic for selecting the optimal AI model based on query content and user tier.
 */

const MODELS = {
  GENERAL: 'google/gemini-3.5-flash',
  COMPLEX: 'anthropic/claude-sonnet-4',
  CODE: 'meta-llama/llama-3.1-70b-instruct',
  SPEED: 'google/gemini-3.1-flash-lite',
  REASONING: 'openai/gpt-4o-mini'
};

/**
 * Maps outdated/deprecated model slugs to their current equivalents.
 */
const mapModel = (model) => {
  if (!model || model === 'auto') return MODELS.GENERAL;
  const mapping = {
    'google/gemini-2.0-flash-001': MODELS.GENERAL,
    'google/gemini-2.0-flash-lite-001': MODELS.SPEED,
    'anthropic/claude-3.5-sonnet': MODELS.COMPLEX
  };
  return mapping[model] || model;
};


/**
 * Routes a query to the most appropriate model.
 * @param {string} query - The user's input message.
 * @param {string} userTier - The user's subscription tier ('free', 'pro', 'enterprise').
 * @returns {string} The model slug to use.
 */
const routeModel = (query, userTier = 'free') => {
  const lowerQuery = query.toLowerCase();

  // 1. Coding Tasks
  const codeTriggers = ['javascript', 'python', 'java', 'react', 'code', 'function', 'bug', 'debug', 'script'];
  if (codeTriggers.some(t => lowerQuery.includes(t))) {
    return userTier === 'free' ? MODELS.CODE : 'meta-llama/llama-3.1-405b-instruct';
  }

  // 2. Complex Reasoning / Long Form
  const reasoningTriggers = ['explain', 'analyze', 'compare', 'evaluate', 'summary of', 'comprehensive'];
  if (reasoningTriggers.some(t => lowerQuery.includes(t)) || query.length > 500) {
    return userTier === 'free' ? MODELS.GENERAL : MODELS.COMPLEX;
  }

  // 3. Image Generation Intent (Handled by a different service, but router can flag it)
  const imageTriggers = ['generate image', 'draw', 'create a picture', 'visualize'];
  if (imageTriggers.some(t => lowerQuery.includes(t))) {
    // We'll handle this in the controller to switch to ImageService
    return 'IMAGE_GENERATION_PENDING';
  }

  // Default to General
  return MODELS.GENERAL;
};

module.exports = {
  routeModel,
  mapModel,
  MODELS
};
