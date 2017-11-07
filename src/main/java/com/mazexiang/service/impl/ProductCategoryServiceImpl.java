package com.mazexiang.service.impl;

import com.mazexiang.dao.ProductCategoryDao;
import com.mazexiang.entity.ProductCategory;
import com.mazexiang.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

    @Autowired
    private ProductCategoryDao productCategoryDao;
    @Override
    public List<ProductCategory> getProductCategoryList(Long shopId ) {

        return productCategoryDao.queryProductCategory(shopId);
    }

    @Override
    public int deleteProductCategoryById(Long productCategoryId) {

        ProductCategory productCategory = new ProductCategory();

        productCategory.setProductCategoryId(productCategoryId);
        productCategory.setIsDelete(1);

        int i = productCategoryDao.updateProductCategory(productCategory);

        return i;
    }
}
