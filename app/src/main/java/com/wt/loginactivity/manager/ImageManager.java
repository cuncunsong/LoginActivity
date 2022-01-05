package com.wt.loginactivity.manager;

import com.wt.loginactivity.bean.Image;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.util.List;

public class ImageManager {

    //插入数据
    public boolean insertImage(String imagePath, int userId){

        Image image=new Image();
        image.setImageSource(imagePath);
        image.setUserId(userId);
        //创建数据库
        LitePal.getDatabase();
        return image.save();
    }
    //查找
     public boolean findImageByUserId(int userId){
        List<Image> imageList=DataSupport.findAll(Image.class);
         for (Image image : imageList) {
             if(image.getUserId() == userId){
                 return true;
             }
         }
         return false;
     }
}
