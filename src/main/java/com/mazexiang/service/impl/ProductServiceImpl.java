package com.mazexiang.service.impl;

import com.mazexiang.dao.ProductDao;
import com.mazexiang.dao.ProductImgDao;
import com.mazexiang.dto.ImageHolder;
import com.mazexiang.dto.ProductExecution;
import com.mazexiang.entity.Product;
import com.mazexiang.entity.ProductImg;
import com.mazexiang.entity.Shop;
import com.mazexiang.enums.ProductStateEnum;
import com.mazexiang.exceptions.ProductOperationException;
import com.mazexiang.service.ProductService;
import com.mazexiang.util.ImageUtil;
import com.mazexiang.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductDao productDao;
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductImgDao productImgDao;


    @Override
    public ProductExecution getProductListByShopId(Long shopId) {

        List<Product> productList = productDao.queryAllProductsByShopId(shopId);

        return new ProductExecution(ProductStateEnum.SUCCESS,productList);
    }

    @Override
    public ProductExecution getProductByShopIdAndProductId(Long shopId, Long productId) {
        Product product = productDao.queryProductByShopIdAndProductId(shopId, productId);
        return new ProductExecution(ProductStateEnum.SUCCESS,product);
    }

    @Override
    public int modifyProductMainInfo(Product product) {
        //1。判断商品主缩略图是否修改，如果是，的话， 删除文件夹中原来的主图，并处理现在的图片然后保存

        //2。保存产品其他信息
        int effectedNum = productDao.updateProductByCondition(product);


        return effectedNum;
    }

    @Override
    public int modifyProductMainImg(Product product, ImageHolder thumbnail) {
        product = productDao.queryProductByShopIdAndProductId(product.getShop().getShopId(), product.getProductId());
        String storePath = product.getImgAddr();
        String basePath = PathUtil.getImgBasePath();
        File file = new File(basePath+storePath);
        ImageUtil.deleteFile(file);
        String targetPath = PathUtil.getProductImagePath(product);
        String path = ImageUtil.generateThumbnail(thumbnail, targetPath);
        product.setImgAddr(path);
        int effectedNum = productDao.updateProductByCondition(product);

        return effectedNum;
    }

    @Override
    public void modifyProductDetailImg(Product product, List<ImageHolder> imageHolderList) {
        productImgDao.deleteProductImgByProductId(product.getProductId());
        String targetAddr = PathUtil.getProductImagePath(product);
        List<ProductImg> productImgList = new ArrayList<>();

        for (ImageHolder thumbnail: imageHolderList){
            String productImgAddr = ImageUtil.generateThumbnail(thumbnail, targetAddr);
            //获取图片处理之后的地址
            ProductImg productImg = new ProductImg();
            productImg.setImgAddr(productImgAddr);
            productImg.setPriority(1);//优先级默认为1
            productImg.setCreateTime(new Date());
            productImg.setProductId(product.getProductId());
            productImgList.add(productImg);
        }
        try {
            //插入并保存 图片
           int effectedNum = productImgDao.batchInsertProductImg(productImgList);
        }catch (Exception e){
            throw new ProductOperationException("插入图片操作失败："+ e.getMessage());
        }


    }


    @Override
    @Transactional
    //1.处理缩略图，获取缩略图的相对路径并赋值给product
    //2.往tb_product表中写入商品信息，获取productID
    //3.结合productId批量处理商品详情图
    //4.将商品详情图列表批量插入tb_product_img
    public ProductExecution addProduct(Product product,ImageHolder thumbnail ,List<ImageHolder> thumbnailList) throws ProductOperationException {
       if(product==null||product.getShop()==null||product.getShop().getShopId()==null){
           return new ProductExecution(ProductStateEnum.NULL_PRODUCT);
       }else {

               product.setCreateTime(new Date());
               product.setLastEditTime(new Date());
               List<ProductImg> productImgList = new ArrayList<>();
               if(thumbnailList!=null&&thumbnailList.size()>0){ //传入的图片不为空

                        String targetAddr = PathUtil.getProductImagePath(product);

                        int effectedNum;
                        try {
                             effectedNum = productDao.insertProduct(product);
                            String productThumbnail = addProductImg(targetAddr, thumbnail);
                            product.setImgAddr(productThumbnail);
                        }catch (Exception e){
                            //e.printStackTrace();
                            throw new ProductOperationException("插入商品失败: " +e.getMessage());
                        }

                        if (effectedNum > 0) {
                            for (ImageHolder thumb : thumbnailList) {
                                String productImgAddr = addProductImg(targetAddr, thumb);
                                //获取图片处理之后的地址
                                ProductImg productImg = new ProductImg();
                                productImg.setImgAddr(productImgAddr);
                                productImg.setPriority(1);//优先级默认为1
                                productImg.setCreateTime(new Date());
                                productImg.setProductId(product.getProductId());
                                productImgList.add(productImg);
                            }
                            try {
                                //插入并保存 图片
                                effectedNum = productImgDao.batchInsertProductImg(productImgList);
                            }catch (Exception e){
                                throw new ProductOperationException("插入图片操作失败："+ e.getMessage());
                            }
                            if (effectedNum <= 0) {
                                throw new ProductOperationException("图片插入失败");
                            }
                            return new ProductExecution(ProductStateEnum.SUCCESS);
                        } else {
                            throw new ProductOperationException("添加商品失败");
                        }


               }else {
                   throw new ProductOperationException("商品图片不能为空");
               }
           }
    }


    private String addProductImg(String targetAddr , ImageHolder thumbnail){
        String productImgAddr = ImageUtil.generateThumbnail(thumbnail, targetAddr);
        return productImgAddr;
    }
}
