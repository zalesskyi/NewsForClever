package com.zalesskyi.android.newsforclever.view.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.zalesskyi.android.newsforclever.R;
import com.zalesskyi.android.newsforclever.view.BaseView;

import java.net.MalformedURLException;
import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity implements BaseView.DetailView {

    private static final String URL_EXTRA = "url_to_article";

    private String mUrlToArticle;

    @BindView(R.id.detail_webview)
    WebView mWebView;

    @BindView(R.id.detail_progress_bar)
    ProgressBar mProgressBar;

    @BindView(R.id.detail_toolbar)
    Toolbar mToolbar;

    public static Intent newIntent(Context ctx, String urlToArticle) {
        Intent i = new Intent(ctx, DetailActivity.class);
        i.putExtra(URL_EXTRA, urlToArticle);
        return i;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mUrlToArticle = getIntent().getStringExtra(URL_EXTRA);

        ButterKnife.bind(this);
        setupUI();
    }

    @Override
    public void showError(String err) {
        Toast.makeText(this, err, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean isProgressShown() {
        return mProgressBar.isShown();
    }

    @Override
    public void hideProgress() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showProgress(int progress) {
        mProgressBar.setVisibility(View.VISIBLE);
        mProgressBar.setProgress(progress);
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void setupUI() {
        setSupportActionBar(mToolbar);
        mToolbar.setTitleTextColor(Color.DKGRAY);
        mToolbar.setSubtitleTextColor(Color.GRAY);

        String toolbarSubtitle;
        try {
            toolbarSubtitle = new URL(mUrlToArticle).getHost();
        } catch (MalformedURLException exc) {
            toolbarSubtitle = mUrlToArticle;
        }
        mToolbar.setSubtitle(toolbarSubtitle);

        mProgressBar.setMax(100);

        mWebView.getSettings().setJavaScriptEnabled(true);

        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView webView, int newProgress) {
                if (newProgress == 100) {
                    hideProgress();
                    return;
                }
                showProgress(newProgress);
            }

            @Override
            public void onReceivedTitle(WebView webView, String title) {
                mToolbar.setTitle(title);
            }
        });

        mWebView.setWebViewClient(new WebViewClient());
        mWebView.loadUrl(mUrlToArticle);
    }
}
