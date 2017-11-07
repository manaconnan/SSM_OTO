package com.mazexiang.dao;

import com.mazexiang.BaseTest;
import com.mazexiang.entity.ShopCategory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.*;

public class ShopCategoryDaoTest  extends BaseTest{
    @Autowired
    private ShopCategoryDao shopCategoryDao;
    @Test
    public void queryShopCategory() throws Exception {

        ShopCategory shopCategory = new ShopCategory();

        List<ShopCategory> shopCategories = shopCategoryDao.queryShopCategory(shopCategory);
        System.out.println(shopCategories.size());
    }

}