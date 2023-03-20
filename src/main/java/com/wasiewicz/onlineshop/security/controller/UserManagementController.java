package com.wasiewicz.onlineshop.security.controller;

import com.wasiewicz.onlineshop.security.model.AuthenticationRequest;
import com.wasiewicz.onlineshop.security.model.AuthenticationResponse;
import com.wasiewicz.onlineshop.security.model.RegisterRequest;
import com.wasiewicz.onlineshop.security.repository.UserDTO;
import com.wasiewicz.onlineshop.security.service.AuthenticationService;
import com.wasiewicz.onlineshop.security.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class UserManagementController {
    private final AuthenticationService authenticationService;
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @GetMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }
    @GetMapping("/users")
    public List<UserDTO> getUsers(){
        return userService.getUsers();
    }
}
