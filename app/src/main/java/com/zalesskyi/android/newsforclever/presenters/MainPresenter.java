package com.zalesskyi.android.newsforclever.presenters;

import android.app.Application;

import com.zalesskyi.android.newsforclever.R;
import com.zalesskyi.android.newsforclever.interactors.InteractorContract;
import com.zalesskyi.android.newsforclever.realm.RealmService;
import com.zalesskyi.android.newsforclever.utils.NetworkCheck;
import com.zalesskyi.android.newsforclever.view.BaseView;

public class MainPresenter
        extends BasePresenter<BaseView.MainView>{

    public MainPresenter(Application application, InteractorContract interactor,
                         NetworkCheck networkCheck, RealmService realmService) {
        mApplication = application;
        mInteractor = interactor;
        mNetworkCheck = networkCheck;
        mRealmService = realmService;
    }

    public void doGetTopNews() {
        mInteractor.toDoGetTopNews("us", mApplication.getString(R.string.api_key));
    }
}
