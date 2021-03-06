package com.zalesskyi.android.newsforclever.view.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.zalesskyi.android.newsforclever.R;
import com.zalesskyi.android.newsforclever.model.Article;
import com.zalesskyi.android.newsforclever.view.listeners.ItemListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsHolder extends RecyclerView.ViewHolder {

    private Context mContext;
    private ItemListener<Article> mListener;

    private View mParent;

    @BindView(R.id.article_title)
    TextView mArticleTitle;

    @BindView(R.id.article_description)
    TextView mArticleDescription;

    @BindView(R.id.article_image)
    ImageView mArticleImage;

    @BindView(R.id.article_source)
    TextView mArticleSource;

    @BindView(R.id.article_published_at)
    TextView mArticlePublishedAt;

    public NewsHolder(View itemView, Context ctx, ItemListener<Article> listener) {
        super(itemView);
        mParent = itemView;
        ButterKnife.bind(this, itemView);
        mContext = ctx;
        mListener = listener;
    }

    public void bindArticle(Article article) {
        mArticleTitle.setText(article.getTitle());
        mArticleDescription.setText(article.getDescription());
        mArticleSource.setText(article.getSource().getName());
        mArticlePublishedAt.setText(article.getPublishedAt());
        Picasso.with(mContext)
                .load(article.getUrlToImage())
                .error(R.drawable.ic_tyrannosaurus)
                .fit()
                .into(mArticleImage);

        mParent.setOnClickListener(v -> mListener.open(article));
    }
}
