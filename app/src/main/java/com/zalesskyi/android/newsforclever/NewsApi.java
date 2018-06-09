package com.zalesskyi.android.newsforclever;

import com.zalesskyi.android.newsforclever.model.NewsList;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface NewsApi {

    @GET("top-headlines")
    Observable<NewsList> getTopNews(@Query("country") String country, @Query("apiKey") String apiKey);

    @GET("everything")
    Observable<NewsList> getNewsBySearch(@Query("q") String query, @Query("apiKey") String apiKey);
}
