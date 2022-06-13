package com.ppk.news.data;

import com.ppk.news.data.model.NewsHeaderModel;

import java.util.ArrayList;

public interface NewsListener<NewsApiResponse> {
    /**
     * on data retrieving success
     * @param newsHeaders contain the newsHeaders
     * @param message to show the retrieve message
     */
    void onSuccess(ArrayList<NewsHeaderModel> newsHeaders, String message);

    /**
     * on data retrieving failure
     * @param message error message
     */
    void onFailure(String message);
}
