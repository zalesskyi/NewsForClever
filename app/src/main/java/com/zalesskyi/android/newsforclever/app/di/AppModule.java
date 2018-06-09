package com.zalesskyi.android.newsforclever.app.di;

import android.app.Application;

import com.zalesskyi.android.newsforclever.realm.RealmService;
import com.zalesskyi.android.newsforclever.realm.RealmServiceImpl;
import com.zalesskyi.android.newsforclever.utils.NetworkCheck;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;

@Module
public class AppModule {

    private Application mApplication;

    public AppModule(Application app) {
        mApplication = app;
    }

    @Provides
    public Application provideApplication() {
        return mApplication;
    }

    @Provides
    public NetworkCheck provideNetworkCheck() {
        return new NetworkCheck(mApplication);
    }

    @Provides
    Realm provideRealm() {
        return Realm.getDefaultInstance();
    }

    @Provides
    RealmService provideRealmService(Realm realm) {
        return new RealmServiceImpl(realm);
    }
}
