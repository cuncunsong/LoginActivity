package com.wt.loginactivity.fragmentViewPage;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wt.loginactivity.R;
import com.wt.loginactivity.bean.Article;

import java.util.ArrayList;
import java.util.List;

public class FragmentArticle extends Fragment {

    private static final String TAG = "FragmentArticle";
    private RecyclerView listView;
    private List<Article> articleList=new ArrayList<>();

    public FragmentArticle(List<Article> mArticleList) {
        this.articleList=mArticleList;
    }
    public FragmentArticle() { }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_article,container,false);
        //initArticle();
        listView = view.findViewById(R.id.list_view);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        listView.setLayoutManager(layoutManager);
        ArticleAdapter adapter = new ArticleAdapter(articleList,this.getContext());
        listView.setAdapter(adapter);
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
    //    private void initArticle() {
//        articleList.add(new Article("小王子","优秀作家","2021-12-14"));
//        articleList.add(new Article("云边有个小卖部","张嘉佳","2021-12-14"));
//        articleList.add(new Article("活着","余华","2021-12-14"));
//        articleList.add(new Article("骆驼祥子","老舍","2021-12-14"));
//        articleList.add(new Article("笑话","李诞","2021-12-14"));
//        for (int i = 0; i < 95; i++) {
//            articleList.add(new Article("十万个冷笑话"+i,"讲冷笑话的人",""));
//        }
//    }

}
