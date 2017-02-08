package com.jianping.lee.dailynews.base;

/**
 * Created by Li on 2017/2/8.
 */
public interface BaseView<T> {
    void setPresenter(T presenter);

    void showMessage(String msg);
}
