package com.elmrabet.productService.mapper;

import com.elmrabet.common.dto.ProductDTO;
import com.elmrabet.productService.entity.Product;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {

  ProductDTO toDTO(Product product);

  List<ProductDTO> toDTOs(List<Product> products);

  Product toEntity(ProductDTO productDTO);

  List<Product> toEntities(List<ProductDTO> productDTOs);
}
