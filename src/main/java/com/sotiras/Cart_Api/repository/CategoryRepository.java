package com.sotiras.Cart_Api.repository;

import com.sotiras.Cart_Api.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Long> {
    Category findByName(String name);
}
