package com.example.webshopapi.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        Optional<Product> productOptional = productRepository.findById((long)id);

        if (productOptional.isPresent()) {
            Product product = productOptional.get();

            return new ProductDTO(
                    product.getId(),
                    product.getName(),
                    product.getDescription(),
                    product.getPrice()
            );
        }
        else{
            throw new RuntimeException("Product not found");
        }
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
        productRepository.deleteById((long)id);
    }

}
