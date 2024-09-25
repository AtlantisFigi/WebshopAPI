package com.example.webshopapi.product;

public record ProductDTO(
        Integer id,
        String name,
        String description,
        Double price
) {

}
