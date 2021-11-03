package com.nguyenduc.repository;

import com.nguyenduc.model.category.Category;
import com.nguyenduc.model.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICategoryRepository extends JpaRepository<Category, Long> {
}
