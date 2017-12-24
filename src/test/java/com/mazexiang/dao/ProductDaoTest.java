package com.mazexiang.dao;

import com.mazexiang.BaseTest;
import com.mazexiang.entity.Product;
import com.mazexiang.entity.ProductCategory;
import com.mazexiang.entity.Shop;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class ProductDaoTest extends BaseTest {

    @Autowired
    private ProductDao productDao;
    @Test
    public void insertProduct() throws Exception {
        ProductCategory productCategory = new ProductCategory();
        productCategory.setProductCategoryId(3L);
        Shop shop = new Shop();
        shop.setShopId(1L);

        Product product = new Product();
        product.setCreateTime(new Date());
        product.setEnableStatus(1);
        product.setImgAddr("testaddr");
        product.setLastEditTime(new Date());
        product.setNormalPrice("100yuan");
        product.setProductCategory(productCategory);
        product.setPriority(10);
        product.setProductDesc("test");
        product.setProductName("testName");
        product.setPromotionPrice("20yuan");
        product.setShop(shop);

        int i = productDao.insertProduct(product);
        assertEquals(1,i);
    }

    @Test
    public void testQueryAllProductsByShopId(){
        List<Product> products = productDao.queryAllProductsByShopId(1L);
        assertEquals(6,products.size());
    }
}