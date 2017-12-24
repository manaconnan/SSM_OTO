package com.mazexiang.util.wexin;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.FormatFlagsConversionMismatchException;

public class SignUtil {

    private static String token = "myo2o";
    public static boolean checkSignature(String signature,String timestamp ,String nonce){
        String[] arr = new String[] {token,timestamp,nonce};
        Arrays.sort(arr);
        StringBuilder content = new StringBuilder();
        for(int i =0;i<arr.length;i++){
            content.append(arr[i]);
        }
        MessageDigest md ;
        String temStr = null;
        try{
            md = MessageDigest.getInstance("SHA-1");
            byte[] digest = md.digest(content.toString().getBytes());
            temStr = byteToStr(digest);
        }catch (Exception e){
            e.printStackTrace();
        }
        content = null;

        return temStr!=null? temStr.equals(signature.toUpperCase()):false;
    }

    private static String byteToStr(byte[] digest) {
        String strDiget="";
        for(int i = 0;i<digest.length;i++){
            strDiget+=byteToHexStr(digest[i]);
        }
        return  strDiget;
    }

    /**
     * 将字节转换成十六进制字符串
     * @param b
     * @return
     */
    private static String byteToHexStr(byte b) {
        char[] digit = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
        char[] tempArr = new char[2];
        tempArr[0] = digit[(b>>>4)&0x0F];
        tempArr[1] = digit[b&0x0f];
        String s = new String(tempArr);
        return s;
    }
}
