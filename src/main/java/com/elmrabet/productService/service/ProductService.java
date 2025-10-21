package com.elmrabet.productService.service;

import com.elmrabet.common.dto.ProductDTO;
import com.elmrabet.productService.entity.Product;
import com.elmrabet.productService.mapper.ProductMapper;
import com.elmrabet.productService.repository.ProductRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProductService {

  private final ProductRepository productRepository;
  private final ProductMapper productMapper;

  @CacheEvict(value = {"products", "products_all"}, allEntries = true)
  public ProductDTO create(ProductDTO productDTO) {
    Product product = productMapper.toEntity(productDTO);
    Product savedProduct = productRepository.save(product);
    return productMapper.toDTO(savedProduct);
  }

  @Cacheable(value = "products_all")
  public List<ProductDTO> getAll() {
    List<Product> products = productRepository.findAll();
    return productMapper.toDTOs(products);
  }

  @Cacheable(value = "products", key = "#id")
  public ProductDTO getById(Long id) {
    Product product = productRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
    return productMapper.toDTO(product);
  }
}
