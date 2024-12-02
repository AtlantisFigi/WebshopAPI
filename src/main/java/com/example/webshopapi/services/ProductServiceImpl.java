package com.example.webshopapi.services;

import com.example.webshopapi.dataTransferObjects.ProductDTO;
import com.example.webshopapi.dataTransferObjects.SearchCriteria;
import com.example.webshopapi.entities.Category;
import com.example.webshopapi.entities.Product;
import com.example.webshopapi.exceptions.ProductNotFoundException;
import com.example.webshopapi.repositories.CategoryRepository;
import com.example.webshopapi.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final SimpMessagingTemplate messagingTemplate;

    @Override
    public List<ProductDTO> findAll() {
        return productRepository.findAll().stream()
                .map(this::convertToProductDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ProductDTO findById(int id) {
        Product product = productRepository.findById((long) id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));

        return convertToProductDTO(product);
    }

    @Override
    public void saveProduct(ProductDTO productDTO) {
        Product product = convertToProduct(productDTO);

        productRepository.save(product);
    }

    @Override
    public void deleteProduct(int id) {
        productRepository.deleteById((long) id);
    }

    @Override
    public List<ProductDTO> findBySearchCriteria(SearchCriteria criteria) {
        List<String> categories = (criteria.categories() == null || criteria.categories().isEmpty())
                ? null
                : criteria.categories();

        List<Product> products = productRepository.searchProducts(
                criteria.name(),
                categories,
                categories == null ? 0 : categories.size()
        );

        return products.stream()
                .map(this::convertToProductDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void updateProduct(int id, ProductDTO productDTO) {
        Product product = productRepository.findById((long) id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));

        product.setName(productDTO.name());
        product.setDescription(productDTO.description());
        product.setPrice(productDTO.price());
        List<Category> updatedCategories = productDTO.categories().stream()
                .map(categoryName -> {
                    Category category = categoryRepository.findByName(categoryName);
                    if (category == null) {
                        category = new Category();
                        category.setName(categoryName);
                        categoryRepository.save(category);  // Save new category
                    }
                    return category;
                })
                .collect(Collectors.toList());
        product.setCategories(updatedCategories);
        product.setQuantity(productDTO.quantity());
        productRepository.save(product);

        ProductDTO updatedProductDTO = convertToProductDTO(product);
        messagingTemplate.convertAndSend("/topic/products", updatedProductDTO);
    }

    private ProductDTO convertToProductDTO(Product product) {

        return new ProductDTO(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getCategories().stream()
                        .map(Category::getName)
                        .collect(Collectors.toList()),
                product.getQuantity()
        );
    }

    private Product convertToProduct(ProductDTO productDTO) {
        Product product = new Product();
        product.setName(productDTO.name());
        product.setDescription(productDTO.description());
        product.setPrice(productDTO.price());
        product.setCategories(productDTO.categories().stream()
                .map(categoryName -> {
                    Category category = new Category();
                    category.setName(categoryName);
                    return category;
                })
                .collect(Collectors.toList())
        );

        return product;
    }
}
