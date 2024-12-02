package com.example.webshopapi.controllers;

import com.example.webshopapi.dataTransferObjects.AuthRequest;
import com.example.webshopapi.dataTransferObjects.AuthResponse;
import com.example.webshopapi.dataTransferObjects.UserRegistrationDTO;
import com.example.webshopapi.services.UserService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthenticatorController {

    private final UserService userService;

    public AuthenticatorController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserRegistrationDTO userRegistrationDTO) {
        boolean isRegistered = userService.registerUser(userRegistrationDTO);

        if (isRegistered) {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        AuthResponse authResponse = userService.authenticateUser(authRequest);


        if (authResponse.token() != null) {
            ResponseCookie cookie = ResponseCookie.from("JWT", authResponse.token())
                    .httpOnly(true)
                    .path("/")
                    .maxAge(3600)
                    .sameSite("Lax")
                    .secure(false)
                .build();

            return ResponseEntity.ok()
                    .header(HttpHeaders.SET_COOKIE, cookie.toString())
                    .body(authResponse.userDto());

        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        ResponseCookie jwtCookie = ResponseCookie.from("JWT", "")
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(0)
                .sameSite("Lax")
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .build();
    }
}