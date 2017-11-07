package com.mazexiang.dao;

import com.mazexiang.BaseTest;
import com.mazexiang.entity.PersonInfo;
import com.mazexiang.entity.Shop;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.*;

public class ShopDaoTest extends BaseTest {

    @Autowired
    private ShopDao shopDao;
    
    
    @Test
    public void testQueryShopListAndShopCount(){
        Shop shopCondition = new Shop();
        PersonInfo owner = new PersonInfo();
        owner.setUserId(1L);
       // shopCondition.setOwner(owner);
        shopCondition.setShopName("测试");
        List<Shop> shops = shopDao.queryShopList(shopCondition, 0, 5);
        int i = shopDao.queryShopCount(shopCondition);
        System.out.println(shops.size());
        System.out.println(i);
        
    }
    
    @Test
    public void queryByShopId() throws Exception {
        Shop shop = shopDao.queryByShopId(1L);
        System.out.println(shop);
    }
    
    

    @Test
    public void insertShop() throws Exception {
    }

    @Test
    public void updateShop() throws Exception {
    }

}