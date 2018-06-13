package com.zalesskyi.android.newsforclever.app.di;

import android.app.Application;
import android.util.Config;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zalesskyi.android.newsforclever.NewsApi;
import com.zalesskyi.android.newsforclever.interactors.InteractorContract;
import com.zalesskyi.android.newsforclever.interactors.InteractorImpl;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ApiModule {

    @Provides
    OkHttpClient provideOkHttpClient() {
        final OkHttpClient.Builder builder = new OkHttpClient.Builder();

        if (Config.DEBUG) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(logging);
        }

        try {
            final TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
                @Override
                public void checkClientTrusted(
                        java.security.cert.X509Certificate[] chain,
                        String authType) throws CertificateException {
                }

                @Override
                public void checkServerTrusted(
                        java.security.cert.X509Certificate[] chain,
                        String authType) throws CertificateException {
                }

                @Override
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return new java.security.cert.X509Certificate[0];
                }
            } };

            final SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustAllCerts,
                    new java.security.SecureRandom());
            final SSLSocketFactory sslSocketFactory = sslContext
                    .getSocketFactory();

            builder.connectTimeout(60 * 1000, TimeUnit.MILLISECONDS)
                    .sslSocketFactory(sslSocketFactory)
                    .readTimeout(60 * 1000, TimeUnit.MILLISECONDS);

            return builder.build();
        } catch (KeyManagementException | NoSuchAlgorithmException exc) {
            throw new RuntimeException(exc);
        }
    }


    @Provides
    Gson provideGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setFieldNamingPolicy(
                FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);

        return gsonBuilder.setLenient().create();
    }

    @Provides
    Retrofit provideRestAdapter(Gson gson, OkHttpClient okHttpClient) {
        return new Retrofit.Builder().client(okHttpClient)
                .baseUrl("https://newsapi.org/v2/")
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    @Provides
    public NewsApi providesApiService(Retrofit restAdapter) {
        return restAdapter.create(NewsApi.class);
    }

    @Provides
    public InteractorContract provideInteractor(NewsApi apiService) {
        return new InteractorImpl(apiService);
    }
}
