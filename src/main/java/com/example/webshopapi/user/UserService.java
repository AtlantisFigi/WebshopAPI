package com.example.webshopapi.user;

import com.example.webshopapi.auth.AuthRequest;
import com.example.webshopapi.auth.AuthResponse;

public interface UserService {
    boolean registerUser(UserRegistrationDTO userRegistrationDTO);
    AuthResponse authenticateUser(AuthRequest authRequest);
}