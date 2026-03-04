const mongoose = require('mongoose');

const MessageSchema = new mongoose.Schema({
  role: {
    type: String,
    enum: ['user', 'assistant'],
    required: true
  },
  content: {
    type: String,
    required: true
  },
  timestamp: {
    type: Date,
    default: Date.now
  }
});

const ConversationSchema = new mongoose.Schema({
  user: {
    type: mongoose.Schema.Types.ObjectId,
    ref: 'User',
    required: true
  },
  title: {
    type: String,
    default: 'New Conversation',
    maxlength: 100
  },
  messages: [MessageSchema],
  model: {
    type: String,
    default: 'google/gemini-2.0-flash-001'
  },
  isArchived: {
    type: Boolean,
    default: false
  },
  isPinned: {
    type: Boolean,
    default: false
  },
  createdAt: {
    type: Date,
    default: Date.now
  },
  updatedAt: {
    type: Date,
    default: Date.now
  }
});

// Auto-generate title from first message
ConversationSchema.methods.generateTitle = function () {
  if (this.messages.length > 0) {
    const firstMsg = this.messages[0].content;
    this.title = firstMsg.length > 60
      ? firstMsg.substring(0, 60) + '...'
      : firstMsg;
  }
};

ConversationSchema.pre('save', function (next) {
  this.updatedAt = Date.now();
  next();
});

module.exports = mongoose.model('Conversation', ConversationSchema);
