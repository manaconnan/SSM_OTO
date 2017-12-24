package com.mazexiang.web.shopadmin;


import com.mazexiang.dto.ProductCategoryExecution;
import com.mazexiang.dto.Result;
import com.mazexiang.entity.ProductCategory;
import com.mazexiang.entity.Shop;
import com.mazexiang.enums.ProductCategoryStateEnum;
import com.mazexiang.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("shopadmin")
public class ProductCategoryManagement {

    @Autowired
    private ProductCategoryService productCategoryService;

    @ResponseBody
    @RequestMapping(value="/removeproductcategory",method = RequestMethod.POST)
    private Map<String,Object> removeproductcategory(Long productCategoryId,HttpServletRequest request){
        Map<String,Object> modelMap = new HashMap<>();
        if(productCategoryId!=null&&productCategoryId>0){
            try {
                Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
                ProductCategory productCategory = new ProductCategory();
                productCategory.setShopId(currentShop.getShopId());
                productCategory.setProductCategoryId(productCategoryId);
                ProductCategoryExecution productCategoryExecution =
                        productCategoryService.deleteProductCategory(productCategory);
                if(productCategoryExecution.getState()==ProductCategoryStateEnum.SUCCESS.getState()){
                    modelMap.put("success",true);
                }else {
                    modelMap.put("success",false);
                    modelMap.put("errMsg",productCategoryExecution.getStateInfo());
                }
            }catch (Exception e){
                modelMap.put("success",false);
                modelMap.put("errMsg",e.getMessage());
            }

        }else {
            modelMap.put("success",false);
            modelMap.put("errMsg","传入的商品类别ID为空");
        }
        return modelMap;

    }

    @RequestMapping(value = "/getproductcategorylist",method= RequestMethod.GET)
    @ResponseBody
    public Result<List<ProductCategory>> getProductCategoryList(HttpServletRequest request){

        Shop currentShop =(Shop) request.getSession().getAttribute("currentShop");
        List<ProductCategory> list = null;
        if(currentShop!=null&&currentShop.getShopId()>0){
            list = productCategoryService.getProductCategoryList(currentShop.getShopId());
            return  new Result<>(true,list);
        }else {
            ProductCategoryStateEnum ps = ProductCategoryStateEnum.INNER_ERROR;
            return new Result<>(false,ps.getState(),ps.getStateInfo());
        }
    }

    @ResponseBody
    @RequestMapping(value = "/addproductcategories",method=RequestMethod.POST)
    private Map<String,Object>addProductCategories(@RequestBody List<ProductCategory> productCategoryList,
                                                   HttpServletRequest request){
        Map<String,Object   > modelMap  = new HashMap<>();
        Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
        for (ProductCategory pc: productCategoryList){
            pc.setShopId(currentShop.getShopId());
            pc.setCreateTime(new Date());
        }
        if(productCategoryList!=null&&productCategoryList.size()>0){
            try {

                ProductCategoryExecution productCategoryExecution = productCategoryService.batchAddProductCategory(productCategoryList);
                if(productCategoryExecution.getState()==ProductCategoryStateEnum.SUCCESS.getState()){
                    modelMap.put("success",true);
                }else {
                    modelMap.put("success",false);
                    modelMap.put("errMsg",productCategoryExecution.getStateInfo());
                }
            }catch (RuntimeException e){
                modelMap.put("success",false);
                modelMap.put("errMsg",e.getMessage());
                return modelMap;
            }
        }else {
            modelMap.put("success",false);
            modelMap.put("errMsg","请至少输入一个商品类别");
        }
        return modelMap;

    }


}
