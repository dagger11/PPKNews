package com.ppk.news.data.manager;

import com.ppk.news.data.model.NewsApiResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CallNewsApi {

    @GET("top-headlines")
    Call<NewsApiResponse> callNewsHeaders(
            @Query("country") String country,
            @Query("category") String category,
            @Query("q") String query,
            @Query("apiKey") String api_key

    );
}
