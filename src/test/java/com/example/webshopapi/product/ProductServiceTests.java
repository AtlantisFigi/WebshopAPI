package com.example.webshopapi.product;

import com.example.webshopapi.exceptions.ProductNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProductServiceTests {
    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAllWhenThereAreProducts() {
        // Arrange
        Product product1 = new Product();
        product1.setId(1);
        product1.setName("Product 1");
        product1.setDescription("Description 1");
        product1.setPrice(10.0);
        product1.setImages(new ArrayList<>());
        product1.setCategories(new ArrayList<>());

        Product product2 = new Product();
        product2.setId(2);
        product2.setName("Product 2");
        product2.setDescription("Description 2");
        product2.setPrice(15.0);
        product2.setImages(new ArrayList<>());
        product2.setCategories(new ArrayList<>());

        List<Product> products = List.of(product1, product2);
        when(productRepository.findAll()).thenReturn(products);

        // Act
        List<ProductDTO> result = productService.findAll();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        for (int i = 0; i < products.size(); i++) {
            assertProductEquals(products.get(i), result.get(i));
        }
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void testFindAllWhenThereAreNoProducts() {
        // Arrange
        when(productRepository.findAll()).thenReturn(new ArrayList<>());

        // Act
        List<ProductDTO> result = productService.findAll();

        // Assert
        assertNotNull(result);
        assertEquals(0, result.size());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void testFindByIdWhenProductExists() {
        // Arrange
        Product product = new Product();
        product.setId(1);
        product.setName("Product 1");
        product.setDescription("Description 1");
        product.setPrice(10.0);
        product.setImages(new ArrayList<>());
        product.setCategories(new ArrayList<>());

        when(productRepository.findById(1L)).thenReturn(java.util.Optional.of(product));

        // Act
        ProductDTO result = productService.findById(1);

        // Assert
        assertNotNull(result);
        assertProductEquals(product, result);
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    void testFindByIdWhenProductDoesNotExist() {
        // Arrange
        when(productRepository.findById(1L)).thenReturn(java.util.Optional.empty());

        // Act
        ProductNotFoundException exception = assertThrows(ProductNotFoundException.class, () -> {
            productService.findById(1);
        });

        // Assert
        assertEquals("Product not found", exception.getMessage());
        verify(productRepository, times(1)).findById(1L);
    }

    private void assertProductEquals(Product product, ProductDTO productDTO) {
        assertEquals(product.getId(), productDTO.id(), "Product ID does not match");
        assertEquals(product.getName(), productDTO.name(), "Product name does not match");
        assertEquals(product.getDescription(), productDTO.description(), "Product description does not match");
        assertEquals(product.getPrice(), productDTO.price(), "Product price does not match");
    }
}
