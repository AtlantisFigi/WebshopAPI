package com.example.webshopapi.dataTransferObjects;

import java.util.List;

public record ProductDTO(
        int id,
        String name,
        String description,
        double price,
        List<String> categories,
        int quantity
) {

}