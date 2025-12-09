# RideShare Application

A full-stack ride-sharing application built with Spring Boot (backend) and React (frontend).

## Features

### Backend (Spring Boot)
- ✅ User Registration & Login with JWT Authentication
- ✅ Role-based access control (ROLE_USER, ROLE_DRIVER)
- ✅ Ride Management:
  - Users can request rides
  - Drivers can view and accept pending ride requests
  - Users and Drivers can complete rides
  - Users can view their ride history
  - Drivers can view their accepted rides
- ✅ Input Validation using Jakarta Validation
- ✅ Global Exception Handling
- ✅ MongoDB for data persistence
- ✅ CORS configured for frontend integration

### Frontend (React + Vite)
- ✅ Modern, responsive UI
- ✅ User Authentication (Login/Register)
- ✅ User Dashboard:
  - Request new rides
  - View ride history
  - Complete accepted rides
- ✅ Driver Dashboard:
  - View pending ride requests
  - Accept rides
  - View accepted rides
  - Complete rides

## Prerequisites

- Java 17 or higher
- Maven 3.6+
- MongoDB (running on localhost:27017)
- Node.js 16+ and npm

## Setup Instructions

### 1. MongoDB Setup

Make sure MongoDB is running on your local machine:

```bash
# On macOS with Homebrew
brew services start mongodb-community

# Or using Docker
docker run -d -p 27017:27017 --name mongodb mongo:latest
```

### 2. Backend Setup

1. Navigate to the project root directory
2. The backend is already configured. Update `application.properties` if needed:
   - MongoDB URI: `mongodb://localhost:27017/rideshare`
   - Server port: `8081`
   - JWT secret and expiration are configured

3. Run the Spring Boot application:
```bash
# Using Maven wrapper
./mvnw spring-boot:run

# Or using Maven
mvn spring-boot:run
```

The backend will start on `http://localhost:8081`

### 3. Frontend Setup

1. Navigate to the frontend directory:
```bash
cd frontend
```

2. Install dependencies (if not already done):
```bash
npm install
```

3. Start the development server:
```bash
npm run dev
```

The frontend will start on `http://localhost:5173` (or another port if 5173 is busy)

## API Endpoints

### Public Endpoints
- `POST /api/auth/register` - Register a new user
- `POST /api/auth/login` - Login and get JWT token

### User Endpoints (Requires ROLE_USER)
- `POST /api/v1/rides` - Request a new ride
- `GET /api/v1/user/rides` - Get user's ride history
- `POST /api/v1/rides/{rideId}/complete` - Complete a ride

### Driver Endpoints (Requires ROLE_DRIVER)
- `GET /api/v1/driver/rides/requests` - View pending ride requests
- `POST /api/v1/driver/rides/{rideId}/accept` - Accept a ride
- `GET /api/v1/driver/rides/my-rides` - View driver's accepted rides
- `POST /api/v1/rides/{rideId}/complete` - Complete a ride

## Usage Example

1. **Register a User:**
   - Go to `/register`
   - Enter username, password, and select "User (Passenger)"
   - Click Register

2. **Register a Driver:**
   - Go to `/register`
   - Enter username, password, and select "Driver"
   - Click Register

3. **Login:**
   - Go to `/login`
   - Enter credentials
   - You'll be redirected to the appropriate dashboard

4. **As a User:**
   - Request a ride by entering pickup and drop locations
   - View your ride history
   - Complete rides that have been accepted by drivers

5. **As a Driver:**
   - View pending ride requests
   - Accept rides you want to take
   - Complete rides after the trip is finished

## Project Structure

```
Rider 2/
├── src/
│   └── main/
│       ├── java/com/ankita/Rider/
│       │   ├── config/          # Security, JWT, CORS configs
│       │   ├── controller/      # REST controllers
│       │   ├── dto/             # Data Transfer Objects
│       │   ├── exception/      # Exception handlers
│       │   ├── model/          # Entity models
│       │   ├── repo/           # MongoDB repositories
│       │   ├── service/        # Business logic
│       │   └── util/           # Utility classes
│       └── resources/
│           └── application.properties
├── frontend/
│   ├── src/
│   │   ├── components/         # React components
│   │   ├── context/            # Auth context
│   │   ├── services/           # API service
│   │   └── App.jsx             # Main app component
│   └── package.json
└── README.md
```

## Technologies Used

### Backend
- Spring Boot 4.0.0
- Spring Security
- Spring Data MongoDB
- JWT (JSON Web Tokens)
- Jakarta Validation
- Maven

### Frontend
- React 18
- Vite
- React Router DOM
- Axios
- Modern CSS

## Notes

- JWT tokens are stored in localStorage
- Tokens expire after 24 hours (configurable in `application.properties`)
- Passwords are encrypted using BCrypt
- CORS is configured to allow requests from `http://localhost:5173`

## Troubleshooting

1. **MongoDB Connection Error:**
   - Ensure MongoDB is running
   - Check the connection string in `application.properties`

2. **CORS Errors:**
   - Verify the frontend URL matches the allowed origins in `CorsConfig.java`
   - Default: `http://localhost:5173`

3. **JWT Token Issues:**
   - Clear localStorage and login again
   - Check JWT secret in `application.properties`

4. **Port Already in Use:**
   - Backend: Change `server.port` in `application.properties`
   - Frontend: Vite will automatically use the next available port

## License

This project is for educational purposes.

