package com.example.webshopapi.auth;

import com.example.webshopapi.user.UserRegistrationDTO;
import com.example.webshopapi.user.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.antlr.v4.runtime.Token;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthenticatorController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenService jwtTokenService;

    public AuthenticatorController(
            UserService userService,
            AuthenticationManager authenticationManager,
            JwtTokenService jwtTokenService
    ) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtTokenService = jwtTokenService;
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
        String token = userService.authenticateUser(authRequest);

        if (token != null) {
            ResponseCookie cookie = ResponseCookie.from("JWT", token)
                    .httpOnly(true)
                    .path("/")
                    .maxAge(3600)
                    .sameSite("Lax")
                    .secure(false)
                .build();

            return ResponseEntity.ok()
                    .header(HttpHeaders.SET_COOKIE, cookie.toString())
                    .build();
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}