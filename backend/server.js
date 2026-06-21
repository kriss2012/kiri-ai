require('dotenv').config();
const express = require('express');
const cors = require('cors');
const helmet = require('helmet');
const morgan = require('morgan');
const mongoose = require('mongoose');
const rateLimit = require('express-rate-limit');

const os = require('os');

const app = express();

let lastCpuUsage = os.cpus();
let lastCpuTime = Date.now();


// Required for Render/Heroku/etc. behind a reverse proxy
app.set('trust proxy', 1);

app.use(helmet({ contentSecurityPolicy: false }));
if (process.env.NODE_ENV !== 'test') app.use(morgan('dev'));

app.use(cors({
  origin: function (origin, callback) {
    if (!origin) return callback(null, true); // Allow requests with no origin (like mobile apps or curl)
    return callback(null, true); // During development, allow all origins
  },
  credentials: true,
  methods: ['GET', 'POST', 'PUT', 'DELETE', 'OPTIONS'],
  allowedHeaders: ['Content-Type', 'Authorization']
}));

const globalLimiter = rateLimit({
  windowMs: 15 * 60 * 1000,
  max: 200,
  message: { success: false, message: 'Too many requests, please try again later.' }
});
app.use('/api', globalLimiter);

const authLimiter = rateLimit({
  windowMs: 15 * 60 * 1000,
  max: 20,
  message: { success: false, message: 'Too many auth attempts, please try again later.' }
});

app.use('/api/subscription/webhook', express.raw({ type: 'application/json' }));
app.use(express.json({ limit: '10mb' }));
app.use(express.urlencoded({ extended: true, limit: '10mb' }));

mongoose.connect(process.env.MONGODB_URI || 'mongodb://localhost:27017/kiri-ai', {
  useNewUrlParser: true,
  useUnifiedTopology: true
}).then(() => {
  console.log('✅ MongoDB connected');
}).catch(err => {
  console.error('❌ MongoDB connection error:', err.message);
  process.exit(1);
});

app.use('/api/auth', authLimiter, require('./routes/auth'));
app.use('/api/chat', require('./routes/chat'));
app.use('/api/image', require('./routes/image'));
app.use('/api/subscription', require('./routes/subscription'));
// Keep-alive/ping endpoint for cron jobs to prevent Render cold starts
app.get('/ping', (req, res) => {
  res.status(200).json({
    success: true,
    message: 'pong',
    timestamp: new Date().toISOString()
  });
});

app.get('/api/stats', (req, res) => {
  try {
    const currentCpuUsage = os.cpus();
    const currentTime = Date.now();
    let totalDiff = 0;
    let idleDiff = 0;

    for (let i = 0; i < currentCpuUsage.length; i++) {
      const last = lastCpuUsage[i];
      const curr = currentCpuUsage[i];
      if (!last || !curr) continue;
      const lastTotal = Object.values(last.times).reduce((a, b) => a + b, 0);
      const currTotal = Object.values(curr.times).reduce((a, b) => a + b, 0);
      totalDiff += (currTotal - lastTotal);
      idleDiff += (curr.times.idle - last.times.idle);
    }

    lastCpuUsage = currentCpuUsage;
    lastCpuTime = currentTime;

    const cpu_percent = totalDiff === 0 ? 0 : Math.round(100 - (100 * idleDiff / totalDiff));
    
    const totalMem = os.totalmem();
    const freeMem = os.freemem();
    const ram_percent = Math.round(((totalMem - freeMem) / totalMem) * 100);

    res.json({
      success: true,
      cpu_percent,
      ram_percent
    });
  } catch (err) {
    res.status(500).json({ success: false, error: err.message });
  }
});

app.get('/api/health', async (req, res) => {
  try {
    const dbStatus = mongoose.connection.readyState === 1 ? 'connected' : 'disconnected';
    res.json({
      success: true,
      status: 'Kiri-AI Backend is running 🚀',
      timestamp: new Date().toISOString(),
      services: {
        database: dbStatus,
        auth: 'active',
        chat: 'active',
        subscription: 'active'
      }
    });
  } catch (err) {
    res.status(500).json({ success: false, error: 'Partial service failure' });
  }
});

app.use('*', (req, res) => {
  res.status(404).json({ success: false, message: 'Route not found.' });
});

app.use((err, req, res, next) => {
  console.error('Global error:', err);
  res.status(err.status || 500).json({
    success: false,
    message: err.message || 'Internal server error.'
  });
});

const PORT = process.env.PORT || 5000;
app.listen(PORT, () => {
  console.log(`🚀 Kiri-AI Server running on port ${PORT}`);
});

module.exports = app;
