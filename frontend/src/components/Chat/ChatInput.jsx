import React, { useState, useRef } from 'react';
import { Send, Image as ImageIcon, X } from 'lucide-react';
import './ChatInput.css';

const ChatInput = ({ onSend, isSending }) => {
  const [message, setMessage] = useState('');
  const [selectedFile, setSelectedFile] = useState(null);
  const [previewUrl, setPreviewUrl] = useState(null);
  const fileInputRef = useRef(null);

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
    e.preventDefault();
    if ((message.trim() || selectedFile) && !isSending) {
      onSend(message, selectedFile);
      setMessage('');
      handleRemoveFile();
    }
  };

  return (
    <div className="chat-input-wrapper">
      {previewUrl && (
        <div className="file-preview-area cinematic-fade">
          <div className="preview-container">
            <img src={previewUrl} alt="Preview" />
            <button className="remove-btn" onClick={handleRemoveFile}>
              <X size={14} />
            </button>
          </div>
          <div className="preview-info mono">
            {selectedFile.name.toUpperCase()}
          </div>
        </div>
      )}

      <form className="chat-input-form" onSubmit={handleSubmit}>
        <button 
          type="button" 
          className="attach-btn" 
          onClick={() => fileInputRef.current?.click()}
        >
          <ImageIcon size={20} />
        </button>
        
        <input 
          type="file" 
          ref={fileInputRef} 
          style={{ display: 'none' }} 
          accept="image/*"
          onChange={handleFileChange}
        />

        <input
          type="text"
          className="message-input"
          value={message}
          onChange={(e) => setMessage(e.target.value)}
          placeholder="SEND_MESSAGE / COMMAND_LOG"
          disabled={isSending}
        />

        <button 
          type="submit" 
          className={`send-btn ${message.trim() || selectedFile ? 'active' : ''}`}
          disabled={(!message.trim() && !selectedFile) || isSending}
        >
          <Send size={20} />
        </button>
      </form>
    </div>
  );
};

export default ChatInput;
