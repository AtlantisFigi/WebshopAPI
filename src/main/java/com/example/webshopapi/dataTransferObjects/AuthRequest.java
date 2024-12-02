package com.example.webshopapi.dataTransferObjects;

public record AuthRequest(
        String email,
        String password
) {

}