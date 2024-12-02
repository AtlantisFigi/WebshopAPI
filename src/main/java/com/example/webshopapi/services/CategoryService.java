package com.example.webshopapi.services;

import com.example.webshopapi.dataTransferObjects.CategoryDTO;

import java.util.List;

public interface CategoryService {
    List<CategoryDTO> getAll();
}
