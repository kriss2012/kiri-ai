import React from 'react';
import './Shared.css';

export const KiriButton = ({ children, onClick, type = 'button', variant = 'primary', isLoading, disabled, className = '' }) => {
  return (
    <button
      type={type}
      className={`kiri-button ${variant} ${className}`}
      onClick={onClick}
      disabled={isLoading || disabled}
    >
      {isLoading ? 'PROCESSING...' : children}
    </button>
  );
};

export const KiriTextField = ({ label, value, onChange, placeholder, type = 'text', required }) => {
  return (
    <div className="kiri-input-group">
      {label && <label className="kiri-label">{label.toUpperCase()}</label>}
      <input
        type={type}
        className="kiri-input"
        value={value}
        onChange={onChange}
        placeholder={placeholder}
        required={required}
      />
    </div>
  );
};
