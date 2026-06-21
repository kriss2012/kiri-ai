import React, { useState, useRef, useEffect } from 'react';
import { Send, Image as ImageIcon, X, ChevronDown, Sparkles, Brain, PenTool, Code, AlertTriangle, Check, Route } from 'lucide-react';
import './ChatInput.css';

const AVAILABLE_MODELS = [
  { id: 'auto', name: 'Auto Route', desc: 'Dynamically routes your request', icon: 'route' },
  { id: 'google/gemini-3.5-flash', name: 'Gemini 3.5 Flash', desc: 'Default · Smart & Multimodal', icon: 'zap' },
  { id: 'google/gemini-3.1-flash-lite', name: 'Gemini 3.1 Lite', desc: 'Ultra-fast & light', icon: 'zap-off' },
  { id: 'openai/gpt-4o-mini', name: 'GPT-4o Mini', desc: 'High intelligence & reasoning', icon: 'brain' },
  { id: 'anthropic/claude-sonnet-4', name: 'Claude Sonnet 4', desc: 'Complex reasoning & writing', icon: 'pen-tool' },
  { id: 'meta-llama/llama-3.1-70b-instruct', name: 'Llama 3.1 70B', desc: 'Advanced coding & logic', icon: 'code' }
];

const ChatInput = ({ 
  onSend, 
  isSending, 
  model = 'auto', 
  onModelChange, 
  temperature = 0.7, 
  onTemperatureChange,
  isStreaming,
  onStopStreaming 
}) => {
  const [message, setMessage] = useState('');
  const [selectedFile, setSelectedFile] = useState(null);
  const [previewUrl, setPreviewUrl] = useState(null);
  const [isDropdownOpen, setIsDropdownOpen] = useState(false);
  const fileInputRef = useRef(null);
  const dropdownRef = useRef(null);

  // Close dropdown on click outside
  useEffect(() => {
    const handleOutsideClick = (e) => {
      if (dropdownRef.current && !dropdownRef.current.contains(e.target)) {
        setIsDropdownOpen(false);
      }
    };
    document.addEventListener('mousedown', handleOutsideClick);
    return () => document.removeEventListener('mousedown', handleOutsideClick);
  }, []);

  const selectedModel = AVAILABLE_MODELS.find(m => m.id === model) || AVAILABLE_MODELS[0];

  const handleFileChange = (e) => {
    const file = e.target.files[0];
    if (file) {
      setSelectedFile(file);
      setPreviewUrl(URL.createObjectURL(file));
    }
  };

  const handleRemoveFile = () => {
    setSelectedFile(null);
    setPreviewUrl(null);
    if (fileInputRef.current) fileInputRef.current.value = '';
  };

  const handleSubmit = (e) => {
    e?.preventDefault();
    if ((message.trim() || selectedFile) && !isSending) {
      onSend(message, selectedFile);
      setMessage('');
      handleRemoveFile();
    }
  };

  const handleKeyDown = (e) => {
    if (e.key === 'Enter' && !e.shiftKey) {
      e.preventDefault();
      handleSubmit();
    }
  };

  // Check if selected model supports vision (Llama 3.1 does not, auto/gemini/gpt-4o/claude do)
  const isVisionSupported = model !== 'meta-llama/llama-3.1-70b-instruct';
  const showVisionWarning = selectedFile && !isVisionSupported;

  return (
    <div className="chat-input-wrapper">
      {showVisionWarning && (
        <div className="vision-warn">
          <AlertTriangle size={14} />
          <span>
            This model does not support file attachments. Switch to <code>Gemini 3.5 Flash</code>.
          </span>
        </div>
      )}

      {previewUrl && (
        <div className="fbar">
          <div className="img-preview">
            <img src={previewUrl} alt="Preview" />
            <button className="f-rm" onClick={handleRemoveFile}>
              <X size={10} />
            </button>
          </div>
        </div>
      )}

      <form className={`iw ${showVisionWarning ? 'highlight' : ''}`} onSubmit={handleSubmit}>
        {/* Model selector row */}
        <div className="model-row">
          <div className="model-dd" ref={dropdownRef}>
            <button
              type="button"
              className={`model-btn ${isDropdownOpen ? 'open' : ''}`}
              onClick={() => setIsDropdownOpen(!isDropdownOpen)}
              aria-label="Select model"
            >
              {selectedModel.id === 'auto' ? (
                <Route size={13} style={{ color: 'var(--grad1)' }} />
              ) : (
                <svg viewBox="0 0 24 24" width="13" height="13">
                  <defs>
                    <linearGradient id="mvg" x1="0%" y1="0%" x2="100%" y2="100%">
                      <stop offset="0%" stopColor="var(--grad1)" />
                      <stop offset="50%" stopColor="var(--grad2)" />
                      <stop offset="100%" stopColor="var(--grad3)" />
                    </linearGradient>
                  </defs>
                  <path
                    d="M12 2L14.09 8.26L21 9.27L16 14.14L17.18 21.02L12 17.77L6.82 21.02L8 14.14L3 9.27L9.91 8.26L12 2Z"
                    fill="url(#mvg)"
                  />
                </svg>
              )}
              <span>{selectedModel.name}</span>
              <ChevronDown className="chev" size={10} />
            </button>

            {isDropdownOpen && (
              <div className="model-menu">
                {AVAILABLE_MODELS.map(m => (
                  <div
                    key={m.id}
                    className={`mm-opt ${m.id === selectedModel.id ? 'sel' : ''}`}
                    onClick={() => {
                      onModelChange(m.id);
                      setIsDropdownOpen(false);
                    }}
                  >
                    <div className="mmo-icon">
                      {m.icon === 'route' && <Route size={13} />}
                      {m.icon === 'zap' && <Sparkles size={13} />}
                      {m.icon === 'zap-off' && <Sparkles size={13} style={{ opacity: 0.5 }} />}
                      {m.icon === 'brain' && <Brain size={13} />}
                      {m.icon === 'pen-tool' && <PenTool size={13} />}
                      {m.icon === 'code' && <Code size={13} />}
                    </div>
                    <div className="mmo-info">
                      <div className="mmo-name">{m.name}</div>
                      <div className="mmo-desc">{m.desc}</div>
                    </div>
                    {m.id === selectedModel.id && (
                      <Check className="mmo-chk" size={12} />
                    )}
                  </div>
                ))}
              </div>
            )}
          </div>

          <div className="temp-ctrl">
            <span className="temp-lbl">Temp</span>
            <input
              type="number"
              className="temp-input"
              value={temperature}
              onChange={(e) => onTemperatureChange(parseFloat(e.target.value) || 0.7)}
              min="0"
              max="2"
              step="0.1"
            />
          </div>
        </div>

        {/* Textarea and Send Button Row */}
        <div className="text-row">
          <textarea
            className="message-input"
            value={message}
            onChange={(e) => setMessage(e.target.value)}
            placeholder="Ask Kiri..."
            disabled={isSending}
            onKeyDown={handleKeyDown}
            rows={1}
          />

          <div className="ia">
            <button
              type="button"
              className="ib2"
              onClick={() => fileInputRef.current?.click()}
              title="Attach image"
            >
              <ImageIcon size={16} />
            </button>
            <input
              type="file"
              ref={fileInputRef}
              style={{ display: 'none' }}
              accept="image/*"
              onChange={handleFileChange}
            />

            {isStreaming ? (
              <button
                type="button"
                className="ib2 stbtn"
                onClick={onStopStreaming}
                title="Stop generation"
              >
                <X size={16} />
              </button>
            ) : (
              <button
                type="submit"
                className={`ib2 sbtn ${(message.trim() || selectedFile) && !showVisionWarning ? 'active' : ''}`}
                disabled={(!message.trim() && !selectedFile) || showVisionWarning}
                title="Send message"
              >
                <Send size={16} />
              </button>
            )}
          </div>
        </div>
      </form>
      <div className="disc">
        Kiri can make mistakes. Verify important info.
      </div>
    </div>
  );
};

export default ChatInput;
