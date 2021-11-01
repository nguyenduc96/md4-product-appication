package com.nguyenduc.model.product;

import com.nguyenduc.model.category.Category;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private double price;

    private Date createdDate;

    @ManyToOne
    private Category category;
}
