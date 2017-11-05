package com.mazexiang.dao;

import com.mazexiang.entity.Shop;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component
public interface ShopDao {

    int insertShop(Shop shop);

    int updateShop( Shop shop);
}
