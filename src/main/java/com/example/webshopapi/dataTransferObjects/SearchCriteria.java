package com.example.webshopapi.dataTransferObjects;

import java.util.List;

public record SearchCriteria(
        String name,
        List<String> categories,
        int minPrice,
        int maxPrice
) {
}
