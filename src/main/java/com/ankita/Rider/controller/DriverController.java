package com.ankita.Rider.controller;

import com.ankita.Rider.model.Ride;
import com.ankita.Rider.service.RideService;
import com.ankita.Rider.util.RoleUtil;
import org.springframework.security.core.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/driver/rides")
public class DriverController {

    private final RideService rideService;

    public DriverController(RideService rideService) {
        this.rideService = rideService;
    }

    // DRIVER views all pending requests
    @GetMapping("/requests")
    public ResponseEntity<?> getPending(Authentication auth) {

        if (!RoleUtil.isDriver(auth))
            return ResponseEntity.status(403).body("Only DRIVER role can view pending rides");

        List<Ride> pending = rideService.getPendingRequests();
        return ResponseEntity.ok(pending);
    }

    // DRIVER accepts a ride
    @PostMapping("/{rideId}/accept")
    public ResponseEntity<?> accept(@PathVariable String rideId, Authentication auth) {

        if (!RoleUtil.isDriver(auth)) {
            return ResponseEntity.status(403).body("Only DRIVER role can accept rides");
        }

        String driverId = (String) auth.getPrincipal();
        Ride r = rideService.acceptRide(rideId, driverId);

        return ResponseEntity.ok(r);
    }

    // DRIVER views their accepted rides
    @GetMapping("/my-rides")
    public ResponseEntity<?> getMyRides(Authentication auth) {

        if (!RoleUtil.isDriver(auth)) {
            return ResponseEntity.status(403).body("Only DRIVER role can view their rides");
        }

        String driverId = (String) auth.getPrincipal();
        List<Ride> rides = rideService.getRidesForDriver(driverId);

        return ResponseEntity.ok(rides);
    }
}