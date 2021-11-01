package com.nguyenduc.controller;

import com.nguyenduc.model.category.Category;
import com.nguyenduc.service.category.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin("*")
public class CategoryController {
    @Autowired
    private ICategoryService categoryService;

    @GetMapping("/categories")
    public ResponseEntity<List<Category>> showAllCategory() {
        List<Category> categories = (List<Category>) categoryService.findAll();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }
}
