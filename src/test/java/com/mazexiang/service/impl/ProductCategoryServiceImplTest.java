package com.mazexiang.service.impl;

import com.mazexiang.BaseTest;
import com.mazexiang.dto.ProductCategoryExecution;
import com.mazexiang.entity.ProductCategory;
import com.mazexiang.service.ProductCategoryService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.*;

public class ProductCategoryServiceImplTest extends BaseTest {
    
    @Autowired
    ProductCategoryService productCategoryService;
    @Test
    public void getProductCategoryList() throws Exception {
        List<ProductCategory> productCategoryList = productCategoryService.getProductCategoryList(1l);
        System.out.println(productCategoryList.size());
        

    }



    @Test
    public void testUpdateProductCategory(){
        ProductCategory productCategory = new ProductCategory();
        productCategory.setShopId(1L);
        productCategory.setProductCategoryId(11l);
        ProductCategoryExecution productCategoryExecution = productCategoryService.deleteProductCategory(productCategory);

        System.out.println(productCategoryExecution.getStateInfo());
    }

}