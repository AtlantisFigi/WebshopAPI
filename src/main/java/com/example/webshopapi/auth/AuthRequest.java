package com.example.webshopapi.auth;

public record AuthRequest(
        String email,
        String password
) {

}