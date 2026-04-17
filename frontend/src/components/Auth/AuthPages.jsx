import React, { useState } from 'react';
import { useAuth } from '../../context/AuthContext';
import { KiriButton, KiriTextField } from '../Shared/KiriUI';
import './Auth.css';

export const LoginPage = () => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const { login } = useAuth();
  const [loading, setLoading] = useState(false);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError('');
    try {
      const res = await login(email, password);
      if (res.success) {
        window.location.href = '/';
      } else {
        setError(res.message);
      }
    } catch (err) {
      setError('AUTHENTICATION_FAILED: Invalid credentials.');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="auth-page cinematic-fade">
      <div className="auth-container">
        <img src="/logo.svg" alt="Kiri Logo" className="auth-logo" />
        <div className="mono-label mono">SECURITY_GATEWAY</div>
        <h2 className="mono">Login_User</h2>
        <form onSubmit={handleSubmit}>
          <KiriTextField
            label="EMAIL_ADDRESS"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            placeholder="ENTER_EMAIL"
            type="email"
            required
          />
          <KiriTextField
            label="ACCESS_KEY"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            placeholder="ENTER_PASSWORD"
            type="password"
            required
          />
          {error && <div className="auth-error mono">{error.toUpperCase()}</div>}
          <KiriButton type="submit" isLoading={loading}>
            AUTHENTICATE_SESSION
          </KiriButton>
        </form>
        <div className="auth-links mono">
          NO_ACCOUNT? <a href="/register">INITIALIZE_NEW_ID</a>
        </div>
      </div>
    </div>
  );
};

export const RegisterPage = () => {
  const [formData, setFormData] = useState({ name: '', email: '', password: '' });
  const [error, setError] = useState('');
  const { register } = useAuth();
  const [loading, setLoading] = useState(false);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError('');
    try {
      const res = await register(formData.name, formData.email, formData.password);
      if (res.success) {
        window.location.href = '/';
      } else {
        setError(res.message);
      }
    } catch (err) {
      setError('REGISTRATION_FAILED: System overload.');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="auth-page cinematic-fade">
      <div className="auth-container">
        <img src="/logo.svg" alt="Kiri Logo" className="auth-logo" />
        <div className="mono-label mono">IDENTITY_PROVISIONING</div>
        <h2 className="mono">Create_Account</h2>
        <form onSubmit={handleSubmit}>
          <KiriTextField
            label="FULL_NAME"
            value={formData.name}
            onChange={(e) => setFormData({...formData, name: e.target.value})}
            placeholder="ENTER_NAME"
            required
          />
          <KiriTextField
            label="EMAIL_ADDRESS"
            value={formData.email}
            onChange={(e) => setFormData({...formData, email: e.target.value})}
            placeholder="ENTER_EMAIL"
            type="email"
            required
          />
          <KiriTextField
            label="ACCESS_KEY"
            value={formData.password}
            onChange={(e) => setFormData({...formData, password: e.target.value})}
            placeholder="ENTER_SECURE_PASSWORD"
            type="password"
            required
          />
          {error && <div className="auth-error mono">{error.toUpperCase()}</div>}
          <KiriButton type="submit" isLoading={loading}>
            PROVISION_ACCESS
          </KiriButton>
        </form>
        <div className="auth-links mono">
          EXISTING_USER? <a href="/login">RETURN_TO_GATEWAY</a>
        </div>
      </div>
    </div>
  );
};
