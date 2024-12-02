package com.example.webshopapi.dataTransferObjects;

public record AuthResponse(
        String token,
        UserResponseDto userDto
) {

}
