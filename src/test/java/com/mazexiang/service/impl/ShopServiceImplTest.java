package com.mazexiang.service.impl;

import com.mazexiang.BaseTest;
import com.mazexiang.dto.ShopExecution;
import com.mazexiang.entity.Area;
import com.mazexiang.entity.PersonInfo;
import com.mazexiang.entity.Shop;
import com.mazexiang.entity.ShopCategory;
import com.mazexiang.enums.ShopStateEnum;
import com.mazexiang.service.ShopService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;

import static com.mazexiang.util.ImageUtil.generateThumbnail;
import static org.junit.Assert.*;

public class ShopServiceImplTest extends BaseTest {
    @Autowired
    ShopService shopService;
    
    @Test
    public void testAddShop() throws Exception {

        Area area = new Area();
        area.setAreaId(4);
        PersonInfo personInfo = new PersonInfo();
        personInfo.setUserId(2L);
        ShopCategory shopCategory = new ShopCategory();
        shopCategory.setShopCategoryId(2L);
        Shop shop = new Shop();
        shop.setAdvice("审核中");
        shop.setArea(area);
        shop.setCreateTime(new Date());
        shop.setEnableStatus(ShopStateEnum.CHECK.getState());
        shop.setOwner(personInfo);
        shop.setPhone("test");
        shop.setPriority(1);
        shop.setShopAddr("test");
        shop.setShopCategory(shopCategory);
        shop.setShopDesc("终于成功了");
        shop.setShopName("测试店铺002");
        File shopImg = new File("/Users/mazexiang/Workspace/resources/testPicture.jpg");
        InputStream ins = new FileInputStream(shopImg);
        ShopExecution shopExecution = shopService.addShop(shop, ins,shopImg.getName());

        System.out.println(shopExecution.getShop());

    }
    @Test
    public void testModifyShop() throws FileNotFoundException {
        Area area = new Area();
        area.setAreaId(4);
        PersonInfo personInfo = new PersonInfo();
        personInfo.setUserId(2L);
        ShopCategory shopCategory = new ShopCategory();
        shopCategory.setShopCategoryId(2L);
        Shop shop = new Shop();
        shop.setShopId(20L);
        shop.setAdvice("审核 通过");
        shop.setArea(area);
        shop.setCreateTime(new Date());
        shop.setEnableStatus(ShopStateEnum.PASS.getState());
        shop.setOwner(personInfo);
        shop.setPhone("11111111111");
        shop.setPriority(3);
        shop.setShopAddr(" 海淀区学院路37好");
        shop.setShopCategory(shopCategory);
        shop.setShopDesc("终于成功了");
        shop.setShopName("测试店铺002");
        File shopImg = new File("/Users/mazexiang/Workspace/resources/ma.jpg");
        InputStream ins = new FileInputStream(shopImg);

        ShopExecution shopExecution1 = shopService.modifyShop(shop, ins, shopImg.getName());
        System.out.println(shopExecution1.getShop());
    }

}