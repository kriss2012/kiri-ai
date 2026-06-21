import React, { useState } from 'react';
import ReactMarkdown from 'react-markdown';
import { Copy, Check, ThumbsUp, ThumbsDown, FileText } from 'lucide-react';
import { useAuth } from '../../context/AuthContext';
import './MessageBubble.css';

const CodeBlock = ({ code, language }) => {
  const [copied, setCopied] = useState(false);

  const handleCopy = () => {
    navigator.clipboard.writeText(code);
    setCopied(true);
    setTimeout(() => setCopied(false), 2000);
  };

  return (
    <div className="cblk">
      <div className="cbh">
        <span>{language.toUpperCase()}</span>
        <button className="cbc-btn" onClick={handleCopy} type="button">
          {copied ? <Check size={12} /> : <Copy size={12} />}
          <span>{copied ? 'Copied' : 'Copy code'}</span>
        </button>
      </div>
      <pre>
        <code className={`language-${language}`}>{code}</code>
      </pre>
    </div>
  );
};

const MessageBubble = ({ message }) => {
  const { user } = useAuth();
  const isUser = message.role === 'user';
  const [copied, setCopied] = useState(false);
  const [liked, setLiked] = useState(false);
  const [disliked, setDisliked] = useState(false);
  
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

  return (
    <div className={`mr ${isUser ? 'usr' : ''}`}>
      {!isUser && (
        <div className="ma ai">
          <svg viewBox="0 0 24 24" width="16" height="16">
            <path
              d="M12 2L14.09 8.26L21 9.27L16 14.14L17.18 21.02L12 17.77L6.82 21.02L8 14.14L3 9.27L9.91 8.26L12 2Z"
              fill="white"
            />
          </svg>
        </div>
      )}

      <div className="mc">
        {!isUser && message.model && (
          <div className="msg-meta">
            <span>{message.model.split('/')[1]?.toUpperCase() || message.model}</span>
          </div>
        )}

        {isUser ? (
          <div className="ub">
            {imageMatch && (
              <img className="msg-img" src={imageMatch[1]} alt="Attachment" />
            )}
            {attachmentMatch && !imageMatch && (
              <div className="msg-pdf-pill">
                <FileText size={13} />
                <span>{attachmentMatch[1]}</span>
              </div>
            )}
            <div className="mt">{cleanContent}</div>
          </div>
        ) : (
          <div className="mt">
            <ReactMarkdown
              components={{
                code({ node, className, children, ...props }) {
                  const match = /language-(\w+)/.exec(className || '');
                  const lang = match ? match[1] : '';
                  const codeString = String(children).replace(/\n$/, '');

                  if (match) {
                    return <CodeBlock code={codeString} language={lang} />;
                  }
                  return (
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
        )}

        {!isUser && (
          <div className="mact">
            <button className="mab copy" onClick={handleCopy} title="Copy response">
              {copied ? <Check size={13} /> : <Copy size={13} />}
            </button>
            <button 
              className={`mab ${liked ? 'lk' : ''}`} 
              onClick={() => { setLiked(!liked); setDisliked(false); }} 
              title="Like"
            >
              <ThumbsUp size={13} />
            </button>
            <button 
              className={`mab ${disliked ? 'dlk' : ''}`} 
              onClick={() => { setDisliked(!disliked); setLiked(false); }} 
              title="Dislike"
            >
              <ThumbsDown size={13} />
            </button>
          </div>
        )}
      </div>

      {isUser && (
        <div className="ma u">
          {user?.name?.charAt(0).toUpperCase() || 'U'}
        </div>
      )}
    </div>
  );
};

export default MessageBubble;
