import React from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import { AuthProvider, useAuth } from './context/AuthContext';
import { ThemeProvider } from './context/ThemeContext';
import ChatPage from './components/Chat/ChatPage';
import { LoginPage, RegisterPage } from './components/Auth/AuthPages';
import PricingPage from './pages/PricingPage';
import ProfilePage from './pages/ProfilePage';
import './index.css';

const ProtectedRoute = ({ children }) => {
  const { token, loading } = useAuth();
  
  if (loading) return (
    <div className="system-loader bg-ivory">
      <div className="mono">INITIALIZING_CORE_SERVICES...</div>
    </div>
  );
  
  if (!token) return <Navigate to="/login" />;
  
  return children;
};

const App = () => {
  return (
    <ThemeProvider>
      <AuthProvider>
        <Router>
          <Routes>
            <Route path="/login" element={<LoginPage />} />
            <Route path="/register" element={<RegisterPage />} />
            
            <Route path="/" element={
              <ProtectedRoute>
                <ChatPage />
              </ProtectedRoute>
            } />

            <Route path="/pricing" element={
              <ProtectedRoute>
                <PricingPage />
              </ProtectedRoute>
            } />

            <Route path="/profile" element={
              <ProtectedRoute>
                <ProfilePage />
              </ProtectedRoute>
            } />

            <Route path="*" element={<Navigate to="/" />} />
          </Routes>
        </Router>
      </AuthProvider>
    </ThemeProvider>
  );
};

export default App;
