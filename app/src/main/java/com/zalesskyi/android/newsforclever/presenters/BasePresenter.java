package com.zalesskyi.android.newsforclever.presenters;

import android.app.Application;

import com.zalesskyi.android.newsforclever.interactors.InteractorContract;
import com.zalesskyi.android.newsforclever.realm.RealmService;
import com.zalesskyi.android.newsforclever.utils.NetworkCheck;
import com.zalesskyi.android.newsforclever.view.BaseView;

abstract class BasePresenter<V extends BaseView> {

    protected static final long NEWS_LIST_REALM_ID = 1;

    protected V mView;
    protected NetworkCheck mNetworkCheck;
    protected Application mApplication;
    protected InteractorContract mInteractor;
    protected RealmService mRealmService;

    public void init(V view) {
        mView = view;
    }

    public void dismiss() {
        mView = null;
    }
}
