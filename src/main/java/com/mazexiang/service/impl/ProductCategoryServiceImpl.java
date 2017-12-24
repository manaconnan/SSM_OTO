package com.mazexiang.service.impl;

import com.mazexiang.dao.ProductCategoryDao;
import com.mazexiang.dto.ProductCategoryExecution;
import com.mazexiang.entity.ProductCategory;
import com.mazexiang.enums.ProductCategoryStateEnum;
import com.mazexiang.exceptions.ProductCategoryOperationException;
import com.mazexiang.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

    @Autowired
    private ProductCategoryDao productCategoryDao;
    @Override
    public List<ProductCategory> getProductCategoryList(Long shopId ) {

        return productCategoryDao.queryProductCategory(shopId);
    }

    @Override
    @Transactional
    public ProductCategoryExecution deleteProductCategory(ProductCategory productCategory) {
        productCategory.setIsDelete(1);

        //TODO 删除商品类别之后， 将该类别下的商品数置为空
        try {

            int effectedNum = productCategoryDao.updateProductCategory(productCategory);
            if(effectedNum>0){
                return new  ProductCategoryExecution(ProductCategoryStateEnum.SUCCESS);

            }else {
                throw new ProductCategoryOperationException("商品类别删除失败");
            }
        }catch (Exception e ){
            throw new ProductCategoryOperationException("deleteProductCategoryById error :"+e.getMessage());
        }

    }

    @Override
    public ProductCategoryExecution batchAddProductCategory(List<ProductCategory> productCategoryList)
            throws ProductCategoryOperationException {
        if(productCategoryList!=null&&productCategoryList.size()>0){
            try {
                int effectedNum = productCategoryDao.batchInsertProductCategory(productCategoryList);
                if(effectedNum>0){
                    return new ProductCategoryExecution(ProductCategoryStateEnum.SUCCESS);
                }else {
                    throw new ProductCategoryOperationException("店铺类别创建失败");
                }
            }catch (Exception e){
                throw new ProductCategoryOperationException("batchAddProductCategory error :"+e.getMessage());
            }
        }else {
            return new  ProductCategoryExecution(ProductCategoryStateEnum.EMPTY_LIST);
        }
    }
}
