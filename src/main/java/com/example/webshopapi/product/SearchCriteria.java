package com.example.webshopapi.product;

import java.util.List;

public record SearchCriteria(
        String name,
        List<String> categories,
        int minPrice,
        int maxPrice
) {
}
