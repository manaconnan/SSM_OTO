package com.mazexiang.service;

import com.mazexiang.dto.ImageHolder;
import com.mazexiang.dto.ProductExecution;
import com.mazexiang.entity.Product;
import com.mazexiang.exceptions.ProductOperationException;

import java.io.InputStream;
import java.util.List;

public interface ProductService {
    ProductExecution addProduct(Product product,ImageHolder thumbnail, List<ImageHolder> productImgList) throws ProductOperationException;
    ProductExecution getProductListByShopId(Long shopId);
    ProductExecution getProductByShopIdAndProductId(Long shopId,Long productId);
    int modifyProductMainInfo(Product product);
    int modifyProductMainImg(Product product, ImageHolder thumbnail);
    void modifyProductDetailImg(Product product, List<ImageHolder> imageHolderList);

}
