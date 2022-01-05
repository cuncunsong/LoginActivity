package com.wt.loginactivity.fragmentViewPage;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.wt.loginactivity.R;
import com.wt.loginactivity.bean.Article;
import com.wt.loginactivity.bean.Image;
import com.wt.loginactivity.manager.ImageManager;

import java.util.ArrayList;
import java.util.List;

public class FragmentMyself extends Fragment implements RadioGroup.OnCheckedChangeListener, ViewPager.OnPageChangeListener {

    private static final String TAG = "FragmentMyself";
    private TextView etUserName;
    private RadioGroup radioGroup;
    private RadioButton raArticles;
    private RadioButton raImages;
    private List<Article> mArticleList=new ArrayList<>();
    private List<Image> mImageList=new ArrayList<>();
    private RecyclerView collArticle,collImage;
    private ViewPager myselfViewPager;
    private ArticleAdapter articleAdapter;
    private ImageAdapter imagerAdapter;
    private ImageManager imageManager;
    private FragmentManager fragmentManager;
    private String[] imageArray;
    MyselfAdapter myselfAdapter;

    public FragmentMyself() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_myself, container, false);
        etUserName = view.findViewById(R.id.et_username);
        //得到从activity得到的数据
        if (getArguments() != null) {
            String username = getArguments().getString("username");
            etUserName.setText("欢迎您，" + username);
        }
        myselfAdapter = new MyselfAdapter(getChildFragmentManager(), getContext());

        radioGroup = view.findViewById(R.id.radioGroup);
        raArticles = view.findViewById(R.id.ra_articles);
        raImages = view.findViewById(R.id.ra_images);
        myselfViewPager = view.findViewById(R.id.myselfViewPager);

        radioGroup.setOnCheckedChangeListener(this::onCheckedChanged);

        myselfViewPager.setAdapter(myselfAdapter);
        myselfViewPager.setCurrentItem(0);
        myselfViewPager.addOnPageChangeListener(this);
        raArticles.setChecked(true);

//        queryArticle();
//        queryImage();
//        collArticle = view.findViewById(R.id.list_collArticle);
//        collImage = view.findViewById(R.id.list_collImage);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
//        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        collArticle.setLayoutManager(layoutManager);
//        StaggeredGridLayoutManager layoutManager1 = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
//        collImage.setLayoutManager(layoutManager1);
//        articleAdapter = new ArticleAdapter(mArticleList,getContext());
//        collArticle.setAdapter(articleAdapter);
//        imagerAdapter=new ImageAdapter(imageArray,getContext());
//        collImage.setAdapter(imagerAdapter);
        Log.e(TAG,"onCreateView");
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG,"onCreate");
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.e(TAG,"onAttach");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.e(TAG,"onActivityCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e(TAG,"onStart");
    }
    @Override
    public void onResume() {
        super.onResume();
        Log.e(TAG,"onResume");
    }
    @Override
    public void onPause() {
        super.onPause();
        Log.e(TAG,"onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e(TAG,"onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.e(TAG,"onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG,"onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.e(TAG,"onDetach");
    }
//
//        public void queryArticle(){
//        //查询收藏文章
//        SQLiteOpenHelper helper = MySQLite.getInstance(getContext());
//        SQLiteDatabase db= helper.getReadableDatabase();
//        if(db.isOpen()){
//            Cursor cursor = db.rawQuery("select * from arColl", null);
//            while(cursor.moveToNext()){
//                String title=cursor.getString(1);
//                String author=cursor.getString(2);
//                mArticleList.add(new Article(title,author,new Date()));
//            }
//        }
//    }
//    public void queryImage(){
//        List<Image> imageList= DataSupport.findAll(Image.class);
//        for (Image image : imageList) {
//            String urlPath=image.getImageSource();
//            Integer id=image.getId();
//            Integer userId=image.getUserId();
//            mImageList.add(new Image(id,urlPath,userId));
//        }
//        imageArray=new String[mImageList.size()];
//        for (int i = 0; i < imageArray.length; i++) {
//            imageArray[i]=mImageList.get(i).getImageSource();
//        }
//    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (i){
            case R.id.ra_articles:
                myselfViewPager.setCurrentItem(0);
                break;
            case R.id.ra_images:
                myselfViewPager.setCurrentItem(1);
                break;
        }

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

        if(state==2){
            int currentItem = myselfViewPager.getCurrentItem();
            switch (currentItem){
                case 0:
                    raArticles.setChecked(true);
                    break;
                case 1:
                    raImages.setChecked(true);
                    break;
            }
        }
    }

}
