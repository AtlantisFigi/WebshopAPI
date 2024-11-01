package com.example.webshopapi.user;

public record UserResponseDto(
        String firstName,
        String lastName,
        String prefix,
        String email
) {
}
