package com.jianping.lee.dailynews.base;

/**
 * Created by Li on 2017/2/8.
 */
public interface BasePresenter<T> {
    void onResume();

    void onDestroy();

    void onStart();

    void onPause();

    void onStop();

    void attachView(T view);

    void detachView();
}
