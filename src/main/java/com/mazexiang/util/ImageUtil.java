package com.mazexiang.util;

import com.mazexiang.dto.ImageHolder;
import net.coobird.thumbnailator.Thumbnailator;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.FieldError;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class ImageUtil {
    private static  String basePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
    private static final SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    private static final Random r = new Random();
    private static Logger logger = LoggerFactory.getLogger(ImageUtil.class);

    /**
     * 将CommonsMultipartFile转换成File类
     *
     * @param cFile
     * @return
     */
    public static File transferCommonsMultipartFileToFile(CommonsMultipartFile cFile) {
        File newFile = new File(cFile.getOriginalFilename());
        try {
            cFile.transferTo(newFile);
        } catch (IllegalStateException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        return newFile;
    }

    /**
     * 处理缩略图， 并返回生成图篇的相对路径
     * @param
     * @param targetAddr
     * @return
     */
    public static String generateThumbnail(ImageHolder thumbnail, String targetAddr){
        String realFileName = getRandomFileName();
        String extension = getFileExtension(thumbnail.getImageName());
        makeDirPath(targetAddr);

        String relativeAddr =targetAddr+realFileName+extension;
        logger.debug(" current relativeAddr is :"+ relativeAddr);
        File dest = new File(PathUtil.getImgBasePath()+relativeAddr);
        logger.debug("current complete addr is: "+ PathUtil.getImgBasePath()+relativeAddr);
        try{
            Thumbnails.of(thumbnail.getImage()).size(200,200).watermark(Positions.CENTER ,
                    ImageIO.read(new File(basePath+"/watermark.png")),0.8f).outputQuality(0.8f).toFile(dest);

        }catch (Exception e){
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        return relativeAddr;
    }

    private static void makeDirPath(String targetAddr) {
        String realFileParentPath = PathUtil.getImgBasePath()+targetAddr;
        File dirPath = new File(realFileParentPath);
        if(!dirPath.exists()){
            dirPath.mkdirs();
        }
    }

    /**
     * 获取文件流的扩展名
     * @param
     * @return
     */
    private static String getFileExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }

    /**
     * 生成随即文件名
     * @return
     */
    private static String getRandomFileName() {
        //获取随即的5位数
        int ranNum = r.nextInt(88888)+10000;
        String nowTimeStr = sDateFormat.format(new Date());

        return  nowTimeStr+ranNum;
    }

    /**
     * 传入的storePath是相对路径
     * 如果storePath是文件路径， 则删除文件， 如果storePath是目录路径，则删除目录及以下所有文件
     * @param storePath 是相对路径！！！
     */
    public static void deleteFileOrPath(String storePath){
        File fileOrPath = new File(PathUtil.getImgBasePath()+storePath);
        if(fileOrPath.exists()){
            if(fileOrPath.isDirectory()){
                File[] lists = fileOrPath.listFiles();
                for (File file : lists){
                    file.delete();
                }
            }
            fileOrPath.delete();
        }

    }
    public static void deleteFile(File file){
        if (file.exists()){

            file.delete();
        }else return;
    }
    public static void main(String[] args) throws IOException {

//        Thumbnails.of(new File("/Users/mazexiang/Workspace/resources/testPicture.jpg"))
//                .size(800,800).watermark(Positions.BOTTOM_RIGHT,
//                ImageIO.read(new File(basePath+"watermark.png")),0.25f)
//                .outputQuality(0.8f).toFile("/Users/mazexiang/Workspace/resources/newPicture.jpg");


    }
}
