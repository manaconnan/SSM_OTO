package com.mazexiang.web.shopadmin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mazexiang.dto.ShopExecution;
import com.mazexiang.entity.Area;
import com.mazexiang.entity.PersonInfo;
import com.mazexiang.entity.Shop;
import com.mazexiang.entity.ShopCategory;
import com.mazexiang.enums.ShopStateEnum;
import com.mazexiang.service.AreaService;
import com.mazexiang.service.ShopCategoryService;
import com.mazexiang.service.ShopService;
import com.mazexiang.util.CodeUtil;
import com.mazexiang.util.HttpServletRequestUtils;
import org.omg.CORBA.OBJ_ADAPTER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "shopadmin" )
public class ShopManagementController {
    @Autowired
    private ShopService shopService;
    @Autowired
    private ShopCategoryService shopCategoryService;
    @Autowired
    private AreaService areaService;


    @RequestMapping(value = "getshopmanagementinfo",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getShopManagementInfo(HttpServletRequest request){
        Map<String,Object> modelMap =new HashMap<>();
        long shopId = HttpServletRequestUtils.getLong(request,"shopId");
        if(shopId<=0){
            Object currentShopObj = request.getSession().getAttribute("currentShop");
            if(currentShopObj==null){
                modelMap.put("redirect",true);
                modelMap.put("url","/o2o/shopadmin/shoplist");//TODO ???

            }else {
                Shop currentShop = (Shop) currentShopObj;
                modelMap.put("redirect",false);
                modelMap.put("shopId",currentShop.getShopId());

            }
        }else {
            Shop currentShop = new Shop();
            currentShop.setShopId(shopId);
            request.getSession().setAttribute("currentShop",currentShop);
            modelMap.put("redirect",false);
        }
        return modelMap;
    }


    @RequestMapping(value = "getshoplist",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getShopList(HttpServletRequest request){
        Map<String,Object> modelMap = new HashMap<>();
        PersonInfo user = new PersonInfo();
        user.setUserId(1L);
        user.setName("mana");
        try {
            Shop shopCondition = new Shop();
            shopCondition.setOwner(user);
            ShopExecution se = shopService.getShopList(shopCondition,0,100);
            modelMap.put("shopList",se.getShopList());
            modelMap.put("user",user);
            modelMap.put("success",true);
        }catch (Exception e){
            modelMap.put("success",false);
            modelMap.put("errMsg",e.getMessage());
        }
        return modelMap;
    }

    @RequestMapping(value = "/getshopbyid",method=RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getShopById(HttpServletRequest request){
        Map<String,Object> modelMap = new HashMap<>();
        Long shopId = HttpServletRequestUtils.getLong(request,"shopId");
        if(shopId>0){
            try {

                Shop shop = shopService.getByShopId(shopId);
                List<Area> areaList = areaService.getAreaList();
                modelMap.put("shop",shop);
                modelMap.put("areaList",areaList);
                modelMap.put("success",true);
            }catch (Exception e){
                modelMap.put("success",false);
                modelMap.put("errMsg",e.getMessage());
            }
        }else {
            modelMap.put("success",false);
            modelMap.put("errMsg","empty shopId");
        }
        return modelMap;
    }

    @RequestMapping(value = "/getshopinitinfo",method=RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getShopInitInfo(){
        Map<String ,Object> modelMap = new HashMap<>();
        List<ShopCategory> shopCategoryList;
        List<Area> areaList ;
        try{
            shopCategoryList = shopCategoryService.getShopCategoryList(new ShopCategory());
            areaList = areaService.getAreaList();
            modelMap.put("shopCategoryList",shopCategoryList);
            modelMap.put("areaList",areaList);
            modelMap.put("success",true);
        }catch (Exception e){
            modelMap.put("success",false);
            modelMap.put("errMsg",e.getMessage());
        }
        return modelMap;
    }

    @RequestMapping(value = "/registershop",method =  RequestMethod.POST)
    @ResponseBody
    public Map<String ,Object> registerShop(HttpServletRequest request){
        Map<String,Object> modelMap = new HashMap<>();
        if(!CodeUtil.checkVerifyCode(request)){
            modelMap.put("success",false);
            modelMap.put("errMsg","验证码输入错误");
            return modelMap;
        }
        //接受并转化前端request传过来的相应的参数， 包括店铺信息和图片信息 , 需要与前端约定 "shopStr"
        String shopStr = HttpServletRequestUtils.getString(request,"shopStr");
        //import com.fasterxml.jackson.databind.ObjectMapper中的 POJO 和JSON数据相互转换的类， 具体可参见github ： jackson-databind
        ObjectMapper mapper = new ObjectMapper();
        Shop shop  = null;

        // TODO
        PersonInfo owner =new PersonInfo();
        owner.setUserId(1L);
        shop.setOwner(owner);
        try{
            shop = mapper.readValue(shopStr,Shop.class);
        }catch (Exception e ){
            modelMap.put("success",false);
            modelMap.put("errorMsg", e.getMessage());
            return  modelMap;
        }
        //获取上传的文件流 , 将图片信息"shopImg"接受进来
        CommonsMultipartFile shopImg = null;
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());
        if(commonsMultipartResolver.isMultipart(request)){
            MultipartHttpServletRequest multipartHttpServletRequest=(MultipartHttpServletRequest) request;
            shopImg =(CommonsMultipartFile) multipartHttpServletRequest.getFile("shopImg");
        }else {
            modelMap.put("success",false);
            modelMap.put("errMsg","上传图片不能为空");
        }

        //注册店铺

        if (shop!=null&&shopImg!=null){
            //在用户登陆的时候获取用户信息
           // PersonInfo owner = (PersonInfo) request.getSession().getAttribute("user");
            shop.setOwner(owner);



            ShopExecution shopExecution = null;
            try {
                shopExecution = shopService.addShop(shop,shopImg.getInputStream(),shopImg.getOriginalFilename());
                if(shopExecution.getState()== ShopStateEnum.CHECK.getState()){
                    modelMap.put("success",true);
                    // 从session中获取用户可以操作的店铺列表
                    List<Shop> shopList =(List<Shop>) request.getSession().getAttribute("shoplist");
                    //如果是第一个店铺， 则新建
                    if(shopList==null){
                        shopList = new ArrayList<>();
                    }
                    shopList.add(shop);
                    request.getSession().setAttribute("shopList",shopList);

                }else {
                    modelMap.put("success",false);
                    modelMap.put("errMsg",shopExecution.getStateInfo());
                }
            } catch (IOException e) {
                modelMap.put("success",false);
                modelMap.put("errorMsg", e.getMessage());
            }

            return modelMap;

        }else {
            modelMap.put("success",false);
            modelMap.put("errMsg","请输入店铺信息");
            return modelMap;
        }

    }

    @RequestMapping(value = "/modifyshop",method =  RequestMethod.POST)
    @ResponseBody
    public Map<String ,Object> modifyShop(HttpServletRequest request){
        Map<String,Object> modelMap = new HashMap<>();
        if(!CodeUtil.checkVerifyCode(request)){
            modelMap.put("success",false);
            modelMap.put("errMsg","验证码输入错误");
            return modelMap;
        }
        //接受并转化前端request传过来的相应的参数， 包括店铺信息和图片信息 , 需要与前端约定 "shopStr"
        String shopStr = HttpServletRequestUtils.getString(request,"shopStr");
        //import com.fasterxml.jackson.databind.ObjectMapper中的 POJO 和JSON数据相互转换的类， 具体可参见github ： jackson-databind
        ObjectMapper mapper = new ObjectMapper();
        Shop shop  = null;


        try{
            shop = mapper.readValue(shopStr,Shop.class);
        }catch (Exception e ){
            modelMap.put("success",false);
            modelMap.put("errorMsg", e.getMessage());
            return  modelMap;
        }
        //获取上传的文件流 , 将图片信息"shopImg"接受进来
        CommonsMultipartFile shopImg = null;
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());
        if(commonsMultipartResolver.isMultipart(request)){
            MultipartHttpServletRequest multipartHttpServletRequest=(MultipartHttpServletRequest) request;
            shopImg =(CommonsMultipartFile) multipartHttpServletRequest.getFile("shopImg");
        }
        // TODO
        PersonInfo owner =new PersonInfo();
        owner.setUserId(1L);
        shop.setOwner(owner);
        // 修改店铺

        if (shop!=null&&shop.getShopId()!=null){
            ShopExecution shopExecution;
            try {
                if(shopImg==null){
                    shopExecution = shopService.addShop(shop,null,null);

                }else {

                    shopExecution = shopService.addShop(shop,shopImg.getInputStream(),shopImg.getOriginalFilename());
                }
                if(shopExecution.getState()== ShopStateEnum.SUCCESS.getState()){
                    modelMap.put("success",true);
                }else {
                    modelMap.put("success",false);
                    modelMap.put("errMsg",shopExecution.getStateInfo());
                }
            } catch (IOException e) {
                modelMap.put("success",false);
                modelMap.put("errorMsg", e.getMessage());
            }

            return modelMap;

        }else {
            modelMap.put("success",false);
            modelMap.put("errMsg","请输入店铺ID");
            return modelMap;
        }

    }


    /**
     * 这是一个将CommonsMultipartFile  靠InputStream转换成File类的方法
     * @param ins
     * @param file
     */
    private static void inputStreamToFile(InputStream ins, File file){
        OutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(file);
            byte[] buffer = new byte[1024];
            int len=0;
            while ((len=ins.read(buffer))!=-1){
                outputStream.write(buffer,0,len);
            }
        }catch (Exception e){
            throw new RuntimeException("调用inputStreamToFile产生异常： "+ e.getMessage());
        }finally {
            try {
                if(outputStream!=null){
                    outputStream.close();
                }
                if (ins!=null){
                    ins.close();
                }
            }catch (IOException e){
                throw new RuntimeException("inputStreamToFile关闭io产生异常： "+ e.getMessage());
            }
        }
    }


}
