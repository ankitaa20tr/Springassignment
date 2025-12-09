package com.ankita.Rider.service;

import com.ankita.Rider.exception.BadRequestException;
import com.ankita.Rider.exception.NotFoundException;
import com.ankita.Rider.model.Ride;
import com.ankita.Rider.repo.RideRepository;
import com.ankita.Rider.util.Constants;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RideService {

    private final RideRepository rideRepository;

    public RideService(RideRepository rideRepository) {
        this.rideRepository = rideRepository;
    }

    // Create ride (USER)
    public Ride createRide(Ride ride) {
        ride.setStatus(Constants.REQUESTED);
        return rideRepository.save(ride);
    }

    // For DRIVER: list all pending
    public List<Ride> getPendingRequests() {
        return rideRepository.findByStatus(Constants.REQUESTED);
    }

    // DRIVER accepts ride
    public Ride acceptRide(String rideId, String driverId) {

        Ride r = rideRepository.findById(rideId)
                .orElseThrow(() -> new NotFoundException("Ride not found"));

        if (!Constants.REQUESTED.equals(r.getStatus())) {
            throw new BadRequestException("Ride is not in REQUESTED status");
        }

        r.setDriverId(driverId);
        r.setStatus(Constants.ACCEPTED);

        return rideRepository.save(r);
    }

    // USER or DRIVER completes ride
    public Ride completeRide(String rideId) {

        Ride r = rideRepository.findById(rideId)
                .orElseThrow(() -> new NotFoundException("Ride not found"));

        if (!Constants.ACCEPTED.equals(r.getStatus())) {
            throw new BadRequestException("Ride must be ACCEPTED to complete");
        }

        r.setStatus(Constants.COMPLETED);

        return rideRepository.save(r);
    }

    // USER: get their own rides
    public List<Ride> getRidesForUser(String userId) {
        return rideRepository.findByUserId(userId);
    }

    // DRIVER: get their accepted rides
    public List<Ride> getRidesForDriver(String driverId) {
        return rideRepository.findByDriverId(driverId);
    }
}