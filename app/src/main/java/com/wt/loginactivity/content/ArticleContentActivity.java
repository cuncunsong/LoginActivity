package com.wt.loginactivity.content;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.wt.loginactivity.BaseActivity;
import com.wt.loginactivity.MySQLite;
import com.wt.loginactivity.R;
import com.wt.loginactivity.bean.Article;

import java.io.Serializable;

public class ArticleContentActivity extends BaseActivity{

    public static void actionStart(Context context,String title,String author,String content){
        Intent intent = new Intent(context, ArticleContentActivity.class);
        intent.putExtra("title",title);
        intent.putExtra("author",author);
        intent.putExtra("content",content);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.article_content);
        Serializable serializable=getIntent().getSerializableExtra("article");
        String title = getIntent().getStringExtra("title");
        String author = getIntent().getStringExtra("author");
        String content = getIntent().getStringExtra("content");
        ArticleContent articleContent= (ArticleContent) getSupportFragmentManager().findFragmentById(R.id.fragment_articleContent);
        articleContent.refresh(title,author,content);//刷新ArticleContent界面
        Button btnCollection =findViewById(R.id.btn_collection);
        Button btnFinish = findViewById(R.id.btn_finish);
        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        if(serializable instanceof Article){
                            SQLiteOpenHelper helper= MySQLite.getInstance(ArticleContentActivity.this);
                            SQLiteDatabase sqLiteDatabase=helper.getWritableDatabase();
                            if(sqLiteDatabase.isOpen()){
                                String sql="insert into arColl(title,author) values('"+title+"','"+author+"')";
                                sqLiteDatabase.execSQL(sql);
                            }
                            sqLiteDatabase.close();
                            Looper.prepare();
                            Toast.makeText(ArticleContentActivity.this,"收藏成功",Toast.LENGTH_SHORT).show();
                            Looper.loop();
                        }else {
                            Toast.makeText(ArticleContentActivity.this,"收藏失败",Toast.LENGTH_SHORT).show();
                        }
                    }
                }).start();
            }
        });
    }
}
