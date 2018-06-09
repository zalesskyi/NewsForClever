package com.zalesskyi.android.newsforclever.realm;

import io.realm.Realm;

public class RealmServiceImpl implements RealmService {
    private Realm mRealm;

    public RealmServiceImpl(Realm realm) {
        mRealm = realm;
    }
}
