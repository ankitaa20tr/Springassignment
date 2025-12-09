import { useState, useEffect } from 'react';
import { useAuth } from '../context/AuthContext';
import api from '../services/api';

const UserDashboard = () => {
  const { user, logout, isUser } = useAuth();
  const [pickupLocation, setPickupLocation] = useState('');
  const [dropLocation, setDropLocation] = useState('');
  const [rides, setRides] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');

  useEffect(() => {
    loadRides();
    // Check if user has correct role
    if (user && !isUser()) {
      setError('You must be logged in as a USER to request rides. Current role: ' + user.role);
    }
  }, [user, isUser]);

  const loadRides = async () => {
    try {
      const response = await api.get('/v1/user/rides');
      setRides(response.data);
    } catch (err) {
      console.error('Failed to load rides:', err);
    }
  };

  const handleRequestRide = async (e) => {
    e.preventDefault();
    setError('');
    setLoading(true);

    try {
      await api.post('/v1/rides', {
        pickupLocation,
        dropLocation,
      });
      setPickupLocation('');
      setDropLocation('');
      await loadRides();
    } catch (err) {
      console.error('Ride request error:', err);
      console.error('Error response:', err.response);
      console.error('Error data:', err.response?.data);
      
      let errorMessage = 'Failed to request ride';
      
      if (err.response) {
        // Handle different response formats
        if (typeof err.response.data === 'string') {
          errorMessage = err.response.data;
        } else if (err.response.data?.message) {
          errorMessage = err.response.data.message;
        } else if (err.response.data?.error) {
          errorMessage = err.response.data.error;
        } else if (err.response.data) {
          errorMessage = JSON.stringify(err.response.data);
        }
      } else if (err.message) {
        errorMessage = err.message;
      }
      
      setError(errorMessage);
    } finally {
      setLoading(false);
    }
  };

  const handleCompleteRide = async (rideId) => {
    try {
      await api.post(`/v1/rides/${rideId}/complete`);
      await loadRides();
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
        <h1>User Dashboard</h1>
        <div className="user-info">
          <span>Welcome, {user?.username}</span>
          <button onClick={logout} className="btn-secondary">Logout</button>
        </div>
      </div>

      <div className="dashboard-content">
        <div className="card">
          <h2>Request a Ride</h2>
          {error && <div className="error-message">{error}</div>}
          <form onSubmit={handleRequestRide}>
            <div className="form-group">
              <label>Pickup Location</label>
              <input
                type="text"
                value={pickupLocation}
                onChange={(e) => setPickupLocation(e.target.value)}
                required
                placeholder="Enter pickup location"
              />
            </div>
            <div className="form-group">
              <label>Drop Location</label>
              <input
                type="text"
                value={dropLocation}
                onChange={(e) => setDropLocation(e.target.value)}
                required
                placeholder="Enter drop location"
              />
            </div>
            <button type="submit" disabled={loading} className="btn-primary">
              {loading ? 'Requesting...' : 'Request Ride'}
            </button>
          </form>
        </div>

        <div className="card">
          <h2>My Rides</h2>
          {rides.length === 0 ? (
            <p className="no-data">No rides yet. Request a ride to get started!</p>
          ) : (
            <div className="rides-list">
              {rides.map((ride) => (
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
                    {ride.driverId && (
                      <div>
                        <strong>Driver ID:</strong> {ride.driverId}
                      </div>
                    )}
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

export default UserDashboard;

