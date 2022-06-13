package com.ppk.news.presentation.contracts;

import com.ppk.news.data.model.NewsHeaderModel;

import java.util.ArrayList;

public interface NewsActivityContract {

    interface View extends BaseView{
        void onNewsLoaded(ArrayList<NewsHeaderModel> newsHeaderList);
    }

    interface Presenter extends BasePresenter<View>{
        void loadNews(String category);
        void loadNewsWithQuery(String category,String query);
    }
}
