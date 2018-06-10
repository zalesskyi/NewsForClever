package com.zalesskyi.android.newsforclever.view.listeners;

import com.zalesskyi.android.newsforclever.model.Article;

import java.util.List;

public interface MainListener {

    /**
     * Получение новостей.
     *
     * @param callback callback отображения новостей.
     *                 Вызывается при успешном получении списка новостей.
     */
    void getTopNews(MainCallback callback);

    /**
     * Открытие детального представления статьи.
     * Открывается DetailActivity.
     *
     * @param url URL страницы статьи.
     */
    void openDetailArticle(String url);

    interface MainCallback {

        /**
         * Callback отображения списка новостей.
         * Вызывается при успешной загрузке списка новостей.
         *
         * @param articles список новостей.
         */
        void showNews(List<Article> articles);

        /**
         * Callback отображения пустого списка новостей.
         * Вызывается при ошибке загрузки списка новостей
         * и пустой БД, так что нельзя отобразить старые новости.
         */
        void showEmptyList();
    }
}
