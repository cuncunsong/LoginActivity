package com.wt.loginactivity.content;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.wt.loginactivity.R;

public class ArticleContent extends Fragment {

    private View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_article_content, container, false);
        return view;
    }
    public void refresh(String title,String author,String content){
        Button btnCollection = view.findViewById(R.id.btn_collection);
        TextView arTitle = view.findViewById(R.id.ar_title);
        TextView arAuthor = view.findViewById(R.id.ar_author);
        TextView arContent = view.findViewById(R.id.ar_content);
        arAuthor.setText(author);
        arContent.setText(content);
        arTitle.setText(title);
    }
}
