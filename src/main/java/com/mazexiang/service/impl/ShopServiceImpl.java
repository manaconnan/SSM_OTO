package com.mazexiang.service.impl;

import com.mazexiang.dao.ShopDao;
import com.mazexiang.dto.ShopExecution;
import com.mazexiang.entity.Shop;
import com.mazexiang.enums.ShopStateEnum;
import com.mazexiang.exceptions.ShopOperationException;
import com.mazexiang.service.ShopService;
import com.mazexiang.util.ImageUtil;
import com.mazexiang.util.PageCalculator;
import com.mazexiang.util.PathUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.Date;
import java.util.List;


@Service
public class ShopServiceImpl implements ShopService {

    private static Logger logger = LoggerFactory.getLogger(ShopServiceImpl.class);

    @Autowired
    private ShopDao shopDao;


    @Override
    @Transactional
    public ShopExecution addShop(Shop shop, InputStream shopImgInputStream, String fileName) {
        //空值判断
        if (shop==null){
            return new ShopExecution(ShopStateEnum.NULL_SHOP);
        }
        try {
            //给店铺信息赋初始值
            shop.setEnableStatus(0);
            shop.setCreateTime(new Date());
            shop.setLastEditTime(new Date());
            //添加店铺信息到数据库
            int effectedNum = shopDao.insertShop(shop);
            if (effectedNum<=0){
                //只有跑出ShopOperationException的时候，事务才会回滚
                throw new ShopOperationException("店铺插入失败");
            }else {
                if(shopImgInputStream !=null){
                //如果传进来的图片不为空， 则为店铺添加图片
                    try {
                        addShopImg(shop, shopImgInputStream , fileName);
                    }catch (Exception e){
                        throw new ShopOperationException("addShopImg error: "+ e.getMessage());
                    }
                    //更新店铺图片信息
                    effectedNum = shopDao.updateShop(shop);
                    if (effectedNum<=0){
                        throw new ShopOperationException("店铺更新图片地址失败");
                    }
                }
            }
        }catch (Exception e){
            throw new ShopOperationException("addShop error : "+e.getMessage());
        }

        return new ShopExecution(ShopStateEnum.SUCCESS,shop);
    }

    @Override
    public Shop getByShopId(long shopId) {
        return shopDao.queryByShopId(shopId);
    }

    @Override
    public ShopExecution modifyShop(Shop shop, InputStream shopImgInputStream, String fileName)
            throws ShopOperationException {
        if(shop==null||shop.getShopId()==null){
            return new ShopExecution(ShopStateEnum.NULL_SHOP);
        }else {

            try {
                //1.判断是否需要处理图片

                if (shopImgInputStream != null && fileName != null && !"".equals(fileName)) {
                    Shop tempShop = shopDao.queryByShopId(shop.getShopId());
                    if (tempShop.getShopImg() != null) {
                        ImageUtil.deleteFileOrPath(tempShop.getShopImg());
                    }
                    addShopImg(shop, shopImgInputStream, fileName);
                }

                //2. 跟新店铺信息
                shop.setLastEditTime(new Date());
                int effectedNum = shopDao.updateShop(shop);
                if (effectedNum <= 0) {
                    return new ShopExecution(ShopStateEnum.INNER_ERROR);
                } else {
                    return new ShopExecution(ShopStateEnum.SUCCESS, shop);
                }
            }catch (Exception e){
                throw new ShopOperationException("modifyShopError :"+ e.getMessage());
            }
        }


    }

    @Override
    public ShopExecution getShopList(Shop shopCondition, int pageIndex, int pageSize) {

        int rowIndex = PageCalculator.calculateRowIndex(pageIndex,pageSize);
        List<Shop> shopList = shopDao.queryShopList(shopCondition,rowIndex,pageSize);
        int count = shopDao.queryShopCount(shopCondition);
        ShopExecution se = new ShopExecution();
        if(shopList!=null){
            se.setShopList(shopList);
            se.setCount(count);
        }else {
            se.setState(ShopStateEnum.INNER_ERROR.getState());
        }
        return se;
    }

    private void addShopImg(Shop shop, InputStream shopImgInputStream ,String fileName) {
        //获取shop图片目录的相对值路径
        String targetAddr = PathUtil.getShopImagePath(shop.getShopId());

        String shopImgAddr = ImageUtil.generateThumbnail(shopImgInputStream,fileName ,targetAddr);

        //将修改后的图片路径更新到shop对象中
        shop.setShopImg(shopImgAddr);

    }
}
