package com.ankita.Rider.repo;


import com.ankita.Rider.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;


public interface UserRepository extends MongoRepository<User, String> {
    List<User> findByUsername(String username);
}