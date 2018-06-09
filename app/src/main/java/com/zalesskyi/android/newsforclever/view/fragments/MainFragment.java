package com.zalesskyi.android.newsforclever.view.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zalesskyi.android.newsforclever.R;
import com.zalesskyi.android.newsforclever.model.Article;
import com.zalesskyi.android.newsforclever.view.adapters.NewsAdapter;
import com.zalesskyi.android.newsforclever.view.listeners.ItemListener;
import com.zalesskyi.android.newsforclever.view.listeners.MainListener;

import java.util.List;

import javax.annotation.Nullable;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainFragment extends Fragment implements MainListener.MainCallback {

    private static final String SEARCH_QUERY_ARG = "search_query";

    private ItemListener<Article> mItemListener = item -> {

    };

    @Nullable
    private String mSearchQuery;
    private MainListener mListener;
    private NewsAdapter mAdapter;

    @BindView(R.id.news_recycler_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.news_empty_list)
    View mEmptyList;

    public static MainFragment newInstance(MainListener listener, @Nullable String searchQuery) {
        MainFragment mainFragment = new MainFragment();
        mainFragment.mListener = listener;

        Bundle args = new Bundle();
        args.putString(SEARCH_QUERY_ARG, searchQuery);
        mainFragment.setArguments(args);
        return mainFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, parent, false);

        ButterKnife.bind(this, v);

        mSearchQuery = getArguments().getString(SEARCH_QUERY_ARG);

        setupUI();
        if (mListener != null) {
            mListener.getTopNews(this);
        }
        return v;
    }

    private void setupUI() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mAdapter = new NewsAdapter(getActivity(), mItemListener);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void showNews(List<Article> articles) {
        mRecyclerView.setVisibility(View.VISIBLE);
        mEmptyList.setVisibility(View.GONE);

        for (Article article : articles) {
            mAdapter.addItem(article);
        }
    }

    @Override
    public void showEmptyList() {
        mRecyclerView.setVisibility(View.GONE);
        mEmptyList.setVisibility(View.VISIBLE);
    }
}
