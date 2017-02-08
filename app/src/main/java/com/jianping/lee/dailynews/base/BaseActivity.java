package com.jianping.lee.dailynews.base;

import android.app.Activity;
import android.os.Bundle;

import com.jianping.lee.dailynews.utils.LogUtils;

/**
 * Created by Li on 2017/2/8.
 */
public abstract class BaseActivity<T extends BasePresenter> extends Activity{

    protected T mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        LogUtils.d(this.getClass() + this.getClass().getName() + "==== onCreate");
    }

    protected void init(){
        DailyNewsApplication.getInstance().registerActivity(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mPresenter != null){
            mPresenter.onStart();
        }
        LogUtils.d(this.getClass() + this.getClass().getName() + "=== onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mPresenter != null){
            mPresenter.onResume();
        }
        LogUtils.d(this.getClass() + this.getClass().getName() + "=== onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mPresenter != null){
            mPresenter.onPause();
        }
        LogUtils.d(this.getClass() + this.getClass().getName() + "=== onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mPresenter != null){
            mPresenter.onStop();
        }
        LogUtils.d(this.getClass() + this.getClass().getName() + "=== onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DailyNewsApplication.getInstance().unRegisterActivity(this);
        if (mPresenter != null){
            mPresenter.onDestroy();
        }
        mPresenter = null;
        LogUtils.d(this.getClass() + this.getClass().getName() + "=== onDestroy");
    }
}
