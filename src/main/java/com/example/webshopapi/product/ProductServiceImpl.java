package com.example.webshopapi.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public ProductDTO findById(int id) {
        Product product = productRepository.findById((long) id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        return convertToProductDTO(product);
    }

    @Override
    public void saveProduct(ProductDTO productDTO) {
        Product product = new Product();
        product.setName(productDTO.name());
        product.setDescription(productDTO.description());
        product.setPrice(productDTO.price());
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

    private ProductDTO convertToProductDTO(Product product) {
        List<String> base64Images = product.getImages().stream()
                .map(image -> Base64.getEncoder().encodeToString(image.getImage()))
                .collect(Collectors.toList());

        return new ProductDTO(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                base64Images
        );
    }
}
