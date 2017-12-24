package com.mazexiang.service.impl;

import com.mazexiang.BaseTest;
import com.mazexiang.dto.ImageHolder;
import com.mazexiang.dto.ProductExecution;
import com.mazexiang.entity.Product;
import com.mazexiang.entity.ProductCategory;
import com.mazexiang.entity.Shop;
import com.mazexiang.service.ProductService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class ProductServiceImplTest extends BaseTest {


    @Autowired
    private ProductService productService;
    @Test
    public void testAddProduct() throws Exception {
        Shop shop = new Shop();
        shop.setShopId(1L);
        ProductCategory productCategory = new ProductCategory();
        productCategory.setProductCategoryId(4L);
        productCategory.setShopId(1L);

        Product product = new Product();
        product.setCreateTime(new Date());
        product.setLastEditTime(new Date());
        product.setProductName("第五件商品");
        product.setShop(shop);
        product.setNormalPrice("100yuan");
        product.setPromotionPrice("80yuan");
        product.setProductDesc("testProd desc");
        product.setPriority(10);
        product.setProductCategory(productCategory);
        product.setEnableStatus(1);

        File shopImg = new File("/Users/mazexiang/Workspace/resources/ma.jpg");
        InputStream ins = new FileInputStream(shopImg);
        ImageHolder thumbnail = new ImageHolder(shopImg.getName(),ins);

        File shopImg3 = new File("/Users/mazexiang/Workspace/resources/newPicture.jpg");
        InputStream ins3 = new FileInputStream(shopImg3);
        ImageHolder thumbnail3 = new ImageHolder(shopImg3.getName(),ins3);

        File shopImg2 = new File("/Users/mazexiang/Workspace/resources/testPicture.jpg");
        InputStream ins2 = new FileInputStream(shopImg2);
        ImageHolder thumbnail2 = new ImageHolder(shopImg2.getName(),ins2);

        List<ImageHolder> imageHolders = new ArrayList<>();
        imageHolders.add(thumbnail);
        imageHolders.add(thumbnail2);
        ProductExecution productExecution = productService.addProduct(product, thumbnail3,imageHolders);
    }

    @Test
    public void testgetProductByid(){
        ProductExecution productByShopIdAndProductId = productService.getProductByShopIdAndProductId(1l, 1l);
        Product product = productByShopIdAndProductId.getProduct();
        System.out.println(product.getProductName());

    }
    @Test
    public void testUpdateProduct(){
        Product product = new Product();
        Shop shop = new Shop();
        shop.setShopId(1l);
        product.setProductId(1l);
        product.setImgAddr("测试地址");
        product.setLastEditTime(new Date());
        product.setShop(shop);


        productService.modifyProductMainInfo(product);
    }


}