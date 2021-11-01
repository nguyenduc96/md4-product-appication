package com.nguyenduc.service.image;

import com.nguyenduc.model.image.Image;
import com.nguyenduc.model.product.Product;
import com.nguyenduc.service.general.IGeneralService;

import java.util.List;

public interface IImageService extends IGeneralService<Image> {
    List<Image> findAllByProduct(Product product);
}
