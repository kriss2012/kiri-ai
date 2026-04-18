import React, { useState, useEffect, useRef } from 'react';
import { useAuth } from '../../context/AuthContext';
import Sidebar from '../Sidebar/Sidebar';
import MessageBubble from './MessageBubble';
import ChatInput from './ChatInput';
import api from '../../utils/api';
import { Menu, Star, Moon, Sun } from 'lucide-react';
import { useTheme } from '../../context/ThemeContext';
import './Chat.css';

const ChatPage = () => {
  const { user } = useAuth();
  const { isDarkMode, toggleTheme } = useTheme();
  const [isSidebarOpen, setIsSidebarOpen] = useState(window.innerWidth > 1024);
  const [conversations, setConversations] = useState([]);
  const [currentConvId, setCurrentConvId] = useState(null);
  const [messages, setMessages] = useState([]);
  const [isSending, setIsSending] = useState(false);
  const [isLoading, setIsLoading] = useState(false);
  const [title, setTitle] = useState('Kiri AI');
  
  const messagesEndRef = useRef(null);

  useEffect(() => {
    fetchConversations();
  }, []);

  useEffect(() => {
    scrollToBottom();
  }, [messages]);

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
        setMessages(res.data.conversation.messages);
        setTitle(res.data.conversation.title || 'Untitled log');
      }
      if (window.innerWidth <= 768) setIsSidebarOpen(false);
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
    if (window.innerWidth <= 768) setIsSidebarOpen(false);
  };

  const onSend = async (text, file) => {
    if (isSending) return;

    // LOCAL_OPTIMISTIC_UPDATE: Show user message immediately
    const userMsg = {
      role: 'user',
      content: text + (file ? `\n[IMAGE_URI: ${URL.createObjectURL(file)}]` : ''),
      id: Date.now().toString()
    };
    
    setMessages(prev => [...prev, userMsg]);
    setIsSending(true);

    try {
      if (file) {
        // MULTIMODAL_UPLOAD
        const formData = new FormData();
        formData.append('file', file);
        formData.append('content', text);
        if (currentConvId) formData.append('conversationId', currentConvId);

        const res = await api.post('/chat/message/upload', formData, {
          headers: { 'Content-Type': 'multipart/form-data' }
        });

        if (res.data.success) {
          const assistantMsg = {
            role: 'assistant',
            content: res.data.message,
            id: Date.now() + 1
          };
          setMessages(prev => [...prev, assistantMsg]);
          if (!currentConvId) {
            setCurrentConvId(res.data.conversationId);
            fetchConversations();
          }
        }
      } else {
        // STANDARD_MESSAGE
        const res = await api.post('/chat/message', {
          message: text,
          conversationId: currentConvId
        });

        if (res.data.success) {
          const assistantMsg = {
            role: 'assistant',
            content: res.data.message,
            id: Date.now() + 1
          };
          setMessages(prev => [...prev, assistantMsg]);
          if (!currentConvId) {
            setCurrentConvId(res.data.conversationId);
            fetchConversations();
          }
        }
      }
    } catch (err) {
      console.error('Send failed', err);
    } finally {
      setIsSending(false);
    }
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
      />

      {isSidebarOpen && window.innerWidth <= 1024 && (
        <div className="sidebar-backdrop" onClick={() => setIsSidebarOpen(false)} />
      )}

      <div className="main-viewport">
        <header className="page-header">
          <button className="menu-toggle" onClick={() => setIsSidebarOpen(true)}>
            <Menu size={20} />
          </button>
          
          <div className="header-title mono">{title.toUpperCase()}</div>

          <div className="header-actions">
            <button className="icon-btn theme-toggle" onClick={toggleTheme}>
              {isDarkMode ? <Sun size={20} /> : <Moon size={20} />}
            </button>
            <button className="icon-btn premium-btn" onClick={() => window.location.href = '/pricing'}>
              <Star size={20} fill={user?.isPremium ? 'gold' : 'none'} />
            </button>
          </div>
        </header>

        <main className="chat-viewport">
          {isLoading ? (
            <div className="loader mono cinematic-fade">INITIALIZING_ENCRYPTED_CHANNEL...</div>
          ) : messages.length === 0 ? (
            <div className="empty-state cinematic-fade">
              <div className="mono-label mono">SYSTEM_READY</div>
              <h1 className="mono">Kiri AI</h1>
              <p>Multimodal intelligence layer active. Send a message or upload an artifact to begin analysis.</p>
            </div>
          ) : (
            <div className="messages-list">
              {messages.map((msg, idx) => (
                <MessageBubble key={msg.id || idx} message={msg} />
              ))}
              {isSending && (
                <div className="typing-indicator mono cinematic-fade">
                  KIRI_IS_ANALYZING...
                </div>
              )}
              <div ref={messagesEndRef} />
            </div>
          )}
        </main>

        <footer className="footer-bar">
          <ChatInput onSend={onSend} isSending={isSending} />
        </footer>
      </div>
    </div>
  );
};

export default ChatPage;
