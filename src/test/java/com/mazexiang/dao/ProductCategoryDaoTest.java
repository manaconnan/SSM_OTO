package com.mazexiang.dao;

import com.mazexiang.BaseTest;
import com.mazexiang.entity.Product;
import com.mazexiang.entity.ProductCategory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class ProductCategoryDaoTest extends BaseTest{
    @Autowired
    private ProductCategoryDao productCategoryDao;

    @Test
    public void queryProductCategory() throws Exception {
    }

    @Test
    public void updateProductCategory() throws Exception {
    }

    @Test
    public void batchInsertProductCategory() throws Exception {

        ProductCategory p1 = new ProductCategory();
        p1.setProductCategoryName("商品类别1");
        p1.setPriority(1);
        p1.setCreateTime(new Date());
        p1.setShopId(2L);
        ProductCategory p2 = new ProductCategory();
        p2.setShopId(2L);
        p2.setCreateTime(new Date());
        p2.setPriority(2);
        p2.setProductCategoryName("商品类别2");
        ProductCategory p3 = new ProductCategory();
        p3.setShopId(2L);
        p3.setCreateTime(new Date());
        p3.setPriority(2);
        p3.setProductCategoryName("商品类别3");
        List<ProductCategory> list = new ArrayList<>();
        list.add(p1);
        list.add(p2);
        list.add(p3);
        int i = productCategoryDao.batchInsertProductCategory(list);
        System.out.println(i);

    }

}