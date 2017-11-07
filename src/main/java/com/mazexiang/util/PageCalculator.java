package com.mazexiang.util;

public class PageCalculator {
    public static int calculateRowIndex(int pageIndex, int pageSize){
        //通过输入的页码和每页的大小，计算出sql查询的起始行数rowIndex
        return (pageIndex>0)?(pageIndex-1)*pageSize:0;
    }
}
