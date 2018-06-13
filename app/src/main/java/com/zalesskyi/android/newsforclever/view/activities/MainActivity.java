package com.zalesskyi.android.newsforclever.view.activities;

import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
    private SearchView mSearchView;
    private FragmentManager mFragmentManager;

    @Inject
    MainPresenter mPresenter;

    @BindView(R.id.main_container)
    View mFragmentContainer;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    private MainListener mListener = new MainListener() {
        @Override
        public void getTopNews(MainCallback callback) {
            if (mSearchView != null) {
                mSearchView.setQuery("", false);
            }

            mPresenter.doGetTopNews(callback);
        }

        @Override
        public void openDetailArticle(String url) {
            startActivity(DetailActivity.newIntent(MainActivity.this, url));
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        NewsApp.get(this).getAppComponent().inject(this);
        mPresenter.init(this);

        setupUI();

        mFragmentManager = getSupportFragmentManager();
        Fragment fragment = mFragmentManager.findFragmentById(R.id.main_container);

        if (fragment == null) {
            fragment = MainFragment.newInstance(mListener);
            mFragmentManager.beginTransaction()
                    .add(R.id.main_container, fragment)
                    .commit();
        } else {
            fragment = MainFragment.newInstance(mListener);
            mFragmentManager.beginTransaction()
                    .replace(R.id.main_container, fragment)
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        mSearchView = (SearchView) menu.findItem(R.id.menu_item_search).getActionView();
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Fragment fragment = mFragmentManager.findFragmentById(R.id.main_container);
                if (fragment == null) {
                    fragment = MainFragment.newInstance(mListener);
                    mFragmentManager.beginTransaction()
                            .add(R.id.main_container, fragment)
                            .commit();
                }
                mPresenter.doGetNewsBySearch((MainFragment) fragment, query);
                mSearchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.dismiss();
    }

    @Override
    public void showError(String err) {
        Toast.makeText(this, err, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean isProgressShown() {
        return mProgressSnack != null && mProgressSnack.isShown();
    }

    @Override
    public void showProgress() {
        mProgressSnack = Snackbar.make(mFragmentContainer, R.string.main_snack_loading, Snackbar.LENGTH_INDEFINITE);
        mProgressSnack.show();
    }

    @Override
    public void hideProgress() {
        if (mProgressSnack != null && mProgressSnack.isShown()) {
            mProgressSnack.dismiss();
        }
        mProgressSnack = null;
    }

    private void setupUI() {
        setSupportActionBar(mToolbar);
        mToolbar.setTitleTextColor(Color.DKGRAY);
    }
}