package com.mazexiang.dto;

import com.mazexiang.entity.ProductCategory;
import com.mazexiang.enums.ProductCategoryStateEnum;

import java.util.List;

public class ProductCategoryExecution {
    private int state;
    private String stateInfo;
    private List<ProductCategory> productCategoryList;
    public ProductCategoryExecution(){

    }
    //操作失败时候的构造器
    public ProductCategoryExecution(ProductCategoryStateEnum stateEnum){
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
    }
    public ProductCategoryExecution(ProductCategoryStateEnum stateEnum,List<ProductCategory> productCategoryList){
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.productCategoryList = productCategoryList;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public List<ProductCategory> getProductCategoryList() {
        return productCategoryList;
    }

    public void setProductCategoryList(List<ProductCategory> productCategoryList) {
        this.productCategoryList = productCategoryList;
    }
}
