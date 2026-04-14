const mongoose = require('mongoose');
const bcrypt = require('bcryptjs');

const UserSchema = new mongoose.Schema({
  name: {
    type: String,
    required: [true, 'Name is required'],
    trim: true,
    maxlength: [50, 'Name cannot exceed 50 characters']
  },
  email: {
    type: String,
    required: [true, 'Email is required'],
    unique: true,
    lowercase: true,
    match: [/^\w+([.-]?\w+)*@\w+([.-]?\w+)*(\.\w{2,3})+$/, 'Invalid email']
  },
  password: {
    type: String,
    required: [true, 'Password is required'],
    minlength: [6, 'Password must be at least 6 characters'],
    select: false
  },
  avatar: {
    type: String,
    default: null
  },
  isVerified: {
    type: Boolean,
    default: false
  },
  verificationToken: String,
  resetPasswordToken: String,
  resetPasswordExpire: Date,

  // Subscription
  plan: {
    type: String,
    enum: ['free', 'premium_monthly', 'premium_yearly'],
    default: 'free'
  },
  razorpayCustomerId: String,
  razorpayOrderId: String,
  razorpayPaymentId: String,
  razorpaySignature: String,
  subscriptionStatus: {
    type: String,
    enum: ['none', 'active', 'cancelled', 'past_due'],
    default: 'none'
  },
  subscriptionStartDate: Date,
  subscriptionEndDate: Date,

  // Usage tracking
  dailyRequests: {
    type: Number,
    default: 0
  },
  lastRequestDate: {
    type: Date,
    default: null
  },
  totalRequests: {
    type: Number,
    default: 0
  },

  // Chat history reference
  createdAt: {
    type: Date,
    default: Date.now
  },
  updatedAt: {
    type: Date,
    default: Date.now
  }
});

// Hash password before save
UserSchema.pre('save', async function (next) {
  if (!this.isModified('password')) return next();
  const salt = await bcrypt.genSalt(12);
  this.password = await bcrypt.hash(this.password, salt);
  this.updatedAt = Date.now();
  next();
});

// Compare password
UserSchema.methods.comparePassword = async function (candidatePassword) {
  return await bcrypt.compare(candidatePassword, this.password);
};

// Check if user can make request
UserSchema.methods.canMakeRequest = function () {
  const now = new Date();
  const today = new Date(now.getFullYear(), now.getMonth(), now.getDate());

  // Use a temporary variable to check the limit without mutating the DB object here
  const effectiveDailyRequests = (!this.lastRequestDate || new Date(this.lastRequestDate) < today)
    ? 0 : this.dailyRequests;

  // Premium users have unlimited requests
  if (this.isPremium()) return true;

  // Free users: 50 requests per day
  return effectiveDailyRequests < parseInt(process.env.FREE_DAILY_REQUESTS || 50);
};

// Check if premium
UserSchema.methods.isPremium = function () {
  if (this.plan === 'free') return false;
  if (!this.subscriptionEndDate) return false;
  return new Date() < new Date(this.subscriptionEndDate) && this.subscriptionStatus === 'active';
};

// Increment request count
UserSchema.methods.incrementRequest = async function () {
  const now = new Date();
  const today = new Date(now.getFullYear(), now.getMonth(), now.getDate());

  if (!this.lastRequestDate || new Date(this.lastRequestDate) < today) {
    this.dailyRequests = 1;
  } else {
    this.dailyRequests += 1;
  }

  this.lastRequestDate = now;
  this.totalRequests += 1;
  await this.save();
};

module.exports = mongoose.model('User', UserSchema);
