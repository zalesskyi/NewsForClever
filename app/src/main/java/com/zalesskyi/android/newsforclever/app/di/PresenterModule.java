package com.zalesskyi.android.newsforclever.app.di;

import android.app.Application;

import com.zalesskyi.android.newsforclever.interactors.InteractorContract;
import com.zalesskyi.android.newsforclever.interactors.InteractorImpl;
import com.zalesskyi.android.newsforclever.presenters.MainPresenter;
import com.zalesskyi.android.newsforclever.realm.RealmService;
import com.zalesskyi.android.newsforclever.utils.NetworkCheck;

import dagger.Module;
import dagger.Provides;

@Module
public class PresenterModule {

    @Provides
    MainPresenter providesMainPresenter(Application application, InteractorContract interactor,
                                        NetworkCheck networkCheck, RealmService realmService) {
        return new MainPresenter(application, interactor, networkCheck, realmService);
    }
}
