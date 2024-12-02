package com.example.webshopapi.dataTransferObjects;

public record UserRegistrationDTO(
        String firstName,
        String lastName,
        String prefix,
        String email,
        String password
){

}