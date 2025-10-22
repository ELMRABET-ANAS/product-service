package com.elmrabet.productService.service;

import com.elmrabet.common.dto.ProductDTO;
import com.elmrabet.productService.entity.Product;
import com.elmrabet.productService.mapper.ProductMapper;
import com.elmrabet.productService.repository.ProductRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProductService {

  private final ProductRepository productRepository;
  private final ProductMapper productMapper;

  // CRÉATION : Invalide la liste ET ajoute le nouveau produit au cache individuel
  // Optimisation : le produit créé est immédiatement disponible en cache pour les GET /products/{id}
  @Caching(
      put = @CachePut(value = "products", key = "#result.id"),
      evict = @CacheEvict(value = "products_all", allEntries = true)
  )
  public ProductDTO create(ProductDTO productDTO) {
    Product product = productMapper.toEntity(productDTO);
    Product savedProduct = productRepository.save(product);
    return productMapper.toDTO(savedProduct);
  }

  // MODIFICATION : Met à jour le cache du produit modifié ET invalide la liste
  // @CachePut met à jour le cache avec la nouvelle valeur retournée
  // @CacheEvict invalide la liste pour qu'elle reflète les changements
  @Caching(
      put = @CachePut(value = "products", key = "#result.id"),
      evict = @CacheEvict(value = "products_all", allEntries = true)
  )
  public ProductDTO update(Long id, ProductDTO productDTO) {
    Product existingProduct = productRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));

    existingProduct.setName(productDTO.name());
    existingProduct.setPrice(productDTO.price());

    Product updatedProduct = productRepository.save(existingProduct);
    return productMapper.toDTO(updatedProduct);
  }

  // SUPPRESSION : Invalide le produit supprimé ET la liste
  // On doit supprimer des deux caches car le produit n'existe plus
  @Caching(evict = {
      @CacheEvict(value = "products", key = "#id"),
      @CacheEvict(value = "products_all", allEntries = true)
  })
  public void delete(Long id) {
    Product product = productRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
    productRepository.delete(product);
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
