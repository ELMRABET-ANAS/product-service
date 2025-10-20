package com.elmrabet.productService.mapper;

import com.elmrabet.productService.dto.ProductDTO;
import com.elmrabet.productService.entity.Product;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ProductMapper {

  ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

  ProductDTO toDTO(Product product);

  List<ProductDTO> toDTOs(List<Product> products);

  Product toEntity(ProductDTO productDTO);

  List<Product> toEntities(List<ProductDTO> productDTOs);
}
