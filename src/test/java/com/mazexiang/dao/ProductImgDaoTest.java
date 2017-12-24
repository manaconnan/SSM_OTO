package com.mazexiang.dao;

import com.mazexiang.BaseTest;
import com.mazexiang.entity.ProductImg;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class ProductImgDaoTest extends BaseTest {
    @Autowired
    private ProductImgDao productImgDao;
    @Test
    public void queryPrductImgList() throws Exception {
    }

    @Test
    public void testBatchInsertProductImg() throws Exception {

        ProductImg img1 = new ProductImg();
        img1.setCreateTime(new Date());
        img1.setImgAddr("testAddr");
        img1.setImgDesc("testDesc");
        img1.setPriority(10);
        img1.setProductId(1L);


        ProductImg img2 = new ProductImg();
        img2.setCreateTime(new Date());
        img2.setImgAddr("testAddr");
        img2.setImgDesc("testDesc");
        img2.setPriority(10);
        img2.setProductId(1L);


        List<ProductImg> imgList = new ArrayList<>();
        imgList.add(img1);
        imgList.add(img2);
        int i = productImgDao.batchInsertProductImg(imgList);

        assertEquals(2,i);
    }

    @Test
    public void deleteProductImgByProductId() throws Exception {
    }

}