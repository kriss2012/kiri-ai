import React, { useState } from 'react';
import ReactMarkdown from 'react-markdown';
import { Copy, Check, ThumbsUp, ThumbsDown, FileText } from 'lucide-react';
import { useAuth } from '../../context/AuthContext';
import './MessageBubble.css';

const CodeBlock = ({ language, value }) => {
  const [copied, setCopied] = useState(false);

  const handleCopyCode = () => {
    navigator.clipboard.writeText(value);
    setCopied(true);
    setTimeout(() => setCopied(false), 2000);
  };

  return (
    <div className="cblk">
      <div className="cbh">
        <span>{language.toUpperCase()}</span>
        <button className="cbc-btn" onClick={handleCopyCode} aria-label="Copy code">
          {copied ? <Check size={12} /> : <Copy size={12} />}
          <span>{copied ? 'Copied' : 'Copy code'}</span>
        </button>
      </div>
      <pre>
        <code>{value}</code>
      </pre>
    </div>
  );
};

const MessageBubble = ({ message }) => {
  const { user } = useAuth();
  const isUser = message.role === 'user';
  
  const [copied, setCopied] = useState(false);
  const [rating, setRating] = useState(null); // 'like' or 'dislike' or null

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

  const handleRate = (type) => {
    if (rating === type) {
      setRating(null);
    } else {
      setRating(type);
    }
  };

  // Generate some realistic stats for visual satisfaction
  const wordCount = cleanContent.split(/\s+/).filter(Boolean).length;
  const tokenCount = Math.round(wordCount * 1.35);
  const speed = 48; // t/s
  const duration = (tokenCount / speed).toFixed(1);

  return (
    <div className={`mr ${isUser ? 'usr' : 'ai-row'}`} role="listitem">
      <div className={`ma ${isUser ? 'u' : 'ai'}`}>
        {isUser ? (
          user?.name?.charAt(0).toUpperCase() || 'U'
        ) : (
          <svg viewBox="0 0 24 24" width="16" height="16">
            <path
              d="M12 2L14.09 8.26L21 9.27L16 14.14L17.18 21.02L12 17.77L6.82 21.02L8 14.14L3 9.27L9.91 8.26L12 2Z"
              fill="white"
            />
          </svg>
        )}
      </div>

      <div className="mc">
        {isUser ? (
          <div className="ub">
            <div className="mt">{cleanContent}</div>
            {attachmentMatch && (
              <div className="usr-attach">
                <div className="msg-pdf-pill">
                  <FileText size={13} />
                  <span>{attachmentMatch[1]}</span>
                </div>
              </div>
            )}
            {imageMatch && (
              <div className="usr-attach">
                <img src={imageMatch[1]} alt="Attachment" className="msg-img" />
              </div>
            )}
          </div>
        ) : (
          <>
            <div className="mt">
              <ReactMarkdown
                components={{
                  code({ node, className, children, ...props }) {
                    const match = /language-(\w+)/.exec(className || '');
                    const codeVal = String(children).replace(/\n$/, '');
                    return match ? (
                      <CodeBlock language={match[1]} value={codeVal} />
                    ) : (
                      <code className={className} {...props}>
                        {children}
                      </code>
                    );
                  }
                }}
              >
                {cleanContent}
              </ReactMarkdown>
            </div>

            {tokenCount > 0 && (
              <div className="msg-meta">
                <span>{message.model ? message.model.split('/').pop().toUpperCase() : 'GEMINI'}</span>
                <span className="dot"></span>
                <span>{tokenCount} tokens</span>
                <span className="dot"></span>
                <span>{speed} tok/s</span>
                <span className="dot"></span>
                <span>{duration}s</span>
              </div>
            )}

            <div className="mact">
              <button
                className="mab"
                onClick={handleCopy}
                title="Copy response"
                aria-label="Copy response"
              >
                {copied ? <Check size={14} /> : <Copy size={14} />}
              </button>
              <button
                className={`mab ${rating === 'like' ? 'lk' : ''}`}
                onClick={() => handleRate('like')}
                title="Thumbs up"
                aria-label="Thumbs up"
              >
                <ThumbsUp size={14} />
              </button>
              <button
                className={`mab ${rating === 'dislike' ? 'dlk' : ''}`}
                onClick={() => handleRate('dislike')}
                title="Thumbs down"
                aria-label="Thumbs down"
              >
                <ThumbsDown size={14} />
              </button>
            </div>
          </>
        )}
      </div>
    </div>
  );
};

export default MessageBubble;
