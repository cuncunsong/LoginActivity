package com.wt.loginactivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.wt.loginactivity.bean.Article;
import com.wt.loginactivity.bean.ImageUtil;
import com.wt.loginactivity.fragmentViewPage.FragmentArticle;
import com.wt.loginactivity.fragmentViewPage.FragmentImage;
import com.wt.loginactivity.fragmentViewPage.FragmentMyself;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity{

    private static final String TAG = "MainActivity";

    private Button btnArticle,btnImage,btnMyself;
    private ViewPager viewPager;
    private FragmentManager fragmentManager;
    String[] imageArray;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Button forceOffLine = findViewById(R.id.force_lineOut);
//        forceOffLine.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //强制用户下线
//                Intent intent = new Intent("com.wt.loginactivity.FORCE_OFFLINE");
//                sendBroadcast(intent);
//            }
//        });

        //初始化按钮，绑定布局文件的按钮并实现点击事件
        btnInit();
        //绑定viewPager并获得fragmentManager实例
        viewPager = findViewById(R.id.fragment_viewPager);
        fragmentManager=getSupportFragmentManager();

        //设置viewPager的起始页面和按钮的起始状态
        viewPager.setCurrentItem(0);
        btnArticle.setBackgroundColor(0xFF87F00D);

        //设置viewPager的Adapter，得到当前的Fragment页面
        viewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                return createFragment(position);
            }

            @Override
            public int getCount() {
                return 3;
            }
        });

        //设置viewPager的界面改变监听
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }


            @Override
            public void onPageSelected(int position) {

                clearAllBtnStatus();

                switch (position){
                    case 0:
                        btnArticle.setBackgroundColor(0xFF87F00D);
                        break;
                    case 1:
                        btnImage.setBackgroundColor(0xFF87F00D);
                        break;
                    case 2:
                        btnMyself.setBackgroundColor(0xFF87F00D);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        Log.e(TAG,"onCreate");
    }

    private void btnInit(){

        initArticle();
        imageArray = ImageUtil.imageUrls;
        Log.d("QX", "MainActivity >>> ImageUtil = " + imageArray);
        btnArticle=findViewById(R.id.btn_article);
        btnImage=findViewById(R.id.bt_image);
        btnMyself=findViewById(R.id.btn_myself);

        btnArticle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 viewPager.setCurrentItem(0);
            }
        });
        btnImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(1);
            }
        });
        btnMyself.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(2);
                Intent intent=getIntent();
                String username = intent.getStringExtra("username");
                String id = intent.getStringExtra("id");
                //将用户名通过Bundle传递到fragmentMyself
                FragmentMyself myself=new FragmentMyself();
                Bundle bundle = new Bundle();
                //存入数据到bundle对象
                bundle.putString("username",username);
                bundle.putString("id",id);
                //调用setArguments方法，传入bundle对象
                myself.setArguments(bundle);//数据传递到fragment中
                FragmentManager fragmentManager=getSupportFragmentManager();
                FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
                //替换显示的fragment
                //Fragment fragmentById = fragmentManager.findFragmentById(R.id.et_username);
                fragmentTransaction.replace(R.id.username_layout,myself);
                fragmentTransaction.commit();
            }
        });

       clearAllBtnStatus();
    }

    //用于清除按钮的背景颜色状态
    private void clearAllBtnStatus() {

        btnArticle.setBackgroundColor(0xffffffff);
        btnImage.setBackgroundColor(0xffffffff);
        btnMyself.setBackgroundColor(0xffffffff);
    }

    private Fragment createFragment(int position){

        switch (position){
            case 0:
                return new FragmentArticle(articleList);
            case 1:
//                Log.d("QX", "MainActivity >>> imageArray = " + imageArray);
                return new FragmentImage(imageArray);
            case 2:
                return new FragmentMyself();
            default:
                break;
        }
        return null;
    }
    private List<Article> articleList=new ArrayList<>();
    private void initArticle() {
        articleList.add(new Article("小王子","优秀作家","2021-12-14"));
        articleList.add(new Article("云边有个小卖部","张嘉佳","2021-12-14"));
        articleList.add(new Article("活着","余华","2021-12-14"));
        articleList.add(new Article("骆驼祥子","老舍","2021-12-14"));
        articleList.add(new Article("笑话","李诞","2021-12-14"));
        for (int i = 0; i < 95; i++) {
            articleList.add(new Article("十万个冷笑话"+i,"讲冷笑话的人",""));
        }
    }
    //菜单栏

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.force_lineOut:
                //强制用户下线
                Intent intent = new Intent("com.wt.loginactivity.FORCE_OFFLINE");
                sendBroadcast(intent);
                break;
            default:
                break;
        }
        return true;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG,"onDestroy");
    }
    @Override
    protected void onStart() {
        super.onStart();
        Log.e(TAG,"onStart");
    }
    @Override
    protected void onPause() {
        super.onPause();
        Log.e(TAG,"onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(TAG,"onStop");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG,"onResume");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e(TAG,"onRestart");
    }


}