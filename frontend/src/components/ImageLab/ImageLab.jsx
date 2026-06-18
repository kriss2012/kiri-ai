import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../../context/AuthContext';
import Sidebar from '../Sidebar/Sidebar';
import api from '../../utils/api';
import { Menu, Download, RefreshCw, Send, Image as ImageIcon } from 'lucide-react';
import { useTheme } from '../../context/ThemeContext';
import { KiriButton } from '../Shared/KiriUI';
import './ImageLab.css';

const ImageLab = () => {
  const navigate = useNavigate();
  const { user } = useAuth();
  const { isDarkMode, toggleTheme } = useTheme();
  const [isSidebarOpen, setIsSidebarOpen] = useState(window.innerWidth > 1024);
  const [prompt, setPrompt] = useState('');
  const [isGenerating, setIsGenerating] = useState(false);
  const [generatedImages, setGeneratedImages] = useState([]);
  const [selectedImage, setSelectedImage] = useState(null);

  const handleGenerate = async (e) => {
    e?.preventDefault();
    if (!prompt.trim() || isGenerating) return;

    setIsGenerating(true);
    try {
      const res = await api.post('/image/generate', { prompt });
      if (res.data.success) {
        const newImage = {
          id: Date.now(),
          url: res.data.imageUrl,
          prompt: prompt,
          revisedPrompt: res.data.revisedPrompt,
          timestamp: new Date().toISOString()
        };
        setGeneratedImages(prev => [newImage, ...prev]);
        setSelectedImage(newImage);
        setPrompt('');
      }
    } catch (err) {
      console.error('Image generation failed', err);
      alert('Generation failed. Please check your credit balance or try again.');
    } finally {
      setIsGenerating(false);
    }
  };

  const downloadImage = (url) => {
    const link = document.createElement('a');
    link.href = url;
    link.download = `kiri-ai-gen-${Date.now()}.png`;
    document.body.appendChild(link);
    link.click();
    document.body.removeChild(link);
  };

  return (
    <div className="app-container">
      <Sidebar 
        isOpen={isSidebarOpen} 
        onClose={() => setIsSidebarOpen(false)}
        conversations={[]} // Optional: load image history if stored in DB
        onSelect={() => {}} 
        onNewChat={() => navigate('/')}
      />

      <div className="main-viewport image-lab-viewport">
        <header className="page-header">
          <button className="menu-toggle" onClick={() => setIsSidebarOpen(true)}>
            <Menu size={20} />
          </button>
          <div className="header-title mono">IMAGE_LAB // V1.0</div>
        </header>

        <main className="image-lab-content">
          <div className="generation-area">
            {selectedImage ? (
              <div className="image-display-container cinematic-fade">
                <img src={selectedImage.url} alt="Generated" className="main-image" />
                <div className="image-overlay">
                  <div className="prompt-badge mono">{selectedImage.prompt}</div>
                  <KiriButton onClick={() => downloadImage(selectedImage.url)} variant="secondary">
                    <Download size={16} /> DOWNLOAD
                  </KiriButton>
                </div>
              </div>
            ) : (
              <div className="empty-image-state">
                <ImageIcon size={48} strokeWidth={1} />
                <p className="mono">WAITING_FOR_PROMPT...</p>
              </div>
            )}
          </div>

          <div className="history-sidebar">
            <div className="section-label mono">GENERATION_HISTORY</div>
            <div className="history-grid">
              {generatedImages.map(img => (
                <div 
                  key={img.id} 
                  className={`history-thumb ${selectedImage?.id === img.id ? 'active' : ''}`}
                  onClick={() => setSelectedImage(img)}
                >
                  <img src={img.url} alt="Thumb" />
                </div>
              ))}
            </div>
          </div>
        </main>

        <footer className="footer-bar prompt-footer">
          <form className="prompt-form" onSubmit={handleGenerate}>
            <input 
              type="text" 
              placeholder="Describe the image you want to generate..." 
              value={prompt}
              onChange={(e) => setPrompt(e.target.value)}
              disabled={isGenerating}
              className="prompt-input"
            />
            <KiriButton type="submit" disabled={isGenerating || !prompt.trim()}>
              {isGenerating ? <RefreshCw className="spin" size={18} /> : <Send size={18} />}
              <span className="btn-text">{isGenerating ? 'GENERATING...' : 'GENERATE'}</span>
            </KiriButton>
          </form>
        </footer>
      </div>
    </div>
  );
};

export default ImageLab;
