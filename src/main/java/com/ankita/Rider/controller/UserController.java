package com.ankita.Rider.controller;

import com.ankita.Rider.model.Ride;
import com.ankita.Rider.service.RideService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final RideService rideService;

    public UserController(RideService rideService) {
        this.rideService = rideService;
    }

    // USER views their ride history
    @GetMapping("/rides")
    public ResponseEntity<?> myRides(Authentication auth) {

        String userId = (String) auth.getPrincipal();
        List<Ride> rides = rideService.getRidesForUser(userId);

        return ResponseEntity.ok(rides);
    }
}