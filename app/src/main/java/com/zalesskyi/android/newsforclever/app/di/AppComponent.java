package com.zalesskyi.android.newsforclever.app.di;

import com.zalesskyi.android.newsforclever.view.activities.MainActivity;

import dagger.Component;

@Component(modules = {ApiModule.class, AppModule.class, PresenterModule.class})
public interface AppComponent {

    void inject(MainActivity activity);
}
