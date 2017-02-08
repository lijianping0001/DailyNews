package com.jianping.lee.dailynews.base;

import android.content.Context;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * @fileName: RxPresenter
 * @Author: Li Jianping
 * @Date: 2016/12/21 18:01
 * @Description: 基于Rx的Presenter封装,控制订阅的生命周期
 */
public class RxPresenter<T> implements BasePresenter<T> {

    protected Context mContext;

    protected T mAttachView;

    protected CompositeSubscription mCompositeSubscription;

    protected void unSubscribe(){
        if (mCompositeSubscription != null){
            mCompositeSubscription.unsubscribe();
        }
    }

    protected void addSubscribe(Subscription subscription){
        if (mCompositeSubscription == null){
            mCompositeSubscription = new CompositeSubscription();
        }
        mCompositeSubscription.add(subscription);
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onDestroy() {
        unSubscribe();
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void attachView(T view) {
        this.mAttachView = view;
    }

    @Override
    public void detachView() {
        this.mAttachView = null;
    }
}
