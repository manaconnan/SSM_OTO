package com.mazexiang.util;

import com.google.code.kaptcha.Constants;

import javax.servlet.http.HttpServletRequest;

public class CodeUtil {
    public static boolean checkVerifyCode(HttpServletRequest request){
        String verifyCodeExpected =(String) request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
        String verifyCodeActual = HttpServletRequestUtils.getString(request,"verifyCodeActual");
        if (verifyCodeActual == null|| !verifyCodeActual.equalsIgnoreCase(verifyCodeExpected)){
            return false;
        }
        return true;
    }
}