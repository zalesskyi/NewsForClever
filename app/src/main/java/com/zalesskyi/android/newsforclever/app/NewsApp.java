package com.zalesskyi.android.newsforclever.app;

import android.app.Application;
import android.content.Context;

import com.zalesskyi.android.newsforclever.app.di.ApiModule;
import com.zalesskyi.android.newsforclever.app.di.AppComponent;
import com.zalesskyi.android.newsforclever.app.di.AppModule;
import com.zalesskyi.android.newsforclever.app.di.DaggerAppComponent;
import com.zalesskyi.android.newsforclever.app.di.PresenterModule;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class NewsApp extends Application {

    private AppComponent mAppComponent;

    public static NewsApp get(Context ctx) {
        return (NewsApp) ctx.getApplicationContext();
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        initAppComponent();
        initRealmConfiguration();
    }

    private void initAppComponent() {
        mAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .apiModule(new ApiModule())
                .presenterModule(new PresenterModule())
                .build();
    }

    private void initRealmConfiguration() {
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("newsforclever.realm")
                .schemaVersion(1)
                .build();
        Realm.setDefaultConfiguration(config);
    }
}
