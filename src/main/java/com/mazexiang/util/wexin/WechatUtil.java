package com.mazexiang.util.wexin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mazexiang.dto.WechatUser;
import com.mazexiang.dto.UserAccessToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import java.io.*;
import java.net.URL;

public class WechatUtil {

    private static Logger log = LoggerFactory.getLogger(WechatUtil.class);

    public static UserAccessToken getUserAccessToken(String code) throws IOException{

        String appId = "您的appId";
        log.debug("appId:" +appId);
        String appsecret = "您的appsecret";

        log.debug("secret : "+appsecret);

        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="+appId
                + "&secret="+appsecret+"&code"+code
                +"&grant_type=authorization_code";
        String tokenStr = httpsRequest(url,"GET",null);
        log.debug("userAccessToken:"+tokenStr);

        UserAccessToken  token = new UserAccessToken();
        ObjectMapper mapper = new ObjectMapper();
        try {
            token = mapper.readValue(tokenStr,UserAccessToken.class);

        }catch (Exception e){
            log.error("获取用户信息失败 "+e.getMessage());
            e.printStackTrace();
        }
        return token;

    }
    public static WechatUser getUserInfo(String accessToken,String openId){
        String url = "https://api.weixin.qq.com/sns/uerinfo?access_token"+accessToken
                +"&openId"+openId+"&lang=zh_CN";
        String userStr = httpsRequest(url,"GET",null);
        WechatUser user = new WechatUser();
        ObjectMapper mapper = new ObjectMapper();
        try {
            user = mapper.readValue(userStr,WechatUser.class);
        }catch (Exception e){
            e.printStackTrace();
            log.error(e.getMessage());
        }
        if(user==null){
            log.error("获取用户信息失败");
            return null;
        }
        return user;
    }

    public static String httpsRequest (String requestUrl,String requestMethod,String outputStr){
        StringBuffer buffer = new StringBuffer();
        try {
            TrustManager[] tm = { new MyX509TrustManager()};
            SSLContext sslContext = SSLContext.getInstance("SSL","SunJSSE");
            sslContext.init(null,tm,new java.security.SecureRandom());
            SSLSocketFactory ssf = sslContext.getSocketFactory();
            URL url = new URL(requestUrl);
            HttpsURLConnection httpUrlConn = (HttpsURLConnection)url.openConnection();

            httpUrlConn.setSSLSocketFactory(ssf);
            httpUrlConn.setDoOutput(true);
            httpUrlConn.setDoInput(true);
            httpUrlConn.setUseCaches(false);
            httpUrlConn.setRequestMethod(requestMethod);

            if("GET".equalsIgnoreCase(requestMethod)){
                httpUrlConn.connect();
            }
            if(null!=outputStr){
                OutputStream outputStream = httpUrlConn.getOutputStream();
                outputStream.write(outputStr.getBytes());
                outputStream.close();
            }
            InputStream inputStream = httpUrlConn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream,"utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String str = null;
            while ((str=bufferedReader.readLine())!=null){
                buffer.append(str);
            }
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            inputStream=null;
            httpUrlConn.disconnect();
        }catch (Exception e){
            e.printStackTrace();
            log.error(e.getMessage());
        }
        return buffer.toString();
    }

}
