package com.zalesskyi.android.newsforclever.presenters;

import android.app.Application;
import android.util.Log;

import com.zalesskyi.android.newsforclever.R;
import com.zalesskyi.android.newsforclever.interactors.InteractorContract;
import com.zalesskyi.android.newsforclever.model.Article;
import com.zalesskyi.android.newsforclever.model.NewsList;
import com.zalesskyi.android.newsforclever.realm.RealmService;
import com.zalesskyi.android.newsforclever.utils.NetworkCheck;
import com.zalesskyi.android.newsforclever.view.BaseView;
import com.zalesskyi.android.newsforclever.view.listeners.MainListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainPresenter
        extends BasePresenter<BaseView.MainView>{
    private static final String TAG = "MainPresenter";

    public MainPresenter(Application application, InteractorContract interactor,
                         NetworkCheck networkCheck, RealmService realmService) {
        mApplication = application;
        mInteractor = interactor;
        mNetworkCheck = networkCheck;
        mRealmService = realmService;
    }

    /**
     * Получение новостей.
     * Если нет подключения, то отображается тост.
     * Во время обращения на сервер за списком свежих новостей проверяется локальная БД.
     * Если в БД содержится список новостей, который был успешно загружен с сервера в прошлый раз,
     * то он отображается.
     * При получении списка свежих новостей с сервера, список старых новостей заменяется на новый.
     *
     * @param callback callback отображения новостей.
     *                 Вызывается при успешном получении списка новостей.
     *                 Если новостей нет, отображает пустой список.
     */
    public void doGetTopNews(MainListener.MainCallback callback) {
        if (!mNetworkCheck.isOnline()) {
            mView.showError(mApplication.getString(R.string.err_no_connection));
        }
        Log.i(TAG, "Locale: " + getCountry());
        mInteractor.toDoGetTopNews(getCountry(), mApplication.getString(R.string.api_key))
                .doOnRequest(l -> {
                    mRealmService.getObjects(NewsList.class)                          // Во время запроса на сервер,
                            .subscribe(oldNews -> {                                   // проверяем локальную БД
                                if (oldNews.size() > 0) {
                                    callback.showNews(oldNews.get(0).getArticles());
                                } else {
                                    callback.showEmptyList();
                                }
                                mView.showProgress();
                            }, err -> mView.showProgress());
                })
                .map(news -> {
                    List<Article> articles = news.getArticles();                       // преобразуем ответ от сервера
                    for (Article article : articles) {
                        article.setPublishedAt(
                                getDifferenceBetweenTime(article.getPublishedAt()));
                    }
                    return news;
                }).subscribe(news -> {
                    if (!news.getStatus().equals("ok")) {
                        mView.showError(mApplication.getString(R.string.common_err_msg));
                        callback.showEmptyList();
                        return;
                    }
                    news.setRealmId(NEWS_LIST_REALM_ID);
                    mRealmService.addObject(news, NewsList.class).subscribe(newsList -> {
                        Log.i(TAG, "List of news was added into Realm : " + newsList);
                    });
                    callback.showNews(news.getArticles());
                }, err -> {
                    Log.e(TAG, err.getMessage());
                    mView.showError(err.getMessage());
                    mView.hideProgress();
                }, () ->  {
                    if (mView.isProgressShown()) {
                        mView.hideProgress();
                    }
                });
    }

    /**
     * Метод возвращает время создания
     * статьи, относительно текущего времени.
     *
     * @param articlePostedAt время создания статьи в формате "yyyy-MM-dd'T'HH:mm:ss'Z'"
     * @return относительное время создания
     *         например: Например: 1h ago.
     */
    private String getDifferenceBetweenTime(String articlePostedAt) {
        try {
            DateFormat format = new SimpleDateFormat(
                    "yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault());

            Date articleDate = format.parse(articlePostedAt);
            Date curDate = new Date();

            long timeDif = curDate.getTime() - articleDate.getTime();

            long diffHours = timeDif / (60 * 60 * 1000) % 24;
            long diffMinutes = timeDif / (60 * 1000) % 60;
            long diffSeconds = timeDif / 1000 % 60;

            if (diffHours > 0) {
                return mApplication.getString(
                        R.string.article_created_hours_ago, diffHours);
            } else if (diffMinutes > 0) {
                return mApplication.getString(
                        R.string.article_created_minutes_ago, diffMinutes);
            } else if (diffSeconds > 0) {
                return mApplication.getString(
                        R.string.article_created_seconds_ago, diffSeconds);
            }
            return "";
        } catch (ParseException exc) {
            return "";
        }
    }

    /**
     * @return код страны для текщей локали.
     */
    private String getCountry() {
        return Locale.getDefault().getCountry();
    }
}
