package com.elmrabet.productService.controller;

import com.elmrabet.common.dto.ProductDTO;
import com.elmrabet.productService.service.ProductService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/products")
@AllArgsConstructor
@RestController
public class ProductController {
  private final ProductService productService;

  @GetMapping
  public ResponseEntity<List<ProductDTO>> getAllProducts() {
    List<ProductDTO> products = productService.getAll();
    return ResponseEntity.ok(products);
  }

  @GetMapping("/{id}")
  public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) {
    ProductDTO product = productService.getById(id);
    return ResponseEntity.ok(product);
  }

  @PostMapping
  public ResponseEntity<ProductDTO> create(@RequestBody ProductDTO product) {
    ProductDTO createdProduct = productService.create(product);
    return ResponseEntity.ok(createdProduct);
  }
}
