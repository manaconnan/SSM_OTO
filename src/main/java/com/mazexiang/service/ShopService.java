package com.mazexiang.service;

import com.mazexiang.dto.ImageHolder;
import com.mazexiang.dto.ShopExecution;
import com.mazexiang.entity.Shop;
import com.mazexiang.exceptions.ShopOperationException;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Field;


public interface ShopService {
    ShopExecution addShop(Shop shop, ImageHolder thumbnail) throws ShopOperationException;

    Shop getByShopId(long shopId);

    ShopExecution modifyShop(Shop shop, ImageHolder thumbnail) throws ShopOperationException;

    /**
     * 根据shopCondition 分页返回店铺列表
     * @param shopCondition
     * @param pageIndex 注意这个参数和shopDao中的queryShopList方法的不一样
     * @param pageSize
     * @return
     */
    ShopExecution getShopList(Shop shopCondition,int pageIndex,int pageSize);
}


