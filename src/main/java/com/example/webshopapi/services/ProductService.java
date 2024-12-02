package com.example.webshopapi.services;

import com.example.webshopapi.dataTransferObjects.ProductDTO;
import com.example.webshopapi.dataTransferObjects.SearchCriteria;

import java.util.List;

public interface ProductService {
    List<ProductDTO> findAll();
    ProductDTO findById(int id);
    void saveProduct(ProductDTO productDTO);
    void deleteProduct(int id);
    void updateProduct(int id, ProductDTO productDTO);
    List<ProductDTO> findBySearchCriteria(SearchCriteria criteria);
}