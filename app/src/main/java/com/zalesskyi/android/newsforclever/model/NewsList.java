package com.zalesskyi.android.newsforclever.model;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

public class NewsList extends RealmObject {

    @PrimaryKey
    long realmId;

    @Ignore
    private String status;
    @Ignore
    private int resultsCount;
    private RealmList<Article> articles;

    public long getRealmId() {
        return realmId;
    }

    public void setRealmId(long realmId) {
        this.realmId = realmId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getResultsCount() {
        return resultsCount;
    }

    public void setResultsCount(int resultsCount) {
        this.resultsCount = resultsCount;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = new RealmList<Article>(
                articles.toArray(new Article[articles.size()]));
    }

    @Override
    public String toString() {
        return "status: " + status + "\n" +
                "totalResults: " + resultsCount + "\n" +
                "articles : " + articles;
    }
}