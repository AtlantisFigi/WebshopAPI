package com.example.webshopapi.product;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/search")
    public ResponseEntity<List<ProductDTO>> searchProduct(@RequestBody SearchCriteria criteria) {
        List<ProductDTO> productDTOS = productService.findBySearchCriteria(criteria);

        if (productDTOS.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(productDTOS, HttpStatus.OK);
    }
}
