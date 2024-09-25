package com.example.webshopapi.user;

public record UserRegistrationDTO(
        String firstName,
        String lastName,
        String prefix,
        String email,
        String password
){

}