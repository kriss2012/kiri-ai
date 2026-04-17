import React from 'react';
import { useAuth } from '../../context/AuthContext';
import { useTheme } from '../../context/ThemeContext';
import { LogOut, Settings, Plus, MessageSquare, Sun, Moon } from 'lucide-react';
import { KiriButton } from '../Shared/KiriUI';
import './Sidebar.css';

const Sidebar = ({ isOpen, onClose, conversations, currentId, onSelect, onNewChat }) => {
  const { user, logout } = useAuth();
  const { isDarkMode, toggleTheme } = useTheme();

  return (
    <div className={`sidebar ${isOpen ? 'open' : ''}`}>
      <div className="sidebar-header">
        <img src="/logo.svg" alt="Kiri Logo" className="sidebar-logo" />
        <h2 className="mono">Kiri // Atelier</h2>
      </div>

      <div className="sidebar-actions">
        <KiriButton onClick={onNewChat} className="new-chat-btn">
          <Plus size={18} /> NEW_SESSION
        </KiriButton>
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
        <div className="user-profile" onClick={() => window.location.href = '/profile'}>
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
          <button onClick={() => window.location.href = '/profile'} className="footer-btn">
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
