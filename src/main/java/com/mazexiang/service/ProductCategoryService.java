package com.mazexiang.service;


import com.mazexiang.dao.ProductCategoryDao;
import com.mazexiang.dto.ProductCategoryExecution;
import com.mazexiang.entity.ProductCategory;
import com.mazexiang.exceptions.ProductCategoryOperationException;

import java.util.List;

public interface ProductCategoryService {

    List<ProductCategory> getProductCategoryList(Long shopId);

    ProductCategoryExecution deleteProductCategory(ProductCategory productCategory);

    ProductCategoryExecution batchAddProductCategory(List<ProductCategory> productCategoryList)
            throws ProductCategoryOperationException;
}
