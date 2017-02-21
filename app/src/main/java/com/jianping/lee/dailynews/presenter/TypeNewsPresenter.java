package com.jianping.lee.dailynews.presenter;

import android.content.Context;
import android.support.annotation.NonNull;

import com.jianping.lee.dailynews.R;
import com.jianping.lee.dailynews.base.ConstConfig;
import com.jianping.lee.dailynews.base.RxPresenter;
import com.jianping.lee.dailynews.engine.RxBus;
import com.jianping.lee.dailynews.model.UserEvent;
import com.jianping.lee.dailynews.model.http.Api;
import com.jianping.lee.dailynews.model.http.HttpService;
import com.jianping.lee.dailynews.presenter.contract.TypeNewsContract;
import com.jianping.lee.dailynews.utils.LogUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by Li on 2017/2/14.
 */
public class TypeNewsPresenter extends RxPresenter<TypeNewsContract.View> implements TypeNewsContract.Presenter {

    public TypeNewsPresenter(@NonNull Context context){
        this.mContext = context;
        init();
    }

    private void init() {
        Subscription subscription = RxBus.getDefault().toObservable(UserEvent.class)
                .subscribe(new Action1<UserEvent>() {
                    @Override
                    public void call(UserEvent userEvent) {
                        if (ConstConfig.NEWS_TO_TOP.equals(userEvent.getId())){
                            mAttachView.listGoTop();
                        }
                    }
                });
    }

    @Override
    public void getData(String type) {
        Api.getInstance().getService().Get_news(type).enqueue(new Callback<HttpService.Result>() {
            @Override
            public void onResponse(Call<HttpService.Result> call, Response<HttpService.Result> response) {
                if (response.body() != null){
                    showList(response.body());
                }else {
                    mAttachView.showMessage(mContext.getString(R.string.empty_data));
                }
                mAttachView.stopRefresh();
            }

            @Override
            public void onFailure(Call<HttpService.Result> call, Throwable t) {
                mAttachView.showMessage(mContext.getString(R.string.load_failed));
                mAttachView.stopRefresh();
            }
        });
    }

    private void showList(final HttpService.Result result){
        mAttachView.showList(result.getResult().getData());
    }
}

