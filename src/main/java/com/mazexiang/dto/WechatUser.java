package com.mazexiang.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class WechatUser implements Serializable {
    private static final long serialVersionId = -1248234791274L;

    @JsonProperty("openId")
    private String openId;

    @JsonProperty("nick_name")
    private String nickName;

    @JsonProperty("sex")
    private int sex;

    @JsonProperty("province")
    private String province;

    @JsonProperty("city")
    private String city;
    @JsonProperty("country")
    private String country;
    @JsonProperty("headimgurl")
    private String headimgurl;

    @JsonProperty("language")
    private String language;

    @JsonProperty("privilege")
    private String[] privilage;

    public static long getSerialVersionId() {
        return serialVersionId;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getHeadimgurl() {
        return headimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String[] getPrivilage() {
        return privilage;
    }

    public void setPrivilage(String[] privilage) {
        this.privilage = privilage;
    }

    @Override
    public String toString() {
        return "WechatUser{" +
                "openId='" + openId + '\'' +
                ", nickName='" + nickName + '\'' +
                '}';
    }
}
