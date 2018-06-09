package com.zalesskyi.android.newsforclever.view.listeners;

import com.zalesskyi.android.newsforclever.model.Article;

import java.util.List;

public interface MainListener {
    void getTopNews(MainCallback callback);

    interface MainCallback {
        void showNews(List<Article> articles);
        void showEmptyList();
    }
}
