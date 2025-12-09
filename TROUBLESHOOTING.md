# Troubleshooting Ride Request Issues

## Common Issues and Solutions

### Issue: "Failed to request ride" Error

**Possible Causes:**

1. **User doesn't have ROLE_USER role**
   - If you registered as a DRIVER, you cannot request rides
   - Solution: Register a new account with role "ROLE_USER" or login with a user account

2. **JWT Token Issues**
   - Token might be expired or invalid
   - Solution: Logout and login again

3. **Backend not running**
   - Check if Spring Boot application is running on port 8081
   - Solution: Start the backend with `./mvnw spring-boot:run`

4. **CORS Issues**
   - Frontend and backend might not be communicating
   - Solution: Check browser console for CORS errors

## How to Debug

1. **Check Browser Console**
   - Open Developer Tools (F12)
   - Look for error messages in the Console tab
   - Check the Network tab to see the actual API response

2. **Check User Role**
   - In the browser console, type: `localStorage.getItem('token')`
   - Decode the token at https://jwt.io to see the role
   - Or check the "Welcome, [username]" message - it should show your username

3. **Test API Directly**
   ```bash
   # First, login and get token
   curl -X POST http://localhost:8081/api/auth/login \
     -H "Content-Type: application/json" \
     -d '{"username":"your_username","password":"your_password"}'
   
   # Then use the token to request a ride
   curl -X POST http://localhost:8081/api/v1/rides \
     -H "Content-Type: application/json" \
     -H "Authorization: Bearer YOUR_TOKEN_HERE" \
     -d '{"pickupLocation":"Location A","dropLocation":"Location B"}'
   ```

4. **Check Backend Logs**
   - Look for error messages in the Spring Boot console
   - Check for authentication/authorization errors

## Quick Fixes

1. **Clear localStorage and re-login**
   ```javascript
   // In browser console
   localStorage.clear()
   // Then refresh and login again
   ```

2. **Verify MongoDB is running**
   ```bash
   # Check if MongoDB is running
   mongosh
   # Or
   brew services list  # on macOS
   ```

3. **Restart both frontend and backend**
   - Stop both servers (Ctrl+C)
   - Start backend: `./mvnw spring-boot:run`
   - Start frontend: `cd frontend && npm run dev`


