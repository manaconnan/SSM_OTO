package com.mazexiang.dao;

import com.mazexiang.entity.ProductCategory;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductCategoryDao {
    List<ProductCategory> queryProductCategory(@Param("shopId") Long shopId);

    int updateProductCategory(ProductCategory productCategory);
}
