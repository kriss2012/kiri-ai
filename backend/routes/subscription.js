const express = require('express');
const router = express.Router();
const Razorpay = require('razorpay');
const crypto = require('crypto');
const { protect } = require('../middleware/auth');
const User = require('../models/User');

const razorpay = new Razorpay({
  key_id: process.env.RAZORPAY_KEY_ID,
  key_secret: process.env.RAZORPAY_KEY_SECRET,
});

const PLANS = {
  free: { name: 'Free', price: 0, requests: 50 },
  premium_monthly: { name: 'Premium Monthly', price: 149, requests: -1 },
  premium_yearly: { name: 'Premium Yearly', price: 1500, requests: -1 }
};

// @POST /api/subscription/create-order
// @DESC Create a Razorpay order for a plan
router.post('/create-order', protect, async (req, res) => {
  try {
    const { plan } = req.body;

    if (!PLANS[plan] || plan === 'free') {
      return res.status(400).json({ success: false, message: 'Invalid plan selected.' });
    }

    const amount = PLANS[plan].price * 100; // Razorpay expects amount in paise
    const options = {
      amount: amount,
      currency: 'INR',
      receipt: `rcpt_${Date.now()}`,
      notes: {
        userId: req.user._id.toString(),
        plan: plan
      }
    };

    const order = await razorpay.orders.create(options);

    res.json({
      success: true,
      orderId: order.id,
      amount: order.amount,
      currency: order.currency,
      keyId: process.env.RAZORPAY_KEY_ID
    });
  } catch (error) {
    console.error('Razorpay Order Error:', error);
    res.status(500).json({ success: false, message: 'Failed to create subscription order.' });
  }
});

// @POST /api/subscription/verify-payment
// @DESC Verify Razorpay payment and activate subscription
router.post('/verify-payment', protect, async (req, res) => {
  try {
    const { razorpay_order_id, razorpay_payment_id, razorpay_signature, plan } = req.body;

    const generated_signature = crypto
      .createHmac('sha256', process.env.RAZORPAY_KEY_SECRET)
      .update(razorpay_order_id + '|' + razorpay_payment_id)
      .digest('hex');

    if (generated_signature !== razorpay_signature) {
      return res.status(400).json({ success: false, message: 'Invalid payment signature.' });
    }

    // Payment is verified, update user subscription
    const user = await User.findById(req.user._id);

    user.plan = plan;
    user.subscriptionStatus = 'active';
    user.subscriptionStartDate = new Date();

    const endDate = new Date();
    if (plan === 'premium_monthly') {
      endDate.setMonth(endDate.getMonth() + 1);
    } else if (plan === 'premium_yearly') {
      endDate.setFullYear(endDate.getFullYear() + 1);
    }
    user.subscriptionEndDate = endDate;

    user.razorpayOrderId = razorpay_order_id;
    user.razorpayPaymentId = razorpay_payment_id;
    user.razorpaySignature = razorpay_signature;

    await user.save();

    res.json({
      success: true,
      message: 'Subscription activated successfully!',
      user: {
        plan: user.plan,
        subscriptionStatus: user.subscriptionStatus,
        subscriptionEndDate: user.subscriptionEndDate
      }
    });
  } catch (error) {
    console.error('Razorpay Verification Error:', error);
    res.status(500).json({ success: false, message: 'Payment verification failed.' });
  }
});

// @GET /api/subscription/status
// @DESC Get current user subscription status
router.get('/status', protect, async (req, res) => {
  try {
    const user = await User.findById(req.user._id);
    res.json({
      success: true,
      plan: user.plan,
      status: user.subscriptionStatus,
      endDate: user.subscriptionEndDate,
      isPremium: user.isPremium()
    });
  } catch (error) {
    res.status(500).json({ success: false, message: 'Failed to fetch subscription status.' });
  }
});

// @POST /api/subscription/webhook
// @DESC Razorpay Webhook to handle payment events
router.post('/webhook', async (req, res) => {
  const secret = process.env.RAZORPAY_WEBHOOK_SECRET;
  const signature = req.headers['x-razorpay-signature'];

  try {
    const expectedSignature = crypto
      .createHmac('sha256', secret)
      .update(JSON.stringify(req.body))
      .digest('hex');

    if (signature !== expectedSignature) {
      return res.status(400).json({ success: false, message: 'Invalid webhook signature' });
    }

    const event = req.body.event;
    const payload = req.body.payload.payment.entity;

    if (event === 'payment.captured') {
      const userId = payload.notes.userId;
      const plan = payload.notes.plan;

      const user = await User.findById(userId);
      if (user && user.plan === 'free') {
        user.plan = plan;
        user.subscriptionStatus = 'active';
        user.subscriptionStartDate = new Date();

        const endDate = new Date();
        if (plan === 'premium_monthly') endDate.setMonth(endDate.getMonth() + 1);
        else if (plan === 'premium_yearly') endDate.setFullYear(endDate.getFullYear() + 1);

        user.subscriptionEndDate = endDate;
        user.razorpayPaymentId = payload.id;
        user.razorpayOrderId = payload.order_id;
        await user.save();
      }
    }

    res.json({ status: 'ok' });
  } catch (error) {
    console.error('Webhook Error:', error);
    res.status(500).json({ success: false });
  }
});

module.exports = router;
