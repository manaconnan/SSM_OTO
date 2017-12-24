package com.mazexiang.dto;

import com.mazexiang.entity.Product;
import com.mazexiang.enums.ProductStateEnum;

import java.util.List;

public class ProductExecution {
    private int state;
    private String stateInfo;
    private int count;
    private Product product;
    private List<Product> productList;
    public ProductExecution(){

    }
    public ProductExecution(ProductStateEnum stateEnum){
        this.state =stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
    }
    public  ProductExecution(ProductStateEnum stateEnum, Product product){
        this.state =stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.product = product;


    }
    public ProductExecution(ProductStateEnum stateEnum , List<Product> productList){
        this.state =stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.productList = productList;
    }

    public int getState() {
        return state;
    }



    public String getStateInfo() {
        return stateInfo;
    }


    public int getCount() {
        return count;
    }


    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public List<Product> getProductList() {
        return productList;
    }


}
