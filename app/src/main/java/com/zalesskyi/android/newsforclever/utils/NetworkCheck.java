package com.zalesskyi.android.newsforclever.utils;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkCheck {
    private Application mApplication;

    public NetworkCheck(Application application) {
        mApplication = application;
    }

    /**
     * Проверка наличия сети.
     *
     * @return есть ли интернет-соединение.
     */
    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager)
                mApplication.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
