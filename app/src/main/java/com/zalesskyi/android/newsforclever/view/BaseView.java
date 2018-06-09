package com.zalesskyi.android.newsforclever.view;

public interface BaseView {
    void showError(String err);

    void showProgress();
    void hideProgress();

    interface MainView extends BaseView {

    }
}
