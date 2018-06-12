package com.zalesskyi.android.newsforclever.interactors;

import com.zalesskyi.android.newsforclever.model.NewsList;

import rx.Observable;

public interface InteractorContract {

    /**
     * Отправка на сервер GET-запроса
     * на получение списка свежих новостей.
     *
     * @param country страна - источник новостей
     * @param apiKey ключ API
     * @return Observable, проталкивающий список новостей
     */
    Observable<NewsList> toDoGetTopNews(String country, String apiKey);

    /**
     * Отправка на сервер GET-запроса
     * на получение списка новостей по ключевой строке.
     *
     * @param searchQuery ключевая строка
     * @param apiKey ключ API
     * @return Observable, проталкивающий список новостей
     */
    Observable<NewsList> toDoGetNewsBySearch(String searchQuery, String apiKey);
}
