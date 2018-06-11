package com.zalesskyi.android.newsforclever.realm;

import io.realm.RealmObject;
import io.realm.RealmResults;
import rx.Observable;

public interface RealmService {
    <T extends RealmObject> Observable<T> addObject(T object, Class<T> clazz);
    <T extends RealmObject> Observable<RealmResults<T>> getObjects(Class<T> clazz);
    <T extends RealmObject> Observable<Class<T>> deleteAllObjects(Class<T> clazz);
}
