package com.example.webshopapi.services;

import com.example.webshopapi.dataTransferObjects.CategoryDTO;
import com.example.webshopapi.entities.Category;

import java.util.List;

public interface CategoryService {
    List<CategoryDTO> getAll();
}
