package com.zalesskyi.android.newsforclever.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NewsList {

    @SerializedName("status")
    private String mStatus;

    @SerializedName("totalResults")
    private int mResultsCount;

    @SerializedName("articles")
    private List<Article> mArticles;

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

    public int getResultsCount() {
        return mResultsCount;
    }

    public void setResultsCount(int resultsCount) {
        mResultsCount = resultsCount;
    }

    public List<Article> getArticles() {
        return mArticles;
    }

    public void setArticles(List<Article> articles) {
        mArticles = articles;
    }

    @Override
    public String toString() {
        return "status: " + mStatus + "\n" +
                "totalResults: " + mResultsCount + "\n" +
                "articles : " + mArticles;
    }
}
