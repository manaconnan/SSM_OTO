package com.mazexiang.web.shopadmin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("shopadmin")
public class ShopAdminController {

    @RequestMapping("productmanage")
    public String productManage(){
        return "shop/productmanage";
    }

    @RequestMapping("/productoperation")
    public String productOperation(){
        return "shop/productoperation";
    }
    @RequestMapping(value = "/shopoperation", method = RequestMethod.GET)
    public String shopOperation(){
        return "shop/shopoperation";
    }

    @RequestMapping("/shoplist")
    public String shopList(){
        return "shop/shoplist";
    }
    @RequestMapping("/shopmanagement")
    public String shopManagement(){
        return "shop/shopmanagement";
    }
    @RequestMapping("productcategorymanagement")
    public String productCategoryManagement(){
        return "shop/productcategorymanagement";
    }

}
