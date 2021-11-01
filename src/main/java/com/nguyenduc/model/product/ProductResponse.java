package com.nguyenduc.model.product;

import com.nguyenduc.model.category.Category;
import com.nguyenduc.model.image.Image;

import java.util.Date;
import java.util.List;

public class ProductResponse {
    private Long id;

    private String name;

    private String description;

    private double price;

    private Date createdDate;

    private Category category;

    private List<Image> images;
}
