package com.example.webshopapi.product;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class ProductControllerTest {
    private MockMvc mockMvc;

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
    }

    @Test
    void testSearchProduct_EmptyCriteria() throws Exception {
        // Arrange
        SearchCriteria criteria = new SearchCriteria("", Collections.emptyList(), 0, 1000);
        when(productService.findBySearchCriteria(any())).thenReturn(Collections.emptyList());

        // Act & Assert
        mockMvc.perform(post("/api/product/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "name": "",
                                  "categories": [],
                                  "minPrice": 0,
                                  "maxPrice": 1000
                                }
                                """)
                )
                .andExpect(status().isNoContent());
    }

    @Test
    void testSearchProduct_NoProductsFound() throws Exception {
        // Arrange
        SearchCriteria criteria = new SearchCriteria("SomeProduct", Collections.singletonList("Category1"), 0, 1000);
        when(productService.findBySearchCriteria(any())).thenReturn(Collections.emptyList());

        // Act & Assert
        mockMvc.perform(post("/api/product/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "name": "SomeProduct",
                                  "categories": ["Category1"],
                                  "minPrice": 0,
                                  "maxPrice": 1000
                                }""")
                )
                .andExpect(status().isNoContent());
    }

    @Test
    void testSearchProduct_ProductsFound() throws Exception {
        // Arrange
        List<ProductDTO> productList = new ArrayList<>();
        productList.add(new ProductDTO(1, "Product1", "Description1", 100.0, Collections.emptyList()));
        productList.add(new ProductDTO(2, "Product2", "Description2", 150.0, Collections.emptyList()));

        SearchCriteria criteria = new SearchCriteria("Product", Collections.singletonList("Category1"), 0, 1000);
        when(productService.findBySearchCriteria(any())).thenReturn(productList);

        // Act & Assert
        mockMvc.perform(post("/api/product/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "name": "Product",
                                  "categories": ["Category1"],
                                  "minPrice": 0,
                                  "maxPrice": 1000
                                }
                                """)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[{" +
                        "\"id\": 1," +
                        "\"name\": \"Product1\"," +
                        "\"description\": \"Description1\"," +
                        "\"price\": 100.0," +
                        "\"images\": []" +
                        "},{" +
                        "\"id\": 2," +
                        "\"name\": \"Product2\"," +
                        "\"description\": \"Description2\"," +
                        "\"price\": 150.0," +
                        "\"images\": []" +
                        "}]"));
    }
}
