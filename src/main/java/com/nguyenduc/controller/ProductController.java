package com.nguyenduc.controller;

import com.nguyenduc.model.category.Category;
import com.nguyenduc.model.image.Image;
import com.nguyenduc.model.product.Product;
import com.nguyenduc.model.product.ProductRequest;
import com.nguyenduc.service.category.ICategoryService;
import com.nguyenduc.service.image.IImageService;
import com.nguyenduc.service.product.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/products")
public class ProductController {
    @Value("${file-upload}")
    private String fileUpload;

    @Autowired
    private IImageService iImageService;

    @Autowired
    private IProductService productService;

    @Autowired
    private ICategoryService categoryService;

    @GetMapping
    public ResponseEntity<Iterable<Product>> showAllProduct(@RequestParam(name = "q", required = false) String q,
                                                            @RequestParam(name = "category", required = false) Category category,
                                                            @RequestParam(defaultValue = "0") int page) {
        final int SIZE = 5;
        Iterable<Product> products;
        if (q != null) {
            products = productService.findAllByNameContaining(q);
        } else if (category != null) {
            products = productService.findAllByCategory(category);
        } else {
            products = productService.findAll();
        }
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> addNewProduct(ProductRequest productRequest) throws IOException {
        List<MultipartFile> multipartFiles = productRequest.getMultipartFiles();
        Product product = new Product();
        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setPrice(productRequest.getPrice());
        product.setCategory(productRequest.getCategory());
        product.setCreatedDate(new Date());
        productService.save(product);
        for (MultipartFile multipartFile : multipartFiles) {
            String fileName = multipartFile.getOriginalFilename();
            FileCopyUtils.copy(fileName.getBytes(), new File(fileUpload + fileName));
            Image image = new Image();
            image.setName(fileName);
            image.setProduct(product);
            iImageService.save(image);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
