package com.example.webshopapi.repositories;

import com.example.webshopapi.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findAll();
    Category findByName(String name);
}
