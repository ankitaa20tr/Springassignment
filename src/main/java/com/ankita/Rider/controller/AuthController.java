package com.ankita.Rider.controller;


import com.ankita.Rider.config.JwtUtil;
import com.ankita.Rider.dto.AuthResponse;
import com.ankita.Rider.dto.LoginRequest;
import com.ankita.Rider.dto.RegisterRequest;
import com.ankita.Rider.model.User;
import com.ankita.Rider.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder encoder;

    public AuthController(UserService userService, JwtUtil jwtUtil, BCryptPasswordEncoder enc) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.encoder = enc;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest req) {
        User saved = userService.register(req);
        return ResponseEntity.ok(saved);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest req) {
        User u = userService.findByUsername(req.getUsername())
                .orElse(null);
        if (u == null || !encoder.matches(req.getPassword(), u.getPassword())) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
        String token = jwtUtil.generateToken(u.getUsername(), u.getId(), u.getRole());
        return ResponseEntity.ok(new AuthResponse(token));
    }
}