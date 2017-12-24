package com.mazexiang.web.shopadmin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mazexiang.dto.ImageHolder;
import com.mazexiang.dto.ProductExecution;
import com.mazexiang.dto.Result;
import com.mazexiang.entity.Product;
import com.mazexiang.entity.Shop;
import com.mazexiang.enums.ProductCategoryStateEnum;
import com.mazexiang.enums.ProductStateEnum;
import com.mazexiang.exceptions.ProductOperationException;
import com.mazexiang.service.ProductService;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("shopadmin")
public class ProductManagementController {

    @Autowired
    private ProductService productService;
    private static final int IMAGEMAXCOUNT=6;

    @RequestMapping(value = "modifyproduct",method = RequestMethod.POST)
    @ResponseBody
    private Map<String,Object> modifyProduct(HttpServletRequest request){
        Map<String,Object> modelMap = new HashMap<>();
        if(!CodeUtil.checkVerifyCode(request)){
            modelMap.put("success",false);
            modelMap.put("errMsg","验证码输入错误");
            return modelMap;
        }
        //接受前端参数的变量，包括商品、缩略图、详情图列表
        ObjectMapper mapper = new ObjectMapper();
        Product product = new Product() ;
        String productStr = HttpServletRequestUtils.getString(request,"productStr");
        MultipartHttpServletRequest multipartRequest;
        ImageHolder thumbnail = new  ImageHolder();
        List<ImageHolder> productImgList = new ArrayList<>();
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());
        try{
            //判断请求中的文件流 包括缩略图 和详情图
                boolean hasFile = multipartResolver.isMultipart(request);
                if(hasFile){
                    multipartRequest = (MultipartHttpServletRequest) request;
                    CommonsMultipartFile thumbnailFile = (CommonsMultipartFile) multipartRequest.getFile("thumbnail");
                    if (thumbnailFile!=null){

                        thumbnail = new ImageHolder(thumbnailFile.getOriginalFilename(),thumbnailFile.getInputStream());
                    }
                    for (int i = 0 ;i<IMAGEMAXCOUNT;i++){
                        CommonsMultipartFile productImgFile = (CommonsMultipartFile)
                                multipartRequest.getFile("productImg"+i);
                        if (productImgFile!=null){
                            ImageHolder productImg = new ImageHolder(productImgFile.getOriginalFilename(),
                                    productImgFile.getInputStream());
                            productImgList.add(productImg);
                        }else {
                            break;
                        }
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
                modelMap.put("success",false);
                modelMap.put("errMsg",e.getMessage());
                return  modelMap;
            }
        try {
            //常识将前端闯过来的表单string流 并将其转换城Product对象
            product = mapper.readValue(productStr,Product.class);
        }catch (Exception e){
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }
        if(product!=null&&product.getProductId()!=null){

                //从session中获取当前店铺的Id 并赋值给product，减少对前端数据的依赖

                Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");

                if (currentShop == null){
                    modelMap.put("success",false);
                    modelMap.put("errMsg","当前登陆信息为空，请重新登陆");
                    return modelMap;
                }

                Shop shop = new Shop();
                shop.setShopId(currentShop.getShopId());
                product.setShop(shop);

                int effectedNum;
                try {
                     effectedNum= productService.modifyProductMainInfo(product);

                } catch (ProductOperationException e){
                        e.printStackTrace();
                        modelMap.put("success",false);
                        modelMap.put("errMsg",e.getMessage());
                        return modelMap;
                    }
                if(effectedNum>0){
                    if(thumbnail!=null){
                        try {

                            productService.modifyProductMainImg(product, thumbnail);
                        }catch (Exception e){
                            e.printStackTrace();
                            modelMap.put("success",false);
                            modelMap.put("errMsg","主图插入失败");
                            return modelMap;
                        }

                    }
                    if(productImgList!=null&&productImgList.size()>0){
                        try {
                            productService.modifyProductDetailImg(product,productImgList);
                        }catch (Exception e){
                            e.printStackTrace();
                            modelMap.put("success",false);
                            modelMap.put("errMsg","商品图修改失败");
                            return modelMap;
                        }
                    }


                }

            modelMap.put("success",true);
            return modelMap;
        }else {
            modelMap.put("success",false);
            modelMap.put("errMsg","商品Id不能为空");
            return  modelMap;
        }
    }


    @ResponseBody
    @RequestMapping(value = "/getproductbyid",method=RequestMethod.GET)
    private Map<String,Object> getProductById(HttpServletRequest request){
        Map<String ,Object> modelMap = new HashMap<>();

        Long productId = HttpServletRequestUtils.getLong(request, "productId");
        Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");

        if(productId==null||productId<=0){
            modelMap.put("success",false);
            modelMap.put("errMsg","未能成功获取商品ID");
            return modelMap;
        }
        ProductExecution productExecution = productService.getProductByShopIdAndProductId(
                currentShop.getShopId(),productId);
       if(productExecution.getState()==ProductStateEnum.SUCCESS.getState()){
           Product product = productExecution.getProduct();
           modelMap.put("success",true);
           modelMap.put("product",product);
           return modelMap;
       }else {
           modelMap.put("success",false);
           modelMap.put("errMsg","获取商品失败");
           return modelMap;
       }
    }

    @RequestMapping(value = "/addproduct",method= RequestMethod.POST)
    @ResponseBody
    private Map<String,Object> addProduct(HttpServletRequest request){
        Map<String,Object> modelMap = new HashMap<>();
        if(!CodeUtil.checkVerifyCode(request)){
            modelMap.put("success",false);
            modelMap.put("errMsg","验证码输入错误");
            return modelMap;
        }
        //接受前端参数的变量，包括商品、缩略图、详情图列表
        ObjectMapper mapper = new ObjectMapper();
        Product product = new Product() ;
        String productStr = HttpServletRequestUtils.getString(request,"productStr");
        MultipartHttpServletRequest multipartRequest;
        ImageHolder thumbnail;
        List<ImageHolder> productImgList = new ArrayList<>();
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());
        try{
            //判断请求中的文件流 包括缩略图 和详情图
            if(multipartResolver.isMultipart(request)){
                multipartRequest = (MultipartHttpServletRequest) request;
                CommonsMultipartFile thumbnailFile = (CommonsMultipartFile) multipartRequest.getFile("thumbnail");
                thumbnail = new ImageHolder(thumbnailFile.getOriginalFilename(),thumbnailFile.getInputStream());
                for (int i = 0 ;i<IMAGEMAXCOUNT;i++){
                    CommonsMultipartFile productImgFile = (CommonsMultipartFile)
                            multipartRequest.getFile("productImg"+i);
                    if (productImgFile!=null){
                        ImageHolder productImg = new ImageHolder(productImgFile.getOriginalFilename(),
                                productImgFile.getInputStream());
                        productImgList.add(productImg);
                    }else {
                        break;
                    }
                }
            }else {
                modelMap.put("success",false);
                modelMap.put("errMsg","上传图片不能为空");
                return  modelMap;
            }
        }catch (Exception e){
            modelMap.put("success",false);
            modelMap.put("errMsg",e.getMessage());
            return  modelMap;
        }
        try {
            //常识将前端闯过来的表单string流 并将其转换城Product对象
            product = mapper.readValue(productStr,Product.class);
        }catch (Exception e){
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }
        if(product!=null&&thumbnail!=null&&productImgList.size()>0){
            try {
                //从session中获取当前店铺的Id 并赋值给product，减少对前端数据的依赖
                Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
                Shop shop = new Shop();
                shop.setShopId(currentShop.getShopId());
                product.setShop(shop);
                product.setEnableStatus(0);// 设置商品默认不上架
                ProductExecution pe = productService.addProduct(product, thumbnail,productImgList);
                if(pe.getState() == ProductStateEnum.SUCCESS.getState()){
                    modelMap.put("success",true);
                }else {
                    modelMap.put("success",false);
                    modelMap.put("errMsg",pe.getStateInfo());
                }
            }catch (ProductOperationException e){
                e.printStackTrace();
                modelMap.put("success",false);
                modelMap.put("errMsg",e.getMessage());
            }
        }else {
            modelMap.put("success",false);
            modelMap.put("errMsg","请输入商品信息");
        }
        return modelMap;
    }

    @RequestMapping(value = "getproductlist",method = RequestMethod.GET)
    @ResponseBody
    private Result<List<Product>> getProdutList(HttpServletRequest request){

        Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
        Long shopId = currentShop.getShopId();
        if(shopId<=0){
            //TODO 跳转到登陆页面
            return new Result<>(false,-1,"登陆信息错误");
        }
        try {
            ProductExecution productExecution= productService.getProductListByShopId(shopId);
            List<Product> productList = productExecution.getProductList();
            if(productList!=null&&productList.size()>0){

                return new Result<>(true,productList);

            }else {
               return new Result<>(false,-2,"商品数为空");
            }


        }catch (Exception e ){
            e.printStackTrace();
            return new Result<>(false,-1000,e.getMessage());
        }

    }
}
