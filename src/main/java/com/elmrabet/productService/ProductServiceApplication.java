package com.elmrabet.productService;

import com.elmrabet.common.dto.ProductDTO;
import com.elmrabet.productService.service.ProductService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ProductServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductServiceApplication.class, args);
	}

	@Bean
	public CommandLineRunner init(ProductService productService) {
		return args -> {
			// Initialize the database with some products
			productService.create(new ProductDTO(null, "Product 1", 10.0));
			productService.create(new ProductDTO(null, "Product 2", 20.0));
			productService.create(new ProductDTO(null, "Product 3", 30.0));
		};
	}
}
