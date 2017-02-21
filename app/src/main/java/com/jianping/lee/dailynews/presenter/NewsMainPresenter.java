package com.jianping.lee.dailynews.presenter;

import android.content.Context;

import com.jianping.lee.dailynews.base.ConstConfig;
import com.jianping.lee.dailynews.base.RxPresenter;
import com.jianping.lee.dailynews.engine.RxBus;
import com.jianping.lee.dailynews.model.UserEvent;
import com.jianping.lee.dailynews.presenter.contract.NewsMainContract;
import com.jianping.lee.dailynews.utils.LogUtils;

import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by Li on 2017/2/21.
 */
public class NewsMainPresenter extends RxPresenter<NewsMainContract.View> implements NewsMainContract.Presenter {

    private Context mContext;

    private boolean fabShow = true;

    public NewsMainPresenter(Context context){
        this.mContext = context;
        init();
    }

    private void init(){
        Subscription subscription = RxBus.getDefault().toObservable(UserEvent.class)
                .subscribe(new Action1<UserEvent>() {
                    @Override
                    public void call(UserEvent userEvent) {
                        if (ConstConfig.FAB_TOP_SHOW_HIDE.equals(userEvent.getId())){
                            boolean show = userEvent.isShow();
                            mAttachView.showHideFab(show);
                        }
                    }
                });

        addSubscribe(subscription);
    }

}
