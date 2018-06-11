package com.zalesskyi.android.newsforclever.view;

public interface BaseView {
    void showError(String err);

    boolean isProgressShown();

    void hideProgress();

    interface MainView extends BaseView {
        void showProgress();
    }

    interface DetailView extends BaseView {
        void showProgress(int progress);
    }

}
