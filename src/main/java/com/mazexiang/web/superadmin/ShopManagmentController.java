package com.mazexiang.web.superadmin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "superadmin" ,method = RequestMethod.GET)
public class ShopManagmentController {

    @RequestMapping("/shopregister")
    public String shopRegister(){
        return "shop/shopRegister";
    }

}
