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
import java.util.Optional;

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
            FileCopyUtils.copy(multipartFile.getBytes(), new File(fileUpload + product.getId() + new Date().getTime() + fileName));
            Image image = new Image();
            image.setName(fileName);
            image.setProduct(product);
            iImageService.save(image);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        productService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id,ProductRequest productRequest) throws IOException {
        Product product = productService.findById(id).get();
        if (product.getId() == null) {
            product.setId(id);
        }
        product.setName(productRequest.getName());
        product.setPrice(productRequest.getPrice());
        product.setDescription(productRequest.getDescription());
        product.setCategory(productRequest.getCategory());
        List<MultipartFile> multipartFiles = productRequest.getMultipartFiles();
        for (MultipartFile multipartFile : multipartFiles) {
            String fileName = multipartFile.getOriginalFilename();
            FileCopyUtils.copy(multipartFile.getBytes(), new File(fileUpload + product.getId() + new Date().getTime() + fileName));
            Image image = new Image();
            image.setName(fileName);
            image.setProduct(product);
            iImageService.save(image);
        }
        productService.save(product);
        return new ResponseEntity<>(HttpStatus.CREATED);

    }
}
