package com.ppk.news.data.manager;

import android.util.Log;

import androidx.annotation.NonNull;

import com.ppk.news.data.NewsListener;
import com.ppk.news.data.model.NewsApiResponse;
import com.ppk.news.util.Default_Config;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RequestManager {

    public RequestManager(){}

    private final Retrofit retrofit = new Retrofit.Builder().baseUrl(Default_Config.URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();


    public void getNewsHeaders(String query,
                               String category,
                               @NonNull NewsListener listener){

        //get the headers via CallNewsApi using retrofit
        CallNewsApi callNewsApi = retrofit.create(CallNewsApi.class);
        Call<NewsApiResponse> call =  callNewsApi.callNewsHeaders("us",category,
                query,Default_Config.API_KEY);

        try {
            call.enqueue(new Callback<NewsApiResponse>() {
                @Override
                public void onResponse(Call<NewsApiResponse> call, Response<NewsApiResponse> response) {
                    if (response.isSuccessful()&& response.body()!= null){
                        listener.onSuccess(response.body().getArticles(),response.message());
                        Log.e("Success",""+response.message());
                    }
                    else
                       listener.onFailure("Error Loading!");
                }

                @Override
                public void onFailure(@NonNull Call<NewsApiResponse> call, @NonNull Throwable t) {
                    listener.onFailure(t.getMessage());
                }
            });
        }catch (Exception exception){
            listener.onFailure(exception.getMessage());
        }
    }
}
