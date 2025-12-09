package com.ankita.Rider.service;

import com.ankita.Rider.dto.RegisterRequest;
import com.ankita.Rider.exception.BadRequestException;
import com.ankita.Rider.model.User;
import com.ankita.Rider.repo.UserRepository;
import jakarta.validation.Valid;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = encoder;
    }

    public User register(@Valid RegisterRequest req) {

        userRepository.findByUsername(req.getUsername())
                .forEach(u -> { throw new BadRequestException("Username already exists"); });

        User u = new User();
        u.setUsername(req.getUsername());
        u.setPassword(passwordEncoder.encode(req.getPassword()));
        u.setRole(req.getRole());

        return userRepository.save(u);
    }

    public java.util.Optional<User> findByUsername(String username) {
        List<User> users = userRepository.findByUsername(username);
        return users.isEmpty() ? java.util.Optional.empty() : java.util.Optional.of(users.get(0));
    }
}
