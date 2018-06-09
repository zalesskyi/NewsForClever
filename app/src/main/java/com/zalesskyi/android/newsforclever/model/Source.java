package com.zalesskyi.android.newsforclever.model;

import com.google.gson.annotations.SerializedName;

public class Source {

    @SerializedName("name")
    String mName;

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }
}
