package com.mazexiang.util;

import com.mazexiang.entity.Product;

public class PathUtil {
    private static  String seperator = System.getProperty("file.separator");//获取系统文件的分割符
    public static String getImgBasePath(){
        String os = System.getProperty("os.name");//获取操作系统名称
        String basePath;
        if(os.toLowerCase().startsWith("win")){
            basePath = "D:/projectdev/image/";
        }else {
            basePath = "/Users/mazexiang/Workspace/image/";
        }
        basePath = basePath.replace("/",seperator);
        return basePath;
    }
    public static String getShopImagePath(long shopId){
        String imagePath = "upload/item/shop"+shopId+"/";
        return imagePath.replace("/",seperator);
    }
    public static String getProductImagePath(Product product){
        String imagePath = getShopImagePath(product.getShop().getShopId())+product.getProductName()+"/";
        return imagePath.replace("/",seperator);
    }
}
