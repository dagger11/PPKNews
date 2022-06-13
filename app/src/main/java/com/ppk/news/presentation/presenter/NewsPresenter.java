package com.ppk.news.presentation.presenter;

import android.util.Log;

import com.ppk.news.data.NewsListener;
import com.ppk.news.data.manager.RequestManager;
import com.ppk.news.data.model.NewsHeaderModel;
import com.ppk.news.data.model.NewsApiResponse;
import com.ppk.news.presentation.contracts.NewsActivityContract;
import com.ppk.news.util.Default_Config;

import java.util.ArrayList;

public class NewsPresenter implements NewsActivityContract.Presenter {
    private NewsActivityContract.View view;

    public NewsPresenter(){}

    @Override
    public void setView(NewsActivityContract.View view) {
        this.view = view;
    }

    @Override
    public void dropView() {
        view = null;
    }

    @Override
    public void loadNews(String category) {
        NewsListener<NewsApiResponse> listener = new NewsListener<NewsApiResponse>() {
            @Override
            public void onSuccess(ArrayList<NewsHeaderModel> newsHeaders, String message) {
                Log.e("on success","");
                view.onNewsLoaded(newsHeaders);
            }

            @Override
            public void onFailure(String message) {
                Log.e("error msg ", message);
                //tell the view to show error message
            }
        };

        RequestManager manager = new RequestManager();
        manager.getNewsHeaders(null,category,listener);
    }
}
