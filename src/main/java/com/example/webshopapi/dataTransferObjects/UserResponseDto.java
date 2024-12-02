package com.example.webshopapi.dataTransferObjects;

public record UserResponseDto(
        String firstName,
        String lastName,
        String prefix,
        String email
) {
}
