import React from 'react';
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../../context/AuthContext';
import { useTheme } from '../../context/ThemeContext';
import { LogOut, Plus, MessageSquare, Sun, Moon, Image as ImageIcon, Trash2 } from 'lucide-react';
import './Sidebar.css';

const Sidebar = ({ isOpen, onClose, conversations, currentId, onSelect, onNewChat, onDelete, onClearAll }) => {
  const navigate = useNavigate();
  const { user, logout } = useAuth();
  const { isDarkMode, toggleTheme } = useTheme();

  return (
    <aside className={`sidebar ${isOpen ? '' : 'off'}`} role="navigation" aria-label="Chat history">
      <div className="sb-hd">
        <div className="sb-brand">
          <div className="sb-brand-icon">
            <svg viewBox="0 0 24 24" width="14" height="14">
              <path
                d="M12 2L14.09 8.26L21 9.27L16 14.14L17.18 21.02L12 17.77L6.82 21.02L8 14.14L3 9.27L9.91 8.26L12 2Z"
                fill="white"
              />
            </svg>
          </div>
          <span className="sb-brand-name">Kiri AI</span>
        </div>
        <button className="nc-btn" onClick={onNewChat} aria-label="New chat">
          <Plus size={14} />
          <span>New session</span>
        </button>
      </div>

      <div className="sidebar-modes">
        <div className="sb-section-label">Operation Modes</div>
        <div className="mode-item active">
          <MessageSquare size={14} /> <span>CORE_CHAT</span>
        </div>
        <div className="mode-item" onClick={() => navigate('/image-lab')}>
          <ImageIcon size={14} /> <span>IMAGE_LAB</span>
        </div>
      </div>

      <div className="sb-section-label">Recent Logs</div>
      <div className="sb-list">
        <div className="conversation-list">
          {conversations.map(conv => (
            <div
              key={conv.id}
              className={`cv ${conv.id === currentId ? 'act' : ''}`}
              onClick={() => onSelect(conv.id)}
            >
              <MessageSquare size={14} className="ci" />
              <span className="ct">{conv.title || 'Untitled log'}</span>
              <button
                className="cd"
                onClick={(e) => {
                  e.stopPropagation();
                  onDelete(conv.id);
                }}
                aria-label="Delete chat"
                title="Delete chat"
              >
                <Trash2 size={12} />
              </button>
            </div>
          ))}
        </div>
      </div>

      <div className="sidebar-footer">
        <div className="user-profile" onClick={() => navigate('/profile')}>
          <div className="avatar micro">{user?.name?.charAt(0).toUpperCase() || 'U'}</div>
          <div className="user-info">
            <div className="user-name">{user?.name || 'USER_NULL'}</div>
            <div className="user-plan">{user?.plan?.toUpperCase()}</div>
          </div>
        </div>

        <div className="sb-ft">
          <button
            className="sf-btn"
            onClick={toggleTheme}
            aria-label="Toggle theme"
            title="Toggle theme"
          >
            {isDarkMode ? <Sun size={15} /> : <Moon size={15} />}
          </button>
          <div className="sf-sp"></div>
          <button
            className="sf-btn"
            onClick={onClearAll}
            aria-label="Clear all chats"
            title="Clear all chats"
          >
            <Trash2 size={15} />
          </button>
          <button
            className="sf-btn logout"
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
