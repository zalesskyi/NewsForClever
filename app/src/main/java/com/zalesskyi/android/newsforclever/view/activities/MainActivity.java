package com.zalesskyi.android.newsforclever.view.activities;

import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.zalesskyi.android.newsforclever.R;
import com.zalesskyi.android.newsforclever.app.NewsApp;
import com.zalesskyi.android.newsforclever.presenters.MainPresenter;
import com.zalesskyi.android.newsforclever.view.BaseView;
import com.zalesskyi.android.newsforclever.view.fragments.MainFragment;
import com.zalesskyi.android.newsforclever.view.listeners.MainListener;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
        implements BaseView.MainView {

    private Snackbar mProgressSnack;

    @Inject
    MainPresenter mPresenter;

    @BindView(R.id.main_container)
    View mFragmentContainer;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    private MainListener mListener = callback -> {
        mPresenter.doGetTopNews(callback);
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        NewsApp.get(this).getAppComponent().inject(this);
        mPresenter.init(this);

        setSupportActionBar(mToolbar);
        mToolbar.setTitleTextColor(Color.DKGRAY);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.main_container);

        if (fragment == null) {
            fragment = MainFragment.newInstance(mListener, null);
            fragmentManager.beginTransaction()
                    .add(R.id.main_container, fragment)
                    .commit();
        }
    }

    @Override
    public void showError(String err) {
        Toast.makeText(this, err, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgress() {
        mProgressSnack = Snackbar.make(mFragmentContainer, R.string.main_snack_loading, Snackbar.LENGTH_INDEFINITE);
        mProgressSnack.show();
    }

    @Override
    public void hideProgress() {
        if (mProgressSnack.isShown()) {
            mProgressSnack.dismiss();
        }
        mProgressSnack = null;
    }
}
