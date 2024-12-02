package com.example.webshopapi.services;

import com.example.webshopapi.dataTransferObjects.CategoryDTO;
import com.example.webshopapi.entities.Category;
import com.example.webshopapi.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public List<CategoryDTO> getAll() {
        return categoryRepository.findAll().stream()
                .map(this::convertToCategoryDTO)
                .collect(Collectors.toList());
    }

    private CategoryDTO convertToCategoryDTO(Category category) {
        return new CategoryDTO(category.getId(), category.getName());
    }
}