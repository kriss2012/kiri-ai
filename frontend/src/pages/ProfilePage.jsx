import React, { useState } from 'react';
import { useAuth } from '../context/AuthContext';
import { KiriButton, KiriTextField } from '../components/Shared/KiriUI';
import { ArrowLeft, User, Shield, CreditCard, AlertTriangle, Github } from 'lucide-react';
import api from '../utils/api';
import './Profile.css';

const ProfilePage = () => {
  const { user, updateProfile, logout } = useAuth();
  const [activeTab, setActiveTab] = useState('identity');
  const [name, setName] = useState(user?.name || '');
  const [passForm, setPassForm] = useState({ current: '', next: '', confirm: '' });
  const [saving, setSaving] = useState(false);
  const [error, setError] = useState('');
  const [success, setSuccess] = useState('');

  const handleUpdate = async (e) => {
    e.preventDefault();
    setSaving(true);
    setError('');
    setSuccess('');
    try {
      await updateProfile({ name });
      setSuccess('PROFILE_SYNC_SUCCESS: Identity updated.');
    } catch (err) {
      setError('SYNC_ERROR: Failed to update profile.');
    } finally {
      setSaving(false);
    }
  };

  const handleChangePassword = async (e) => {
    e.preventDefault();
    if (passForm.next !== passForm.confirm) {
      return setError('VALIDATION_ERROR: Passwords do not match.');
    }
    setSaving(true);
    setError('');
    setSuccess('');
    try {
      const res = await api.put('/auth/change-password', {
        currentPassword: passForm.current,
        newPassword: passForm.next
      });
      if (res.data.success) {
        setSuccess('SECURITY_CORE_UPDATED: Password changed.');
        setPassForm({ current: '', next: '', confirm: '' });
      }
    } catch (err) {
      setError(err.response?.data?.message || 'SYNC_ERROR: Current password incorrect.');
    } finally {
      setSaving(false);
    }
  };

  const handleDeleteAccount = async () => {
    if (window.confirm('CRITICAL_WARNING: This action is IRREVERSIBLE. All neural logs and subscription data will be PURGED. Continue?')) {
      try {
        const res = await api.delete('/auth/account');
        if (res.data.success) {
          logout();
          window.location.href = '/login';
        }
      } catch (err) {
        alert('SYSTEM_ERROR: Could not terminate account.');
      }
    }
  };

  return (
    <div className="profile-page cinematic-fade">
      <header className="profile-header">
        <button className="back-btn" onClick={() => window.location.href = '/'}>
          <ArrowLeft size={20} />
        </button>
        <div className="mono-label mono">USER_MANIFEST</div>
      </header>

      <div className="profile-content">
        <div className="profile-sidebar">
          <div className="profile-avatar-large">
            {user?.name?.charAt(0).toUpperCase()}
          </div>
          <h2 className="mono">{user?.name?.toUpperCase()}</h2>
          <div className="user-badge mono">{user?.plan?.toUpperCase()}</div>
          
          <nav className="profile-nav">
            <div 
              className={`nav-item mono ${activeTab === 'identity' ? 'active' : ''}`}
              onClick={() => setActiveTab('identity')}
            >
              <User size={18} /> IDENTITY
            </div>
            <div 
              className={`nav-item mono ${activeTab === 'subscription' ? 'active' : ''}`}
              onClick={() => setActiveTab('subscription')}
            >
              <CreditCard size={18} /> SUBSCRIPTION
            </div>
            <div 
              className={`nav-item mono ${activeTab === 'security' ? 'active' : ''}`}
              onClick={() => setActiveTab('security')}
            >
              <Shield size={18} /> SECURITY
            </div>
          </nav>

          <KiriButton variant="outline" onClick={logout} className="logout-btn">
            TERMINATE_SESSION
          </KiriButton>
        </div>

        <div className="profile-main">
          {error && <div className="status-msg error mono">{error.toUpperCase()}</div>}
          {success && <div className="status-msg success mono">{success.toUpperCase()}</div>}

          {activeTab === 'identity' && (
            <section className="profile-section">
              <h3 className="mono">General_Settings</h3>
              <form onSubmit={handleUpdate}>
                <KiriTextField
                  label="DISPLAY_NAME"
                  value={name}
                  onChange={(e) => setName(e.target.value)}
                  placeholder="Name"
                />
                <KiriTextField
                  label="EMAIL_ADDRESS"
                  value={user?.email}
                  disabled
                />
                <KiriButton type="submit" isLoading={saving}>
                  SYNC_CHANGES
                </KiriButton>
              </form>
            </section>
          )}

          {activeTab === 'subscription' && (
            <section className="profile-section">
              <h3 className="mono">Plan_Details</h3>
              <div className="status-card">
                <div className="status-item">
                  <span className="mono label">CURRENT_PLAN:</span>
                  <span className="mono value">{user?.plan?.toUpperCase()}</span>
                </div>
                <div className="status-item">
                  <span className="mono label">STATUS:</span>
                  <span className="mono value active">{user?.subscriptionStatus?.toUpperCase()}</span>
                </div>
                {user?.subscriptionEndDate && (
                  <div className="status-item">
                    <span className="mono label">EXPIRY_LOG:</span>
                    <span className="mono value">{new Date(user.subscriptionEndDate).toLocaleDateString()}</span>
                  </div>
                )}
              </div>
              <KiriButton onClick={() => window.location.href = '/pricing'} className="upgrade-btn">
                {user?.plan === 'free' ? 'UPGRADE_TO_PREMIUM' : 'MANAGE_SUBSCRIPTION'}
              </KiriButton>
            </section>
          )}

          {activeTab === 'security' && (
            <section className="profile-section">
              <h3 className="mono">Access_Control</h3>
              <form onSubmit={handleChangePassword}>
                <KiriTextField
                  type="password"
                  label="CURRENT_ACCESS_KEY"
                  value={passForm.current}
                  onChange={(e) => setPassForm({...passForm, current: e.target.value})}
                  required
                />
                <KiriTextField
                  type="password"
                  label="NEW_ACCESS_KEY"
                  value={passForm.next}
                  onChange={(e) => setPassForm({...passForm, next: e.target.value})}
                  required
                />
                <KiriTextField
                  type="password"
                  label="CONFIRM_KEY"
                  value={passForm.confirm}
                  onChange={(e) => setPassForm({...passForm, confirm: e.target.value})}
                  required
                />
                <KiriButton type="submit" isLoading={saving}>
                  UPDATE_SECURITY_LAYER
                </KiriButton>
              </form>

              <div className="danger-zone">
                <h4 className="mono"><AlertTriangle size={16} /> DANGER_ZONE</h4>
                <p className="mono-desc">DESTRUCTIVE_ACTION: Initiating account deletion will immediately purge all data. This cannot be undone.</p>
                <button className="delete-acc-btn mono" onClick={handleDeleteAccount}>
                  PURGE_ACCOUNT_IDENTITY
                </button>
              </div>
            </section>
          )}
        </div>
      </div>
    </div>
  );
};

export default ProfilePage;
