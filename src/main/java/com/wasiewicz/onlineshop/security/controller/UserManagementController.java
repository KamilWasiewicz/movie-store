package com.wasiewicz.onlineshop.security.controller;

import com.wasiewicz.onlineshop.security.model.AuthenticationRequest;
import com.wasiewicz.onlineshop.security.model.AuthenticationResponse;
import com.wasiewicz.onlineshop.security.model.RegisterRequest;
import com.wasiewicz.onlineshop.security.repository.UserDTO;
import com.wasiewicz.onlineshop.security.service.AuthenticationService;
import com.wasiewicz.onlineshop.security.service.LogoutService;
import com.wasiewicz.onlineshop.security.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@CrossOrigin
public class UserManagementController {
    private final AuthenticationService authenticationService;
    private final UserService userService;
    private final LogoutService logoutService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }
    @PostMapping("/logout")
    public ResponseEntity<AuthenticationResponse> logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        logoutService.logout(request, response, authentication);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/users")
    public List<UserDTO> getUsers(){
        return userService.getUsers();
    }
}
