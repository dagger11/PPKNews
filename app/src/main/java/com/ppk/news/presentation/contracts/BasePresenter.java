package com.ppk.news.presentation.contracts;

public interface BasePresenter<T extends BaseView> {
    void setView(T view);
    void dropView();
}
