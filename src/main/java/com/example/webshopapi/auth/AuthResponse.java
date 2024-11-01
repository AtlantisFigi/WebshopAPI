package com.example.webshopapi.auth;

import com.example.webshopapi.user.UserResponseDto;

public record AuthResponse(
        String token,
        UserResponseDto userDto
) {

}
