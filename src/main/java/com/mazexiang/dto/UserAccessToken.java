package com.mazexiang.dto;

import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * 用户AccessToken实体类，用来接收accesstoken以及openid等信息
 */

public class UserAccessToken {

    //注解是将对应的json数据赋值给对应的属性
    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("expires_in")
    private String expiresIn;
    @JsonProperty("refresh_token")
    private String refreshToken;

    @JsonProperty("openId")
    private String openId;

    @JsonProperty("scope")
    private String scope;

    public String getAccessToken() {

        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(String expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    @Override
    public String toString() {
        return "UserAccessToken{" +
                "accessToken='" + accessToken + '\'' +
                ", openId='" + openId + '\'' +
                '}';
    }
}
