package com.example.webshopapi.controllers;

import com.example.webshopapi.dataTransferObjects.ProductDTO;
import com.example.webshopapi.services.ProductService;
import com.example.webshopapi.dataTransferObjects.SearchCriteria;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateProduct(@PathVariable int id, @RequestBody ProductDTO productDTO) {
        productService.updateProduct(id, productDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable int id) {
        ProductDTO productDTO = productService.findById(id);
        return new ResponseEntity<>(productDTO, HttpStatus.OK);
    }

    @PostMapping("/search")
    public ResponseEntity<List<ProductDTO>> searchProduct(@RequestBody SearchCriteria criteria) {
        List<ProductDTO> productDTOS = productService.findBySearchCriteria(criteria);
        return new ResponseEntity<>(productDTOS, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable int id) {
        productService.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
