# Kiri AI 🤖
**A full-stack AI chat application powered by Google Gemini**

---

## 🗂 Project Structure
```
kiri-ai/
├── backend/
│   ├── server.js          ← Express API server
│   └── package.json
└── frontend/
    ├── index.html         ← Complete single-file frontend
    └── kiri-intro.html    ← 4K Logo intro animation
├── android-app/           ← Capacitor Android project
└── ...
```

---

## 🚀 Quick Setup

### 1. Backend

```bash
cd backend
npm install
```

Create a `.env` file:
```env
GEMINI_API_KEY=your_google_gemini_api_key_here
JWT_SECRET=your_random_secret_key_here_make_it_long
PORT=3001
```

Start the server:
```bash
npm start
# or for development with auto-reload:
npm run dev
```

### 2. Frontend

Open `frontend/index.html` in a browser **or** serve it:
```bash
# Using Python
cd frontend && python3 -m http.server 8080

# Using Node.js (npx)
cd frontend && npx serve .
```

Then visit `http://localhost:8080`

---

## 🎨 Branding & Animations

### Kiri AI Intro Animation
Located at `frontend/kiri-intro.html`. This is a high-fidelity 4K-ready CSS/JS animation for app splash screens or video intros.

**How to export as Video:**
1. Open `frontend/kiri-intro.html` in Chrome.
2. Press `F11` for full screen.
3. Record with OBS Studio (3840x2160, 60fps).
4. Use the resulting `.mp4` for your App Store preview or splash screen.

---

## 🔑 Get Your Gemini API Key

1. Go to https://aistudio.google.com/app/apikey
2. Click **Create API Key**
3. Copy and paste it into your `.env` file

---

## 💰 Pricing Plans

| Plan    | Price    | Daily Limit  |
|---------|----------|--------------|
| Free    | ₹0       | 50 requests  |
| Premium | ₹149/mo  | Unlimited    |
| Yearly  | ₹1500/yr | Unlimited    |

---

## 🔐 API Endpoints

| Method | Route              | Auth | Description          |
|--------|--------------------|------|----------------------|
| POST   | /api/auth/register | No   | Create account       |
| POST   | /api/auth/login    | No   | Login                |
| GET    | /api/auth/me       | Yes  | Get current user     |
| POST   | /api/chat          | Yes  | Send message to AI   |
| GET    | /api/plans         | No   | Get available plans  |
| POST   | /api/subscribe     | Yes  | Upgrade subscription |
| GET    | /api/usage         | Yes  | Check daily usage    |
| PUT    | /api/profile       | Yes  | Update profile       |
| GET    | /api/health        | No   | Health check         |

---

## 🌐 Deployment

### Backend (Railway / Render / Fly.io)

```bash
# Render: connect GitHub repo, set env vars in dashboard
# Railway: railway up
# Fly.io: fly deploy
```

### Frontend (Netlify / Vercel / GitHub Pages)

1. Upload `frontend/index.html`
2. Update the `API` constant at top of script to your deployed backend URL:
```js
const API = 'https://your-backend.railway.app/api';
```

### Production Database
Replace the in-memory `Map()` storage in `server.js` with:
- **MongoDB** (mongoose) — recommended
- **PostgreSQL** (prisma or pg)
- **Supabase** — easiest for quick deployment

---

## ✨ Features

- 🔐 JWT Authentication (register/login)
- 💬 Real-time chat with Gemini AI
- 📊 Daily usage tracking per user
- 💳 Subscription plans (Free/Premium/Yearly)
- 📱 Fully responsive mobile UI
- 🎨 Beautiful dark theme with Kiri branding
- 📝 Markdown rendering with syntax highlighting
- 💾 Local chat history persistence
- ⌨️ Keyboard shortcuts (Ctrl+K = New Chat)

---

## 🔧 Customization

- Change colors in CSS `:root` variables
- Change AI personality in the `systemInstruction` in `server.js`
- Add payment gateway (Razorpay/Stripe) in `/api/subscribe`
- Add MongoDB persistence for production

---

## 🔒 Security Notes

- Change `JWT_SECRET` to a long random string in production
- Add rate limiting (already included via `express-rate-limit`)
- Use HTTPS in production
- Store API keys in environment variables only, never in code

---

*Built with Express.js + Google Gemini + Vanilla JS*
