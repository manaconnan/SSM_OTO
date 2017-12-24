package com.mazexiang.dao;

import com.mazexiang.entity.ProductImg;

import java.util.List;

public interface ProductImgDao {



    List<ProductImg>  queryProductImgList(Long productId);

    int batchInsertProductImg(List<ProductImg> productImgList);

    int deleteProductImgByProductId(Long productId);


}
