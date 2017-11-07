package com.mazexiang.dao;

import com.mazexiang.entity.Shop;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ShopDao {

    Shop queryByShopId(Long shopId);

    int insertShop(Shop shop);

    int updateShop(Shop shop);

    /**
     * 分页查询店铺，可输入的条件有： 店铺名（模糊），店铺状态，店铺类别，区域ID， owner
     * @param shopCondition 查询的条件
     * @param rowIndex 从第几行开始取数据
     * @param pageSize 返回的条数
     * @return
     */
    List<Shop> queryShopList(@Param("shopCondition") Shop shopCondition,
                             @Param("rowIndex") int rowIndex,@Param("pageSize") int pageSize);

    /**
     * 返回queryShopList的店铺信息总数
     * @param shopCondition
     * @return
     */
    int queryShopCount(@Param("shopCondition") Shop shopCondition);
}
