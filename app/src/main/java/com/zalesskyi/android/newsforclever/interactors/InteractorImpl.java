package com.zalesskyi.android.newsforclever.interactors;

import com.zalesskyi.android.newsforclever.NewsApi;
import com.zalesskyi.android.newsforclever.model.NewsList;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class InteractorImpl implements InteractorContract {

    private NewsApi mApi;

    public InteractorImpl(NewsApi api) {
        mApi = api;
    }

    @Override
    public Observable<NewsList> toDoGetTopNews(String country, String apiKey) {
        return mApi.getTopNews(country, apiKey)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
