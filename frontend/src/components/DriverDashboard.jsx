import { useState, useEffect } from 'react';
import { useAuth } from '../context/AuthContext';
import api from '../services/api';

const DriverDashboard = () => {
  const { user, logout } = useAuth();
  const [pendingRides, setPendingRides] = useState([]);
  const [myRides, setMyRides] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');

  useEffect(() => {
    loadPendingRides();
    loadMyRides();
  }, []);

  const loadPendingRides = async () => {
    try {
      const response = await api.get('/v1/driver/rides/requests');
      setPendingRides(response.data);
    } catch (err) {
      console.error('Failed to load pending rides:', err);
    }
  };

  const loadMyRides = async () => {
    try {
      const response = await api.get('/v1/driver/rides/my-rides');
      setMyRides(response.data);
    } catch (err) {
      console.error('Failed to load my rides:', err);
    }
  };

  const handleAcceptRide = async (rideId) => {
    setError('');
    setLoading(true);

    try {
      await api.post(`/v1/driver/rides/${rideId}/accept`);
      await loadPendingRides();
      await loadMyRides();
    } catch (err) {
      setError(err.response?.data?.message || 'Failed to accept ride');
    } finally {
      setLoading(false);
    }
  };

  const handleCompleteRide = async (rideId) => {
    try {
      await api.post(`/v1/rides/${rideId}/complete`);
      await loadMyRides();
      await loadPendingRides();
    } catch (err) {
      setError(err.response?.data?.message || 'Failed to complete ride');
    }
  };

  const getStatusColor = (status) => {
    switch (status) {
      case 'REQUESTED':
        return 'status-requested';
      case 'ACCEPTED':
        return 'status-accepted';
      case 'COMPLETED':
        return 'status-completed';
      default:
        return '';
    }
  };

  return (
    <div className="dashboard-container">
      <div className="dashboard-header">
        <h1>Driver Dashboard</h1>
        <div className="user-info">
          <span>Welcome, {user?.username}</span>
          <button onClick={logout} className="btn-secondary">Logout</button>
        </div>
      </div>

      {error && <div className="error-message">{error}</div>}

      <div className="dashboard-content">
        <div className="card">
          <h2>Pending Ride Requests</h2>
          {pendingRides.length === 0 ? (
            <p className="no-data">No pending ride requests</p>
          ) : (
            <div className="rides-list">
              {pendingRides.map((ride) => (
                <div key={ride.id} className="ride-item">
                  <div className="ride-info">
                    <div>
                      <strong>From:</strong> {ride.pickUpLocation}
                    </div>
                    <div>
                      <strong>To:</strong> {ride.dropOffLocation}
                    </div>
                    <div>
                      <strong>Passenger ID:</strong> {ride.userId}
                    </div>
                    <div>
                      <strong>Created:</strong>{' '}
                      {new Date(ride.createdAt).toLocaleString()}
                    </div>
                  </div>
                  <button
                    onClick={() => handleAcceptRide(ride.id)}
                    disabled={loading}
                    className="btn-primary"
                  >
                    Accept Ride
                  </button>
                </div>
              ))}
            </div>
          )}
        </div>

        <div className="card">
          <h2>My Accepted Rides</h2>
          {myRides.length === 0 ? (
            <p className="no-data">No accepted rides yet</p>
          ) : (
            <div className="rides-list">
              {myRides.map((ride) => (
                <div key={ride.id} className="ride-item">
                  <div className="ride-info">
                    <div>
                      <strong>From:</strong> {ride.pickUpLocation}
                    </div>
                    <div>
                      <strong>To:</strong> {ride.dropOffLocation}
                    </div>
                    <div>
                      <strong>Status:</strong>{' '}
                      <span className={`status-badge ${getStatusColor(ride.status)}`}>
                        {ride.status}
                      </span>
                    </div>
                    <div>
                      <strong>Passenger ID:</strong> {ride.userId}
                    </div>
                    <div>
                      <strong>Created:</strong>{' '}
                      {new Date(ride.createdAt).toLocaleString()}
                    </div>
                  </div>
                  {ride.status === 'ACCEPTED' && (
                    <button
                      onClick={() => handleCompleteRide(ride.id)}
                      className="btn-success"
                    >
                      Complete Ride
                    </button>
                  )}
                </div>
              ))}
            </div>
          )}
        </div>
      </div>
    </div>
  );
};

export default DriverDashboard;

