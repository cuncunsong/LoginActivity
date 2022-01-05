package com.wt.loginactivity.fragmentViewPage;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.wt.loginactivity.MySQLite;
import com.wt.loginactivity.bean.Article;
import com.wt.loginactivity.bean.Image;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MyselfAdapter extends FragmentPagerAdapter {

    FragmentArticle fragmentArticle;
    FragmentImage fragmentImage;

    public MyselfAdapter(@NonNull FragmentManager fm, Context context) {
        super(fm);
        //查询收藏文章
        List<Article> mArticleList=new ArrayList<>();
        SQLiteOpenHelper helper = MySQLite.getInstance(context);
        SQLiteDatabase db= helper.getReadableDatabase();
        if(db.isOpen()){
            Cursor cursor = db.rawQuery("select * from arColl", null);
            while(cursor.moveToNext()){
                String title=cursor.getString(1);
                String author=cursor.getString(2);
                mArticleList.add(new Article(title,author,new Date()));
            }
        }
        fragmentArticle = new FragmentArticle(mArticleList);


        List<Image> imageList= DataSupport.findAll(Image.class);
//        for (Image image : imageList) {
//            String urlPath=image.getImageSource();
//            Integer id=image.getId();
//            Integer userId=image.getUserId();
//            //imageList.add(new Image(id,urlPath,userId));
//        }
        String[] imageArray=new String[imageList.size()];
        for (int i = 0; i < imageArray.length; i++) {
            imageArray[i]=imageList.get(i).getImageSource();
        }
        Log.d("QX", "MyselfAdapter >>> imageArray = " + imageArray);
       fragmentImage = new FragmentImage(imageArray);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment=null;
        switch (position){
            case 0:
                fragment=fragmentArticle;
                break;
            case 1:
                fragment=fragmentImage;
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }

}
