package com.zalesskyi.android.newsforclever.view.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zalesskyi.android.newsforclever.R;
import com.zalesskyi.android.newsforclever.model.Article;
import com.zalesskyi.android.newsforclever.view.listeners.ItemListener;

import java.util.ArrayList;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsHolder> {

    private List<Article> mArticles;
    private Context mContext;
    private ItemListener<Article> mListener;

    public NewsAdapter(Context ctx, ItemListener<Article> listener) {
        mContext = ctx;
        mListener = listener;
        mArticles = new ArrayList<>();
    }

    @NonNull
    @Override
    public NewsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_news_list, parent, false);

        return new NewsHolder(v, mContext, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsHolder holder, int position) {
        holder.bindArticle(mArticles.get(position));
    }

    @Override
    public int getItemCount() {
        return mArticles.size();
    }

    public void addItem(Article article) {
        mArticles.add(article);
        notifyDataSetChanged();
    }
}
