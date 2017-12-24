package com.mazexiang.exceptions;

public class ProductCategoryOperationException extends RuntimeException {

    private static final long serialVersionUID = 2312446884822251905L;


    public ProductCategoryOperationException(String msg){
        super(msg);
    }

}
