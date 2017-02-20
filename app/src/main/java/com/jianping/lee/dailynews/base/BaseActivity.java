package com.jianping.lee.dailynews.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.jianping.lee.dailynews.utils.LogUtils;
import com.jianping.lee.dailynews.utils.ToastUtils;

import butterknife.ButterKnife;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * Created by Li on 2017/2/8.
 */
public abstract class BaseActivity<T extends BasePresenter> extends SupportActivity implements BaseView{

    protected T mPresenter;

    protected Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        mContext = this;
        if (mPresenter != null){
            mPresenter.attachView(this);
        }
        LogUtils.d(this.getClass() + this.getClass().getName() + "==== onCreate");
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.inject(this);
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

    protected void setToolBar(Toolbar toolbar, String title) {
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressedSupport();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DailyNewsApplication.getInstance().unRegisterActivity(this);
        if (mPresenter != null){
            mPresenter.detachView();
            mPresenter.onDestroy();
        }
        mPresenter = null;
        LogUtils.d(this.getClass() + this.getClass().getName() + "=== onDestroy");
    }

    protected void replaceFragment(int contentId, Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(contentId, fragment);
        transaction.commit();
    }

    /**
     * 显示吐司
     * @param msg 文本
     */
    protected void showToast(String msg){
        ToastUtils.showToast(this, msg);
    }

    /**
     * 显示吐司
     * @param drawableId 图片id
     * @param msg 文本
     */
    protected void showToast(int drawableId, String msg){
        ToastUtils.showToast(this, drawableId, msg);
    }
}
