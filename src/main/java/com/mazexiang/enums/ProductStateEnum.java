package com.mazexiang.enums;

public enum ProductStateEnum {
    SUCCESS(1,"操作成功"),
    NULL_PRODUCT(-1,"商品为空"),
    INNER_ERROR(-1001,"系统内部错误"),
    EMPTY_LIST(-1002,"添加数小于1");

    private int state;
    private String stateInfo;

    ProductStateEnum(int state, String stateInfo){
        this.state = state;
        this.stateInfo = stateInfo;

    }

    public static ProductStateEnum stateOf(int state){

        for(ProductStateEnum stateEnum : values()){
            if(state == stateEnum.state){
                return stateEnum;
            }
        }
        return null;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }
}
