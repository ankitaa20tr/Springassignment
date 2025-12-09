package com.ankita.Rider.controller;

import com.ankita.Rider.dto.CreateRideRequest;
import com.ankita.Rider.model.Ride;
import com.ankita.Rider.service.RideService;
import com.ankita.Rider.util.RoleUtil;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/rides")
public class RideController {

    private final RideService rideService;

    public RideController(RideService rideService) {
        this.rideService = rideService;
    }

    // USER requests a ride
    @PostMapping
    public ResponseEntity<?> createRide(@Valid @RequestBody CreateRideRequest req, Authentication auth) {

        if (auth == null) {
            return ResponseEntity.status(401).body(Map.of("error", "UNAUTHORIZED", "message", "Authentication required"));
        }

        if (!RoleUtil.isUser(auth)) {
            return ResponseEntity.status(403).body(Map.of("error", "FORBIDDEN", "message", "Only USER role can request rides"));
        }

        Ride r = new Ride();
        r.setPickUpLocation(req.getPickupLocation());
        r.setDropOffLocation(req.getDropLocation());
        r.setUserId((String) auth.getPrincipal());

        return ResponseEntity.ok(rideService.createRide(r));
    }

    // USER or DRIVER completes a ride
    @PostMapping("/{rideId}/complete")
    public ResponseEntity<?> completeRide(@PathVariable String rideId, Authentication auth) {
        return ResponseEntity.ok(rideService.completeRide(rideId));
    }
}