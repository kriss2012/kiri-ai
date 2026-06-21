import React, { useState, useEffect, useRef } from 'react';
import { useAuth } from '../../context/AuthContext';
import Sidebar from '../Sidebar/Sidebar';
import MessageBubble from './MessageBubble';
import ChatInput from './ChatInput';
import api from '../../utils/api';
import { Menu, Terminal, Sliders, Activity, Sparkles, Code2, Eye, Info } from 'lucide-react';
import { useTheme } from '../../context/ThemeContext';
import './Chat.css';

const ChatPage = () => {
  const { user } = useAuth();
  const { isDarkMode } = useTheme();
  
  // Layout States
  const [isSidebarOpen, setIsSidebarOpen] = useState(window.innerWidth > 1024);
  const [isSysOpen, setIsSysOpen] = useState(false);

  // Chat Data States
  const [conversations, setConversations] = useState([]);
  const [currentConvId, setCurrentConvId] = useState(null);
  const [messages, setMessages] = useState([]);
  const [isSending, setIsSending] = useState(false);
  const [isLoading, setIsLoading] = useState(false);
  const [title, setTitle] = useState('Kiri AI');

  // Input Configuration States
  const [model, setModel] = useState('auto');
  const [temperature, setTemperature] = useState(0.7);
  const [systemInstructionInput, setSystemInstructionInput] = useState('');
  const [systemInstruction, setSystemInstruction] = useState('');
  const [logLevel, setLogLevel] = useState('INFO');

  // Live Hardware Stats States (simulated for professional local compute aesthetics)
  const [cpu, setCpu] = useState(18);
  const [ram, setRam] = useState(48);

  const messagesEndRef = useRef(null);

  // Handle window resizing
  useEffect(() => {
    const handleResize = () => {
      if (window.innerWidth > 1024) {
        setIsSidebarOpen(true);
      } else {
        setIsSidebarOpen(false);
      }
    };
    window.addEventListener('resize', handleResize);
    return () => window.removeEventListener('resize', handleResize);
  }, []);

  // Fetch conversations on load
  useEffect(() => {
    fetchConversations();
  }, []);

  // Scroll to bottom on new messages
  useEffect(() => {
    scrollToBottom();
  }, [messages, isSending]);

  // Poll real hardware stats from backend, with fallback to simulation
  useEffect(() => {
    const fetchStats = async () => {
      try {
        const res = await api.get('/stats');
        if (res.data.success) {
          setCpu(res.data.cpu_percent);
          setRam(res.data.ram_percent);
        }
      } catch (err) {
        // Fallback to simulation
        setCpu(prev => {
          const delta = Math.floor(Math.random() * 9) - 4; // -4% to +4%
          return Math.max(10, Math.min(85, prev + delta));
        });
        setRam(prev => {
          const delta = Math.floor(Math.random() * 3) - 1; // -1% to +1%
          return Math.max(40, Math.min(95, prev + delta));
        });
      }
    };

    fetchStats();
    const interval = setInterval(fetchStats, 4000);
    return () => clearInterval(interval);
  }, []);

  const scrollToBottom = () => {
    messagesEndRef.current?.scrollIntoView({ behavior: 'smooth' });
  };

  const fetchConversations = async () => {
    try {
      const res = await api.get('/chat/conversations');
      if (res.data.success) {
        setConversations(res.data.conversations);
      }
    } catch (err) {
      console.error('Fetch conversations failed', err);
    }
  };

  const selectConversation = async (id) => {
    try {
      setIsLoading(true);
      setCurrentConvId(id);
      const res = await api.get(`/chat/conversations/${id}`);
      if (res.data.success) {
        setMessages(res.data.conversation.messages || []);
        setTitle(res.data.conversation.title || 'Untitled chat');
        
        // Restore model selection if conversation has a saved model
        if (res.data.conversation.model) {
          setModel(res.data.conversation.model);
        }
      }
      if (window.innerWidth <= 1024) setIsSidebarOpen(false);
    } catch (err) {
      console.error('Select conversation failed', err);
    } finally {
      setIsLoading(false);
    }
  };

  const handleNewChat = () => {
    setCurrentConvId(null);
    setMessages([]);
    setTitle('Kiri AI');
    if (window.innerWidth <= 1024) setIsSidebarOpen(false);
  };

  const deleteConversation = async (id) => {
    try {
      const res = await api.delete(`/chat/conversations/${id}`);
      if (res.data.success) {
        setConversations(prev => prev.filter(c => c.id !== id));
        if (currentConvId === id) {
          handleNewChat();
        }
      }
    } catch (err) {
      console.error('Delete conversation failed', err);
    }
  };

  const clearAllConversations = async () => {
    const confirmClear = window.confirm("Are you sure you want to clear all chat histories?");
    if (!confirmClear) return;
    
    try {
      const res = await api.delete('/chat/conversations');
      if (res.data.success) {
        setConversations([]);
        handleNewChat();
      }
    } catch (err) {
      console.error('Clear conversations failed', err);
    }
  };

  const onSend = async (text, file) => {
    if (isSending) return;

    // Show user message immediately (optimistic update)
    const userMsg = {
      role: 'user',
      content: text + (file ? `\n[IMAGE_ATTACHMENT: ${file.name}]` : ''),
      id: Date.now().toString()
    };
    
    // If the file is an image, we can also simulate the image uri locally
    if (file && file.type.startsWith('image/')) {
      userMsg.content = text + `\n[IMAGE_URI: ${URL.createObjectURL(file)}]` + `\n[IMAGE_ATTACHMENT: ${file.name}]`;
    }

    setMessages(prev => [...prev, userMsg]);
    setIsSending(true);

    try {
      if (file) {
        const formData = new FormData();
        formData.append('file', file);
        formData.append('content', text);
        formData.append('model', model);
        formData.append('temperature', temperature.toString());
        if (systemInstruction) {
          formData.append('systemInstruction', systemInstruction);
        }
        if (currentConvId) {
          formData.append('conversationId', currentConvId);
        }

        const res = await api.post('/chat/message/upload', formData, {
          headers: { 'Content-Type': 'multipart/form-data' }
        });

        if (res.data.success) {
          const assistantMsg = {
            role: 'assistant',
            content: res.data.message,
            model: res.data.model,
            id: (Date.now() + 1).toString()
          };
          setMessages(prev => [...prev, assistantMsg]);
          if (!currentConvId) {
            setCurrentConvId(res.data.conversationId);
            setTitle(res.data.title || 'New Chat');
            fetchConversations();
          }
        }
      } else {
        const payload = {
          message: text,
          model,
          temperature,
          conversationId: currentConvId
        };
        if (systemInstruction) {
          payload.systemInstruction = systemInstruction;
        }

        const res = await api.post('/chat/message', payload);

        if (res.data.success) {
          const assistantMsg = {
            role: 'assistant',
            content: res.data.message,
            model: res.data.model,
            id: (Date.now() + 1).toString()
          };
          setMessages(prev => [...prev, assistantMsg]);
          if (!currentConvId) {
            setCurrentConvId(res.data.conversationId);
            setTitle(res.data.title || 'New Chat');
            fetchConversations();
          }
        }
      }
    } catch (err) {
      console.error('Send failed', err);
      // Append error message to chat log
      setMessages(prev => [
        ...prev,
        {
          role: 'assistant',
          content: '⚠️ Failed to receive response from AI engine. Please ensure backend services are active.',
          id: (Date.now() + 2).toString()
        }
      ]);
    } finally {
      setIsSending(false);
    }
  };

  const handleSaveInstructions = () => {
    setSystemInstruction(systemInstructionInput);
    setIsSysOpen(false);
  };

  const handleResetInstructions = () => {
    setSystemInstructionInput('');
    setSystemInstruction('');
    setIsSysOpen(false);
  };

  const handleSuggestionClick = (promptText) => {
    onSend(promptText, null);
  };

  const getHwColorClass = (value) => {
    if (value > 80) return 'danger';
    if (value > 60) return 'warn';
    return '';
  };

  return (
    <div className="app-container">
      <Sidebar 
        isOpen={isSidebarOpen} 
        onClose={() => setIsSidebarOpen(false)}
        conversations={conversations}
        currentId={currentConvId}
        onSelect={selectConversation}
        onNewChat={handleNewChat}
        onDelete={deleteConversation}
        onClearAll={clearAllConversations}
      />

      {isSidebarOpen && window.innerWidth <= 1024 && (
        <div className="sidebar-backdrop" onClick={() => setIsSidebarOpen(false)} />
      )}

      <div className="main-viewport">
        <header className="page-header">
          <button 
            className="menu-toggle" 
            onClick={() => setIsSidebarOpen(!isSidebarOpen)}
            aria-label="Toggle sidebar"
          >
            <Menu size={18} />
          </button>
          
          <div className="header-title">{title}</div>

          <div className="header-spacer"></div>

          <div className="header-actions">
            {/* System Prompt Toggle Button */}
            <button 
              className={`sys-prompt-toggle-btn ${systemInstruction ? 'has-global' : ''}`}
              onClick={() => setIsSysOpen(!isSysOpen)}
              title="System instructions"
              aria-label="Toggle system instructions panel"
            >
              <Terminal size={13} />
              <span>Instructions</span>
            </button>

            {/* Hardware usage mock stats */}
            <div className="hw-bar" title="Local AI Engine Stats">
              <div className="hw-stat">
                <span>CPU</span>
                <div className="hw-mini-track">
                  <div className="hw-mini-bar cpu" style={{ transform: `scaleX(${cpu / 100})` }}></div>
                </div>
                <span className={`hw-pct ${getHwColorClass(cpu)}`}>{cpu}%</span>
              </div>
              <div className="hw-stat">
                <span>RAM</span>
                <div className="hw-mini-track">
                  <div className="hw-mini-bar ram" style={{ transform: `scaleX(${ram / 100})` }}></div>
                </div>
                <span className={`hw-pct ${getHwColorClass(ram)}`}>{ram}%</span>
              </div>
            </div>
          </div>
        </header>

        {/* Slide-down System Instruction Panel */}
        {isSysOpen && (
          <div className="sys-panel" id="sys-panel">
            <div className="sys-row">
              <label htmlFor="sys-ta">Global System Instructions</label>
              <textarea 
                id="sys-ta" 
                className="sys-ta"
                placeholder="Instruct the AI model how to behave, format responses, or adopt specific personas..."
                value={systemInstructionInput}
                onChange={(e) => setSystemInstructionInput(e.target.value)}
              />
            </div>
            <div className="sys-divider"></div>
            <div className="sys-subrow">
              <select 
                className="sys-select" 
                value={logLevel} 
                onChange={(e) => setLogLevel(e.target.value)}
                aria-label="Select log level"
              >
                <option value="INFO">Log Level: INFO</option>
                <option value="DEBUG">Log Level: DEBUG</option>
                <option value="TRACE">Log Level: TRACE</option>
                <option value="NONE">Log Level: NONE</option>
              </select>
              <span className="sys-hint">Instructions are injected into prompt headers</span>
              <div style={{ marginLeft: 'auto', display: 'flex', gap: '8px' }}>
                <button type="button" className="sys-btn secondary" onClick={handleResetInstructions}>Reset</button>
                <button type="button" className="sys-btn primary" onClick={handleSaveInstructions}>Apply Prompt</button>
              </div>
            </div>
          </div>
        )}

        <main className="chat-viewport" role="log">
          {isLoading ? (
            <div className="loader">INITIALIZING SECURE LOG CHANNEL...</div>
          ) : messages.length === 0 ? (
            <div className="empty-state">
              <div className="wl-wrap">
                <div className="wl-ring"></div>
                <div className="wl-inner">
                  <svg viewBox="0 0 24 24" width="20" height="20">
                    <path
                      d="M12 2L14.09 8.26L21 9.27L16 14.14L17.18 21.02L12 17.77L6.82 21.02L8 14.14L3 9.27L9.91 8.26L12 2Z"
                      fill="white"
                    />
                  </svg>
                </div>
              </div>
              <h1 className="wt">Hello, {user?.name || 'Explorer'}</h1>
              <p className="ws">How can I assist you today?</p>
              
              <div className="sg">
                <button 
                  className="sc" 
                  onClick={() => handleSuggestionClick("Brainstorm 5 catchy names for a tech-focused coffee shop")}
                >
                  <div className="si ib"><Sparkles size={14} /></div>
                  <div className="st">Brainstorm names for my coffee shop</div>
                </button>
                <button 
                  className="sc" 
                  onClick={() => handleSuggestionClick("Design a relational database schema for an e-commerce website")}
                >
                  <div className="si ip"><Code2 size={14} /></div>
                  <div className="st">Design a database schema for an online shop</div>
                </button>
                <button 
                  className="sc" 
                  onClick={() => handleSuggestionClick("Can you explain quantum computing in simple terms for a beginner?")}
                >
                  <div className="si it"><Eye size={14} /></div>
                  <div className="st">Explain quantum computing in simple terms</div>
                </button>
                <button 
                  className="sc" 
                  onClick={() => handleSuggestionClick("Write a python script using requests and beautifulsoup to scrape headlines from a news page")}
                >
                  <div className="si io"><Terminal size={14} /></div>
                  <div className="st">Write a script to scrape news headlines</div>
                </button>
              </div>
            </div>
          ) : (
            <div className="messages-list">
              {messages.map((msg, idx) => (
                <MessageBubble key={msg.id || idx} message={msg} />
              ))}
              {isSending && (
                <div className="typing-indicator" aria-busy="true">
                  <div className="think">
                    <span>Thinking</span>
                    <div className="typ">
                      <span></span>
                      <span></span>
                      <span></span>
                    </div>
                  </div>
                </div>
              )}
              <div ref={messagesEndRef} />
            </div>
          )}
        </main>

        <footer className="footer-bar">
          <ChatInput 
            onSend={onSend} 
            isSending={isSending} 
            model={model}
            onModelChange={setModel}
            temperature={temperature}
            onTemperatureChange={setTemperature}
            isStreaming={isSending} // Toggle square stop button when loading
            onStopStreaming={() => setIsSending(false)} // Simulation allows stopping loading state
          />
        </footer>
      </div>
    </div>
  );
};

export default ChatPage;
