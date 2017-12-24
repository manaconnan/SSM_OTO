package com.mazexiang.web.wechat;


import com.mazexiang.dto.WechatUser;
import com.mazexiang.dto.UserAccessToken;
import com.mazexiang.util.wexin.WechatUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("wechatlogin")
/**
 * 获取关注公众号之后的微信用户信息的接口，如果在微信浏览器里访问
 * * https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx83fb7b6109e53acd&redirect_uri=http://59.110.142.131/o2o/wechatlogin/logincheck&role_type=1&response_type=code&scope=snsapi_userinfo&state=1#wechat_redirect
 * 则这里将会获取到code,之后再可以通过code获取到access_token 进而获取到用户信息

 */
public class WechatLoginController {
    private static Logger log = LoggerFactory.getLogger(WechatController.class);

    @RequestMapping(value = "/logincheck",method = RequestMethod.GET)
    public String doGet(HttpServletRequest request, HttpServletResponse response){
        log.debug("wexin login get..");
        String code = request.getParameter("code");
        log.debug("weixin login code: "+ code);
        WechatUser user = null;
        String openId = null;
        if (code!=null){
            UserAccessToken token;
            try {
                //通过code获取access_token
                token = WechatUtil.getUserAccessToken(code);
                log.debug("wexin login token:"+token);

                //通过token获取accessToken
                String accessToken = token.getAccessToken();

                //通过token 获取openId
                openId = token.getOpenId();
                //通过access_token和openId获取用户昵称等信息
                user = WechatUtil.getUserInfo(accessToken,openId);
                log.debug("weixin login user:"+user.toString());
            }catch (IOException e){
                log.error("error in getUserAccessToken or  else;" +e.getMessage());
                e.printStackTrace();
            }
        }
        //// ======todo begin====== //
        // 前面咱们获取到openId后，可以通过它去数据库判断该微信帐号
        // 是否在我们网站里有对应的帐号了，
        // 没有的话这里可以自动创建上，直接实现微信与咱们网站的无缝对接。
        //=========todo end=======

        if (user !=null){
            //获取成功后指定的访问路径
            return "shop/shoplist";
        }else{
            return null;
        }
    }

}
