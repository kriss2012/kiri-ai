import React, { useState } from 'react';
import { useAuth } from '../context/AuthContext';
import { KiriButton, KiriTextField } from '../components/Shared/KiriUI';
import { ArrowLeft, User, Shield, CreditCard } from 'lucide-react';
import './Profile.css';

const ProfilePage = () => {
  const { user, updateProfile, logout } = useAuth();
  const [name, setName] = useState(user?.name || '');
  const [saving, setSaving] = useState(false);

  const handleUpdate = async (e) => {
    e.preventDefault();
    setSaving(true);
    try {
      await updateProfile({ name });
      alert('PROFILE_SYNC_SUCCESS: Identity updated.');
    } catch (err) {
      alert('SYNC_ERROR: Failed to update profile.');
    } finally {
      setSaving(false);
    }
  };

  return (
    <div className="profile-page cinematic-fade">
      <header className="profile-header">
        <button className="back-btn" onClick={() => window.history.back()}>
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
            <div className="nav-item active mono"><User size={18} /> IDENTITY</div>
            <div className="nav-item mono" onClick={() => window.location.href = '/pricing'}><CreditCard size={18} /> SUBSCRIPTION</div>
            <div className="nav-item mono"><Shield size={18} /> SECURITY</div>
          </nav>

          <KiriButton variant="outline" onClick={logout} className="logout-btn">
            TERMINATE_SESSION
          </KiriButton>
        </div>

        <div className="profile-main">
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
            {user?.plan === 'free' && (
              <KiriButton onClick={() => window.location.href = '/pricing'} className="upgrade-btn">
                UPGRADE_TO_PREMIUM
              </KiriButton>
            )}
          </section>
        </div>
      </div>
    </div>
  );
};

export default ProfilePage;
