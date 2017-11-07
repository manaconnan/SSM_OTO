package com.mazexiang.service;


import com.mazexiang.dao.ProductCategoryDao;
import com.mazexiang.entity.ProductCategory;

import java.util.List;

public interface ProductCategoryService {

    List<ProductCategory> getProductCategoryList(Long shopId);

    int deleteProductCategoryById(Long productCategoryId);
}
