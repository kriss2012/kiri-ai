import React from 'react';
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../../context/AuthContext';
import { useTheme } from '../../context/ThemeContext';
import { LogOut, Settings, Plus, MessageSquare, Sun, Moon } from 'lucide-react';
import { KiriButton } from '../Shared/KiriUI';
import './Sidebar.css';

const Sidebar = ({ isOpen, onClose, conversations, currentId, onSelect, onNewChat }) => {
  const navigate = useNavigate();
  const { user, logout } = useAuth();
  const { isDarkMode, toggleTheme } = useTheme();

  return (
    <div className={`sidebar ${isOpen ? 'open' : ''}`}>
      <div className="sidebar-header">
        <div className="avatar micro" style={{ backgroundColor: 'var(--accent)', marginRight: '8px' }}>K</div>
        <h2 className="mono">Kiri // Atelier</h2>
      </div>

      <div className="sidebar-actions">
        <KiriButton onClick={onNewChat} className="new-chat-btn">
          <Plus size={18} /> NEW_SESSION
        </KiriButton>
      </div>

      <div className="sidebar-modes">
        <div className="section-label mono">Operation_Modes</div>
        <div className="mode-item active">
          <MessageSquare size={16} /> <span>CORE_CHAT</span>
        </div>
        <div className="mode-item" onClick={() => navigate('/image-lab')}>
          <img src="https://img.icons8.com/ios-glyphs/30/null/paint-palette.png" style={{filter: 'invert(1)', width: 16, height: 16}} alt="" /> <span>IMAGE_LAB</span>
        </div>
        <div className="mode-item" onClick={() => navigate('/code-space')}>
          <img src="https://img.icons8.com/ios-glyphs/30/null/code.png" style={{filter: 'invert(1)', width: 16, height: 16}} alt="" /> <span>CODE_SPACE</span>
        </div>
      </div>

      <div className="sidebar-content">
        <div className="section-label mono">Recent_Logs</div>
        <div className="conversation-list">
          {conversations.map(conv => (
            <div
              key={conv.id}
              className={`conversation-item ${conv.id === currentId ? 'active' : ''}`}
              onClick={() => onSelect(conv.id)}
            >
              <MessageSquare size={16} />
              <span className="conv-title">{conv.title?.toUpperCase() || 'UNTITLED_LOG'}</span>
            </div>
          ))}
        </div>
      </div>

      <div className="sidebar-footer">
        <div className="user-profile" onClick={() => navigate('/profile')}>
          <div className="avatar micro">{user?.name?.charAt(0) || 'U'}</div>
          <div className="user-info">
            <div className="user-name mono">{user?.name?.toUpperCase() || 'USER_NULL'}</div>
            <div className="user-plan mono">{user?.plan?.toUpperCase()}</div>
          </div>
        </div>

        <div className="footer-actions">
          <button onClick={toggleTheme} className="footer-btn">
            {isDarkMode ? <Sun size={18} /> : <Moon size={18} />}
          </button>
          <button onClick={() => navigate('/profile')} className="footer-btn">
            <Settings size={18} />
          </button>
          <button onClick={logout} className="footer-btn logout">
            <LogOut size={18} />
          </button>
        </div>
      </div>
    </div>
  );
};

export default Sidebar;
