package com.sotiras.Cart_Api.repository;

import com.sotiras.Cart_Api.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByCategoryName(String category);

    List<Product> findByBrandName(String brand);

    List<Product> findByCategoryAndBrand(String category, String brand);

    List<Product> findByName(String productName);

    List<Product> findByBrandNameAndName(String brand, String productName);

    Long countByBrandAndName(String brand, String productName);
}
