package com.mazexiang.service;

import com.mazexiang.dto.ShopExecution;
import com.mazexiang.entity.Shop;
import com.mazexiang.exceptions.ShopOperationException;

import java.io.InputStream;

public interface ShopService {
    ShopExecution addShop(Shop shop, InputStream shopImgInputStream, String fileName) throws ShopOperationException;
}


