package com.mazexiang.exceptions;

public class ProductOperationException extends RuntimeException {

    private static final long serialVersionUID = 2312446884122251905L;


    public ProductOperationException(String msg){
        super(msg);
    }
}
