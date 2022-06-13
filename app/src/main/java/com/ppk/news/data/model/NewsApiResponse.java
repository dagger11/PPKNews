package com.ppk.news.data.model;

import java.util.ArrayList;

public class NewsApiResponse {
    String status;
    int totalResults;
    ArrayList<NewsHeaderModel> articles;

    public NewsApiResponse(){}

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public ArrayList<NewsHeaderModel> getArticles() {
        return articles;
    }

    public void setArticles(ArrayList<NewsHeaderModel> articles) {
        this.articles = articles;
    }
}
