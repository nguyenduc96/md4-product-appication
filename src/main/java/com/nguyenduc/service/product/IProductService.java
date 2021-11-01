package com.nguyenduc.service.product;

import com.nguyenduc.model.category.Category;
import com.nguyenduc.model.product.Product;
import com.nguyenduc.service.general.IGeneralService;

public interface IProductService extends IGeneralService<Product> {
    Iterable<Product> findAllByCategory(Category category);

    Iterable<Product> findAllByNameContaining(String name);
}
