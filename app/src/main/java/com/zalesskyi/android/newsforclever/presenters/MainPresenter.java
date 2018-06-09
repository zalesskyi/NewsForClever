package com.zalesskyi.android.newsforclever.presenters;

import android.app.Application;

import com.zalesskyi.android.newsforclever.R;
import com.zalesskyi.android.newsforclever.interactors.InteractorContract;
import com.zalesskyi.android.newsforclever.realm.RealmService;
import com.zalesskyi.android.newsforclever.utils.NetworkCheck;
import com.zalesskyi.android.newsforclever.view.BaseView;
import com.zalesskyi.android.newsforclever.view.listeners.MainListener;

public class MainPresenter
        extends BasePresenter<BaseView.MainView>{

    public MainPresenter(Application application, InteractorContract interactor,
                         NetworkCheck networkCheck, RealmService realmService) {
        mApplication = application;
        mInteractor = interactor;
        mNetworkCheck = networkCheck;
        mRealmService = realmService;
    }

    public void doGetTopNews(MainListener.MainCallback callback) {
        mInteractor.toDoGetTopNews("us", mApplication.getString(R.string.api_key))
                .doOnRequest(l -> mView.showProgress())
                .subscribe(news -> {
                    if (!news.getStatus().equals("ok")) {
                        mView.showError(mApplication.getString(R.string.common_err_msg));
                        callback.showEmptyList();
                        return;
                    }
                    callback.showNews(news.getArticles());
                }, err -> {
                    mView.showError(err.getMessage());
                    mView.hideProgress();
                }, () ->  {
                    mView.hideProgress();
                });
    }
}
