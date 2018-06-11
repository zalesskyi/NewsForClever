package com.zalesskyi.android.newsforclever.model;

import io.realm.RealmObject;

public class Source extends RealmObject {

    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}