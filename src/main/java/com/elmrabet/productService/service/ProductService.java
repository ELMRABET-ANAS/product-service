package com.elmrabet.productService.service;

import com.elmrabet.common.dto.ProductDTO;
import com.elmrabet.productService.entity.Product;
import com.elmrabet.productService.mapper.ProductMapper;
import com.elmrabet.productService.repository.ProductRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProductService {

  private final ProductRepository productRepository;
  private final ProductMapper productMapper;

  public ProductDTO create(ProductDTO productDTO) {
    Product product = productMapper.toEntity(productDTO);
    Product savedProduct = productRepository.save(product);
    return productMapper.toDTO(savedProduct);
  }

  public List<ProductDTO> getAll() {
    List<Product> products = productRepository.findAll();
    return productMapper.toDTOs(products);
  }

  public ProductDTO getById(Long id) {
    Product product = productRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
    return productMapper.toDTO(product);
  }
}
