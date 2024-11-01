package com.example.webshopapi.product;

import java.util.List;

public record ProductDTO(
        int id,
        String name,
        String description,
        double price,
        List<String> images
) {

}
