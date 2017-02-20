package com.jianping.lee.dailynews.presenter;

import android.content.Context;

import com.jianping.lee.dailynews.base.RxPresenter;
import com.jianping.lee.dailynews.presenter.contract.TypeNewsDetailContract;

/**
 * Created by Li on 2017/2/19.
 */
public class TypeNewsDetailPresenter extends RxPresenter<TypeNewsDetailContract.View> implements TypeNewsDetailContract.Presenter{

    private Context mContext;

    public TypeNewsDetailPresenter(Context context){
        mContext = context;
    }
}
