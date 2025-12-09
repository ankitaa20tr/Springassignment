package com.ankita.Rider.repo;


import com.ankita.Rider.model.Ride;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface RideRepository extends MongoRepository<Ride, String> {
    List<Ride> findByStatus(String status);
    List<Ride> findByUserId(String userId);
    List<Ride> findByDriverId(String driverId);
}
