package com.jianping.lee.dailynews.base;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jianping.lee.dailynews.utils.ToastUtils;

import butterknife.ButterKnife;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * @fileName: BaseFragment
 * @Author: Li Jianping
 * @Date: 2016/12/22 15:48
 * @Description:
 */
public abstract class BaseFragment<T extends BasePresenter> extends SupportFragment{
    protected T mPresenter;

    protected Context mContext;

    protected View mRootView;

    protected abstract int getLayout();

    protected abstract void initPresenter();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mRootView == null){
            mRootView = inflater.inflate(getLayout(), container, false);
        }
        ButterKnife.inject(this, mRootView);
        initPresenter();
        return mRootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mPresenter != null){
            mPresenter.attachView(this);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (mContext != null){
            this.mContext = context;
        }else {
            this.mContext = getActivity();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (mContext != null){
            mContext = null;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mPresenter != null){
            mPresenter.onStart();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mPresenter != null){
            mPresenter.onPause();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null){
            mPresenter.detachView();
            mPresenter.onDestroy();
        }
        ButterKnife.reset(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mPresenter != null){
            mPresenter.onDestroy();
        }
        mPresenter = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mPresenter != null){
            mPresenter.onResume();
        }
    }

    /**
     * 显示吐司
     * @param msg 文本
     */
    protected void showToast(String msg){
        ToastUtils.showToast(mContext, msg);
    }

    /**
     * 显示吐司
     * @param drawableId 图片id
     * @param msg 文本
     */
    protected void showToast(int drawableId, String msg){
        ToastUtils.showToast(mContext, drawableId, msg);
    }
}
