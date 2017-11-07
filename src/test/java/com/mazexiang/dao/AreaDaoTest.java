package com.mazexiang.dao;

import com.mazexiang.BaseTest;
import com.mazexiang.entity.Area;
import com.mazexiang.entity.PersonInfo;
import com.mazexiang.entity.Shop;
import com.mazexiang.entity.ShopCategory;
import com.mazexiang.service.AreaService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;


public class AreaDaoTest extends BaseTest {

    @Autowired
    private AreaService areaService;

    @Autowired
    private ShopDao shopDao;

    @Test
    public void queryArea() throws Exception {

        List<Area> areaList =areaService.getAreaList();
       // System.out.println("-------------------------------------------------------");
       // System.out.println("============>areaList的值是： " + areaList);
        assertEquals(4,areaList.size());
    }

    @Test
    public void insertShop(){
        Area area = new Area();
        area.setAreaId(1);
        PersonInfo personInfo = new PersonInfo();
        personInfo.setUserId(1L);
        ShopCategory shopCategory = new ShopCategory();
        shopCategory.setShopCategoryId(1L);
        Shop shop = new Shop();
        shop.setAdvice("审核中");
        shop.setArea(area);
        shop.setCreateTime(new Date());
        shop.setEnableStatus(1);
        shop.setOwner(personInfo);
        shop.setPhone("test");
        shop.setPriority(1);
        shop.setShopAddr("test");
        shop.setShopCategory(shopCategory);
        shop.setShopDesc("test");
        shop.setShopImg("test");
        shop.setShopName("test1");
        int i = shopDao.insertShop(shop);
        System.out.println("-------------------------------------------------------");
        System.out.println("============>i的值是： " + i);

    }
    @Test
    public void updateShop(){
        Shop shop = new Shop();
        shop.setShopId(4L);
        shop.setAdvice("审核通过1105");
        shop.setLastEditTime(new Date());
        shopDao.updateShop(shop);
    }
    @Test
    public void testPath(){
        String path = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        System.out.println(path);
    }


}