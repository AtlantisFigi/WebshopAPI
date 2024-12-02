package com.example.webshopapi.services;

import com.example.webshopapi.dataTransferObjects.AuthRequest;
import com.example.webshopapi.dataTransferObjects.AuthResponse;
import com.example.webshopapi.dataTransferObjects.UserRegistrationDTO;

public interface UserService {
    boolean registerUser(UserRegistrationDTO userRegistrationDTO);
    AuthResponse authenticateUser(AuthRequest authRequest);
}