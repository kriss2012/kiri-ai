import React from 'react';
import { useAuth } from '../../context/AuthContext';
import { useTheme } from '../../context/ThemeContext';
import { LogOut, Settings, Plus, MessageSquare, Sun, Moon, Trash2, Palette, Terminal } from 'lucide-react';
import './Sidebar.css';

const Sidebar = ({ isOpen, onClose, conversations, currentId, onSelect, onNewChat, onDeleteChat, onClearAll }) => {
  const { user, logout } = useAuth();
  const { isDarkMode, toggleTheme } = useTheme();

  return (
    <aside className={`sidebar ${isOpen ? 'open' : 'closed'}`} role="navigation" aria-label="Chat history">
      <div className="sidebar-header">
        <div className="sidebar-brand" onClick={() => window.location.href = '/'}>
          <div className="sidebar-brand-icon">
            <svg viewBox="0 0 24 24">
              <path
                d="M12 2L14.09 8.26L21 9.27L16 14.14L17.18 21.02L12 17.77L6.82 21.02L8 14.14L3 9.27L9.91 8.26L12 2Z"
                fill="white"
              />
            </svg>
          </div>
          <span className="sidebar-brand-name">Kiri AI</span>
        </div>
        <div className="sidebar-actions">
          <button className="new-chat-btn" onClick={onNewChat} aria-label="New chat">
            <Plus size={14} />
            <span>New chat</span>
          </button>
        </div>
      </div>

      <div className="sidebar-modes">
        <div className="section-label">Operation_Modes</div>
        <div className="mode-item active" onClick={() => window.location.href = '/'}>
          <div className="mode-icon"><MessageSquare size={15} /></div>
          <span>CORE_CHAT</span>
        </div>
        <div className="mode-item" onClick={() => window.location.href = '/image-lab'}>
          <div className="mode-icon"><Palette size={15} /></div>
          <span>IMAGE_LAB</span>
        </div>
      </div>

      <div className="sidebar-content">
        <div className="section-label">Recent_Logs</div>
        <div className="conversation-list">
          {conversations.map(conv => (
            <div
              key={conv.id}
              className={`conversation-item ${conv.id === currentId ? 'active' : ''}`}
              onClick={() => onSelect(conv.id)}
            >
              <MessageSquare size={14} />
              <span className="conv-title">{conv.title || 'Untitled chat'}</span>
              <button 
                className="conv-delete" 
                onClick={(e) => {
                  e.stopPropagation();
                  onDeleteChat(conv.id);
                }} 
                aria-label="Delete chat"
                title="Delete chat"
              >
                <Trash2 size={13} />
              </button>
            </div>
          ))}
        </div>
      </div>

      <div className="sidebar-footer">
        <div className="user-profile" onClick={() => window.location.href = '/profile'}>
          <div className="avatar micro">{user?.name?.charAt(0).toUpperCase() || 'U'}</div>
          <div className="user-info">
            <div className="user-name">{user?.name || 'User'}</div>
            <div className="user-plan">{user?.plan || 'Free'}</div>
          </div>
        </div>

        <div className="footer-actions">
          <button
            className="footer-btn"
            onClick={toggleTheme}
            aria-label="Toggle theme"
            title="Toggle theme"
          >
            {isDarkMode ? <Sun size={15} /> : <Moon size={15} />}
          </button>
          <div className="footer-spacer"></div>
          <button
            className="footer-btn logout"
            onClick={onClearAll}
            aria-label="Clear all chats"
            title="Clear all chats"
          >
            <Trash2 size={15} />
          </button>
          <button
            className="footer-btn"
            onClick={() => window.location.href = '/profile'}
            aria-label="Profile Settings"
            title="Profile Settings"
          >
            <Settings size={15} />
          </button>
          <button
            className="footer-btn logout"
            onClick={logout}
            aria-label="Logout"
            title="Logout"
          >
            <LogOut size={15} />
          </button>
        </div>
      </div>
    </aside>
  );
};

export default Sidebar;
