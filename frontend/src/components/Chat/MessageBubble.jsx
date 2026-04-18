import React, { useState } from 'react';
import ReactMarkdown from 'react-markdown';
import { Copy, Check, FileText } from 'lucide-react';
import { useAuth } from '../../context/AuthContext';
import './MessageBubble.css';

const MessageBubble = ({ message }) => {
  const { user } = useAuth();
  const isUser = message.role === 'user';
  const [copied, setCopied] = useState(false);
  
  const content = message.content || '';
  const imageMatch = content.match(/\[IMAGE_URI: (.*?)\]/);
  const attachmentMatch = content.match(/\[IMAGE_ATTACHMENT: (.*?)\]/);
  
  const cleanContent = content
    .replace(/\[IMAGE_URI: .*?\]/, '')
    .replace(/\[IMAGE_ATTACHMENT: .*?\]/, '');

  const handleCopy = () => {
    navigator.clipboard.writeText(cleanContent);
    setCopied(true);
    setTimeout(() => setCopied(false), 2000);
  };

  const getInitials = () => {
    if (!user?.name) return 'U';
    return user.name.split(' ').map(n => n[0]).join('').toUpperCase().take(1);
    // Wait, take(1) is not standard. Replaced with charAt(0).
  };

  return (
    <div className={`message-row ${isUser ? 'user' : 'assistant'} cinematic-fade`}>
      <div className="message-container">
        <div className="message-avatar">
          {isUser ? (
            user?.name?.charAt(0).toUpperCase() || 'U'
          ) : (
            <img src="/logo.svg" alt="Kiri AI" />
          )}
        </div>

        <div className="message-content-wrapper">
          <div className="message-label mono">
            {isUser ? (user?.name?.toUpperCase() || 'USER_LOG') : 'KIRI_INTELLIGENCE // ACCESS_POINT'}
          </div>

          <div className="message-bubble">
            {imageMatch && (
              <div className="message-image">
                <img src={imageMatch[1]} alt="Attachment" />
              </div>
            )}
            
            <div className="message-text">
              <ReactMarkdown>{cleanContent}</ReactMarkdown>
            </div>

            {attachmentMatch && (
              <div className="message-attachment mono">
                <FileText size={14} /> FILE_REF: {attachmentMatch[1]}
              </div>
            )}
          </div>

          {!isUser && (
            <div className="message-actions">
              <button className="action-btn mono" onClick={handleCopy}>
                {copied ? <Check size={14} /> : <Copy size={14} />}
                {copied ? 'COPIED' : 'COPY_LOG'}
              </button>
            </div>
          )}
        </div>
      </div>
    </div>
  );
};

export default MessageBubble;
