import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { Check, ArrowLeft } from 'lucide-react';
import { useAuth } from '../context/AuthContext';
import { KiriButton, KiriStatus } from '../components/Shared/KiriUI';
import api from '../utils/api';
import './Pricing.css';

const PRICING_PLANS = [
  {
    id: 'premium_monthly',
    name: 'PREMIUM // MONTHLY',
    price: '₹149',
    period: '/MONTH',
    specs: 'UNLIMITED_REASONING_CAPACITY',
    features: [
      'UNLIMITED DAILY REQUESTS',
      'PRIORITY RESPONSE SPEED',
      'DEEP REASONING MODELS',
      'ADVANCED IMAGE GENERATION',
      'EARLY ACCESS TO NEW FEATURES'
    ]
  },
  {
    id: 'premium_yearly',
    name: 'PREMIUM // YEARLY',
    price: '₹1500',
    period: '/YEAR',
    specs: 'OPTIMIZED_ANNUAL_COMMITMENT',
    features: [
      'ALL MONTHLY FEATURES',
      '2 MONTHS FREE INCLUDED',
      'PRIORITY SUPPORT',
      'BETA PROGRAM ACCESS'
    ],
    highlight: true
  }
];

const PricingPage = () => {
  const navigate = useNavigate();
  const { user } = useAuth();
  const [loading, setLoading] = useState(false);
  const [status, setStatus] = useState({ message: '', type: 'info' });

  useEffect(() => {
    // Load Razorpay Script
    const script = document.createElement('script');
    script.src = 'https://checkout.razorpay.com/v1/checkout.js';
    script.async = true;
    document.body.appendChild(script);
  }, []);

  const handleSubscribe = async (planId) => {
    setLoading(true);
    try {
      const res = await api.post('/subscription/create-order', { plan: planId });
      if (res.data.success) {
        const options = {
          key: res.data.keyId,
          amount: res.data.amount,
          currency: res.data.currency,
          name: 'Kiri AI',
          description: `Subscription: ${planId}`,
          order_id: res.data.orderId,
          handler: async (response) => {
            try {
              const verifyRes = await api.post('/subscription/verify-payment', {
                ...response,
                plan: planId
              });
              if (verifyRes.data.success) {
                setStatus({ message: 'DATA_SYNC_COMPLETE: Subscription Activated.', type: 'success' });
                setTimeout(() => navigate('/'), 2000);
              }
            } catch (err) {
              setStatus({ message: 'VERIFICATION_ERROR: Payment sync failed.', type: 'error' });
            }
          },
          prefill: {
            name: user?.name,
            email: user?.email
          },
          theme: {
            color: '#050505'
          }
        };

        const rzp = new window.Razorpay(options);
        rzp.open();
      }
    } catch (err) {
      console.error('Subscription error', err);
      setStatus({ message: 'SYSTEM_ERROR: Could not initialize payment.', type: 'error' });
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="pricing-page cinematic-fade">
      <header className="pricing-header">
        <button className="back-btn" onClick={() => window.history.back()}>
          <ArrowLeft size={20} />
        </button>
        <div className="mono-label mono">SUBSCRIPTION_CORE</div>
      </header>

      <div className="pricing-title-area">
        <h1 className="mono">Unlock Full Power</h1>
        <p>Advance to elite multimodal intelligence. Bypass all daily limits.</p>
      </div>

      <KiriStatus message={status.message} type={status.type} />

      <div className="plans-grid">
        {PRICING_PLANS.map(plan => (
          <div key={plan.id} className={`plan-card ${plan.highlight ? 'highlight' : ''}`}>
            <div className="plan-name-badge mono">{plan.name}</div>
            <div className="plan-price">
              <span className="amount">{plan.price}</span>
              <span className="period mono">{plan.period}</span>
            </div>
            <div className="plan-specs mono">TECHNICAL_SPEC: {plan.specs}</div>
            
            <div className="features-list">
              {plan.features.map((feat, i) => (
                <div key={i} className="feature-item">
                  <Check size={14} className="check-icon" />
                  <span className="mono">{feat}</span>
                </div>
              ))}
            </div>

            <KiriButton 
              className="subscribe-btn" 
              onClick={() => handleSubscribe(plan.id)}
              disabled={loading || user?.plan === plan.id}
            >
              {user?.plan === plan.id ? 'CURRENT_PLAN' : 'INITIALIZE_UPGRADE'}
            </KiriButton>
          </div>
        ))}
      </div>
    </div>
  );
};

export default PricingPage;
