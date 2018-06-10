package com.zalesskyi.android.newsforclever.presenters;

import android.app.Application;
import android.util.Log;

import com.zalesskyi.android.newsforclever.R;
import com.zalesskyi.android.newsforclever.interactors.InteractorContract;
import com.zalesskyi.android.newsforclever.realm.RealmService;
import com.zalesskyi.android.newsforclever.utils.NetworkCheck;
import com.zalesskyi.android.newsforclever.view.BaseView;
import com.zalesskyi.android.newsforclever.view.listeners.MainListener;

import java.util.Locale;

public class MainPresenter
        extends BasePresenter<BaseView.MainView>{

    public MainPresenter(Application application, InteractorContract interactor,
                         NetworkCheck networkCheck, RealmService realmService) {
        mApplication = application;
        mInteractor = interactor;
        mNetworkCheck = networkCheck;
        mRealmService = realmService;
    }

    /**
     * Получение новостей.
     *
     * @param callback callback отображения новостей.
     *                 Вызывается при успешном получении списка новостей.
     */
    public void doGetTopNews(MainListener.MainCallback callback) {
        if (!mNetworkCheck.isOnline()) {
            mView.showError(mApplication.getString(R.string.err_no_connection));
            return;
        }
        Log.i("Presenter", "Locale: " + getCountry());
        mInteractor.toDoGetTopNews(getCountry(), mApplication.getString(R.string.api_key))
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


    /**
     * @return код страны для текщей локали.
     */
    private String getCountry() {
        return Locale.getDefault().getCountry();
    }
}
