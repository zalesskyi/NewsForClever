package com.zalesskyi.android.newsforclever.view.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.zalesskyi.android.newsforclever.R;
import com.zalesskyi.android.newsforclever.app.NewsApp;
import com.zalesskyi.android.newsforclever.presenters.MainPresenter;
import com.zalesskyi.android.newsforclever.view.BaseView;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity
        implements BaseView.MainView {

    @Inject
    MainPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NewsApp.get(this).getAppComponent().inject(this);
        mPresenter.init(this);
        mPresenter.doGetTopNews();
    }

    @Override
    public void showError(String err) {

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }
}
