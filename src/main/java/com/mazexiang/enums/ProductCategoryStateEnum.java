package com.mazexiang.enums;

public enum ProductCategoryStateEnum {
    INNER_ERROR(-1001,"系统内部错误");

    private int state;
    private String stateInfo;

    private ProductCategoryStateEnum(int state, String stateInfo){
        this.state = state;
        this.stateInfo = stateInfo;

    }

    public static ProductCategoryStateEnum stateOf(int state){

        for(ProductCategoryStateEnum stateEnum : values()){
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
