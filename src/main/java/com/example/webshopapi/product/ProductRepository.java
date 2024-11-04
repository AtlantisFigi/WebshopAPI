package com.example.webshopapi.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query(value = "SELECT p FROM Product p JOIN p.categories c "
            + "WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%')) "
            + "AND (:categories IS NULL OR :categoriesSize = 0 OR p.id IN ( "
            + "SELECT p2.id FROM Product p2 JOIN p2.categories c2 "
            + "WHERE c2.name IN :categories "
            + "GROUP BY p2.id "
            + "HAVING COUNT(DISTINCT c2.name) = :categoriesSize))")
    List<Product> searchProducts(@Param("name") String name, @Param("categories") List<String> categories, @Param("categoriesSize") int categoriesSize);
}