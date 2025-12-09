package com.ankita.Rider.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RegisterRequest {
    @NotBlank(message = "Username is required")
    @Size(min = 3)
    private String username;
    @NotBlank(message = "Password is required")
    @Size(min = 4)
    private String password;
    @NotBlank(message = "Role is required")
    private String role;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() { return username; }

    public void setUsername(String username) {
        this.username = username;
    }

}
