import React, { useState, useRef, useEffect } from 'react';
import { Send, Paperclip, X, Sparkles, ChevronDown, Check, Eye, Cpu, Zap, Code2, AlertTriangle, Square } from 'lucide-react';
import './ChatInput.css';

const AVAILABLE_MODELS = [
  { id: 'auto', name: 'Smart Router', desc: 'Auto-select best model', size: 'Auto', icon: Sparkles, isVision: false },
  { id: 'google/gemini-2.0-flash-001', name: 'Gemini 2.0 Flash', desc: 'Fast, multimodal reasoning', size: '2.0 GB', icon: Eye, isVision: true },
  { id: 'google/gemini-2.0-flash-lite-001', name: 'Gemini 2.0 Flash Lite', desc: 'Ultra-fast lightweight model', size: '1.2 GB', icon: Zap, isVision: false },
  { id: 'anthropic/claude-3.5-sonnet', name: 'Claude 3.5 Sonnet', desc: 'State-of-the-art coder', size: '3.5 GB', icon: Code2, isVision: true },
  { id: 'openai/gpt-4o-mini', name: 'GPT-4o Mini', desc: 'High-performance reasoning', size: '1.8 GB', icon: Cpu, isVision: false },
  { id: 'meta-llama/llama-3.1-70b-instruct', name: 'Llama 3.1 70B', desc: 'Powerful open-source model', size: '70 GB', icon: Cpu, isVision: false },
];

const ChatInput = ({ 
  onSend, 
  isSending, 
  model, 
  onModelChange, 
  temperature, 
  onTemperatureChange, 
  isStreaming, 
  onStopStreaming 
}) => {
  const [text, setText] = useState('');
  const [file, setFile] = useState(null);
  const [filePreview, setFilePreview] = useState(null);
  const [isMenuOpen, setIsMenuOpen] = useState(false);
  const fileInputRef = useRef(null);
  const textareaRef = useRef(null);
  const menuRef = useRef(null);

  // Auto-resize textarea
  useEffect(() => {
    if (textareaRef.current) {
      textareaRef.current.style.height = 'auto';
      textareaRef.current.style.height = `${textareaRef.current.scrollHeight}px`;
    }
  }, [text]);

  // Click outside listener for model menu
  useEffect(() => {
    const handleClickOutside = (event) => {
      if (menuRef.current && !menuRef.current.contains(event.target)) {
        setIsMenuOpen(false);
      }
    };
    document.addEventListener('mousedown', handleClickOutside);
    return () => document.removeEventListener('mousedown', handleClickOutside);
  }, []);

  const handleFileChange = (e) => {
    const selectedFile = e.target.files[0];
    if (!selectedFile) return;

    setFile(selectedFile);

    // Create preview if it's an image
    if (selectedFile.type.startsWith('image/')) {
      const reader = new FileReader();
      reader.onload = (event) => {
        setFilePreview(event.target.result);
      };
      reader.readAsDataURL(selectedFile);
    } else {
      setFilePreview({ name: selectedFile.name, type: selectedFile.type, size: selectedFile.size });
    }
  };

  const removeFile = () => {
    setFile(null);
    setFilePreview(null);
    if (fileInputRef.current) fileInputRef.current.value = '';
  };

  const handleSubmit = (e) => {
    if (e) e.preventDefault();
    if ((!text.trim() && !file) || isSending || isStreaming) return;

    onSend(text, file);
    setText('');
    removeFile();
    if (textareaRef.current) {
      textareaRef.current.focus();
    }
  };

  const handleKeyDown = (e) => {
    if (e.key === 'Enter' && !e.shiftKey) {
      e.preventDefault();
      handleSubmit();
    }
  };

  const activeModelObj = AVAILABLE_MODELS.find(m => m.id === model) || AVAILABLE_MODELS[0];
  const activeModelIcon = activeModelObj.icon;
  const isSelectedModelVision = activeModelObj.isVision;

  // Show warning if file is attached but model doesn't support vision
  const showVisionWarning = file && !isSelectedModelVision && model !== 'auto';

  return (
    <div className="chat-input-wrapper">
      {showVisionWarning && (
        <div className="vision-warn">
          <AlertTriangle size={15} />
          <span>
            Warning: <code>{activeModelObj.name}</code> does not support vision processing. Your file will be ignored.
          </span>
        </div>
      )}

      {file && (
        <div className="fbar">
          {file.type.startsWith('image/') ? (
            <div className="img-preview">
              <img src={filePreview} alt="Image attachment preview" />
              <button className="f-rm" onClick={removeFile} title="Remove image" aria-label="Remove image">
                <X size={10} />
              </button>
            </div>
          ) : (
            <div className="pdf-preview">
              <FileText size={15} />
              <div className="p-info">
                <strong>{file.name}</strong>
                <span>{(file.size / 1024).toFixed(1)} KB</span>
              </div>
              <button className="f-rm" onClick={removeFile} title="Remove file" aria-label="Remove file">
                <X size={13} />
              </button>
            </div>
          )}
        </div>
      )}

      <form onSubmit={handleSubmit} className="iw">
        <div className="model-row">
          <div className="model-dd" ref={menuRef}>
            <button
              type="button"
              className={`model-btn ${isMenuOpen ? 'open' : ''}`}
              onClick={() => setIsMenuOpen(!isMenuOpen)}
              aria-expanded={isMenuOpen}
              aria-haspopup="listbox"
              aria-label={`Selected model: ${activeModelObj.name}`}
            >
              {React.createElement(activeModelIcon, { size: 13 })}
              <span>{activeModelObj.name}</span>
              <ChevronDown className="chev" size={10} />
            </button>

            {isMenuOpen && (
              <div className="model-menu" role="listbox">
                {AVAILABLE_MODELS.map((m) => {
                  const IconComp = m.icon;
                  return (
                    <div
                      key={m.id}
                      className={`mm-opt ${m.id === model ? 'sel' : ''}`}
                      onClick={() => {
                        onModelChange(m.id);
                        setIsMenuOpen(false);
                      }}
                      role="option"
                      aria-selected={m.id === model}
                    >
                      <div className="mmo-icon">
                        <IconComp size={14} />
                      </div>
                      <div className="mmo-info">
                        <div className="mmo-name">{m.name}</div>
                        <div className="mmo-desc">{m.size} • {m.desc}</div>
                      </div>
                      {m.id === model && <Check className="mmo-chk" size={13} />}
                    </div>
                  );
                })}
              </div>
            )}
          </div>

          <div className="temp-ctrl">
            <label className="temp-lbl" htmlFor="temperature-input">Temp</label>
            <input
              id="temperature-input"
              type="number"
              className="temp-input"
              value={temperature}
              onChange={(e) => {
                const val = parseFloat(e.target.value);
                if (!isNaN(val)) {
                  onTemperatureChange(Math.max(0, Math.min(2, val)));
                } else {
                  onTemperatureChange(e.target.value);
                }
              }}
              min="0"
              max="2"
              step="0.1"
              aria-label="Model temperature"
            />
          </div>
        </div>

        <div className="text-row">
          <textarea
            ref={textareaRef}
            className="message-input"
            rows="1"
            placeholder={file ? "Ask about this file..." : "Message Kiri AI..."}
            value={text}
            onChange={(e) => setText(e.target.value)}
            onKeyDown={handleKeyDown}
            aria-label="Chat input"
          />

          <div className="ia">
            <input
              type="file"
              ref={fileInputRef}
              onChange={handleFileChange}
              style={{ display: 'none' }}
              accept="image/*,application/pdf,text/*"
              aria-label="Upload file"
            />
            <button
              type="button"
              className="ib2"
              onClick={() => fileInputRef.current?.click()}
              title="Attach files (Image, PDF, Text)"
              aria-label="Attach files"
            >
              <Paperclip size={16} />
            </button>

            {isStreaming ? (
              <button
                type="button"
                className="ib2 stbtn"
                onClick={onStopStreaming}
                title="Stop generation"
                aria-label="Stop generation"
              >
                <Square size={14} fill="currentColor" />
              </button>
            ) : (
              <button
                type="submit"
                className={`ib2 sbtn ${text.trim() || file ? 'active' : ''}`}
                disabled={(!text.trim() && !file) || isSending}
                title="Send message"
                aria-label="Send message"
              >
                <Send size={15} />
              </button>
            )}
          </div>
        </div>
      </form>
      <div className="disc">Kiri AI can make mistakes. Verify important info.</div>
    </div>
  );
};

export default ChatInput;
