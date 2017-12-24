package com.mazexiang.dao;

import com.mazexiang.entity.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductDao {
    int insertProduct(Product product);

    List<Product> queryAllProductsByShopId(Long shopId);

    Product queryProductByShopIdAndProductId(@Param("shopId") Long shopId,@Param("productId") Long productId);

    int updateProductByCondition(Product product);
}
