package com.example.webshopapi.product;

import java.util.List;

public interface ProductService {
    List<Product> findAll();
    ProductDTO findById(int id);
    void saveProduct(ProductDTO productDTO);
    void deleteProduct(int id);
}