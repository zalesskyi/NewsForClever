package com.zalesskyi.android.newsforclever.interactors;

import com.zalesskyi.android.newsforclever.model.NewsList;

import rx.Observable;

public interface InteractorContract {
    Observable<NewsList> toDoGetTopNews(String country, String apiKey);
}
