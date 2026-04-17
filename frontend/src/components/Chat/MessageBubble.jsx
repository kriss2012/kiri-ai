import React from 'react';
import ReactMarkdown from 'react-markdown';
import './Chat.css';

const MessageBubble = ({ message }) => {
  const isUser = message.role === 'user';
  
  // SANITIZE_CONTENT: Handle [IMAGE_URI: ...] markers if they exist in content
  const content = message.content || '';
  const imageMatch = content.match(/\[IMAGE_URI: (.*?)\]/);
  const attachmentMatch = content.match(/\[IMAGE_ATTACHMENT: (.*?)\]/);
  
  const cleanContent = content
    .replace(/\[IMAGE_URI: .*?\]/, '')
    .replace(/\[IMAGE_ATTACHMENT: .*?\]/, '');

  return (
    <div className={`message-row ${isUser ? 'user' : 'assistant'}`}>
      <div className="message-container">
        <div className="message-label mono">{isUser ? 'USER_LOG' : 'KIRI_AI'}</div>
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
              FILE_REF: {attachmentMatch[1]}
            </div>
          )}
        </div>
      </div>
    </div>
  );
};

export default MessageBubble;
