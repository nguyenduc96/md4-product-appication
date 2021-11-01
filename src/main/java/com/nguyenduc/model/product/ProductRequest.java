package com.nguyenduc.model.product;

import com.nguyenduc.model.category.Category;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@Data
public class ProductRequest {
    private Long id;

    private String name;

    private String description;

    private double price;

    private Date createdDate;

    private Category category;

    private List<MultipartFile> multipartFiles;
}
