package com.wt.loginactivity.fragmentViewPage;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wt.loginactivity.R;
import com.wt.loginactivity.bean.Article;
import com.wt.loginactivity.content.ArticleContentActivity;

import java.util.List;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ViewHolder> {

    private FragmentManager fragmentArticle;
    private List<Article> mArticleList;
    Context context;

    public ArticleAdapter(List<Article> articleList, Context context) {
        this.context=context;
        this.mArticleList=articleList;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView etTitle,etAuthor,etTime;
        private final LinearLayout articleItem;

        public ViewHolder(View view) {
            super(view);
            etTitle= view.findViewById(R.id.et_title);
            etAuthor=view.findViewById(R.id.et_author);
            etTime=view.findViewById(R.id.et_time);
            articleItem=view.findViewById(R.id.article_item);
        }
    }

    //创建viewHolder实例
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.article_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        holder.articleItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Article article = mArticleList.get(position);
                Intent intent = new Intent(context, ArticleContentActivity.class);
                intent.putExtra("article",mArticleList.get(position));
                intent.putExtra("title",article.getTitle());
                intent.putExtra("author",article.getAuthor());
                intent.putExtra("content",article.getContent());
                context.startActivity(intent);
               // Toast.makeText(v.getContext(), "you clicked this" + article.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });
        return holder;
    }

    //对recycleView子项数据进行复制
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Article article=mArticleList.get(position);
        holder.etTitle.setText(article.getTitle());
        holder.etAuthor.setText(article.getAuthor());
        holder.etTime.setText(article.getTime());
    }
    //统计recycleView的子项个数
    @Override
    public int getItemCount() { return mArticleList.size(); }

}
