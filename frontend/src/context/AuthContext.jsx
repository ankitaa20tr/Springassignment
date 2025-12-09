import { createContext, useContext, useState, useEffect } from 'react';
import api from '../services/api';

const AuthContext = createContext(null);

export const useAuth = () => {
  const context = useContext(AuthContext);
  if (!context) {
    throw new Error('useAuth must be used within AuthProvider');
  }
  return context;
};

export const AuthProvider = ({ children }) => {
  const [user, setUser] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const token = localStorage.getItem('token');
    if (token) {
      // Decode token to get user info (simple base64 decode)
      try {
        const payload = JSON.parse(atob(token.split('.')[1]));
        setUser({
          username: payload.sub,
          userId: payload.userId,
          role: payload.role,
        });
      } catch (e) {
        localStorage.removeItem('token');
      }
    }
    setLoading(false);
  }, []);

  const login = async (username, password) => {
    try {
      const response = await api.post('/auth/login', { username, password });
      const token = response.data.token;
      localStorage.setItem('token', token);
      
      const payload = JSON.parse(atob(token.split('.')[1]));
      setUser({
        username: payload.sub,
        userId: payload.userId,
        role: payload.role,
      });
      return { success: true };
    } catch (error) {
      return { 
        success: false, 
        error: error.response?.data?.message || 'Login failed' 
      };
    }
  };

  const register = async (username, password, role) => {
    try {
      await api.post('/auth/register', { username, password, role });
      return { success: true };
    } catch (error) {
      return { 
        success: false, 
        error: error.response?.data?.message || 'Registration failed' 
      };
    }
  };

  const logout = () => {
    localStorage.removeItem('token');
    setUser(null);
  };

  const isUser = () => user?.role === 'ROLE_USER';
  const isDriver = () => user?.role === 'ROLE_DRIVER';

  return (
    <AuthContext.Provider value={{ user, login, register, logout, loading, isUser, isDriver }}>
      {children}
    </AuthContext.Provider>
  );
};

