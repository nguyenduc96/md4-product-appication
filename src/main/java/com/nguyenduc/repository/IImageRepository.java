package com.nguyenduc.repository;

import com.nguyenduc.model.image.Image;
import com.nguyenduc.model.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IImageRepository extends JpaRepository<Image, Long> {
    List<Image> findAllByProduct(Product product);
}
