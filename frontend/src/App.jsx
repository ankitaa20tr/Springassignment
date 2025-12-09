import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import { useAuth } from './context/AuthContext';
import Login from './components/Login';
import Register from './components/Register';
import UserDashboard from './components/UserDashboard';
import DriverDashboard from './components/DriverDashboard';
import ProtectedRoute from './components/ProtectedRoute';
import './App.css';

function App() {
  const { user, loading } = useAuth();

  if (loading) {
    return <div className="loading">Loading...</div>;
  }

  return (
    <Router>
      <Routes>
        <Route path="/login" element={!user ? <Login /> : <Navigate to="/dashboard" replace />} />
        <Route path="/register" element={!user ? <Register /> : <Navigate to="/dashboard" replace />} />
        <Route
          path="/dashboard"
          element={
            user ? (
              user.role === 'ROLE_DRIVER' ? (
                <DriverDashboard />
              ) : (
                <UserDashboard />
              )
            ) : (
              <Navigate to="/login" replace />
            )
          }
        />
        <Route path="/" element={<Navigate to={user ? "/dashboard" : "/login"} replace />} />
      </Routes>
    </Router>
  );
}

export default App;
