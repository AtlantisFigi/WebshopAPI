package com.example.webshopapi.user;

import com.example.webshopapi.auth.AuthRequest;

public interface UserService {
    boolean registerUser(UserRegistrationDTO userRegistrationDTO);
    String authenticateUser(AuthRequest authRequest);
}