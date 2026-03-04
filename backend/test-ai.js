require('dotenv').config();
const { GoogleGenerativeAI } = require('@google/generative-ai');

async function test() {
    const apiKey = process.env.GEMINI_API_KEY;
    if (!apiKey) {
        console.error('❌ GEMINI_API_KEY is missing in environment!');
        return;
    }
    console.log('✅ Found API Key (starts with: ' + apiKey.substring(0, 4) + '...)');

    try {
        const genAI = new GoogleGenerativeAI(apiKey);
        const model = genAI.getGenerativeModel({ model: 'gemini-1.5-flash' });

        console.log('⏳ Testing gemini-1.5-flash...');
        const result = await model.generateContent('Hello, are you working?');
        const response = await result.response;
        const text = response.text();
        console.log('✅ AI Response:', text);
    } catch (err) {
        console.error('❌ AI Test Failed:', err.message);
        if (err.stack) console.error(err.stack);
    }
}

test();
