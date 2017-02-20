package com.jianping.lee.dailynews.presenter.contract;

import com.jianping.lee.dailynews.base.BasePresenter;
import com.jianping.lee.dailynews.base.BaseView;
import com.jianping.lee.dailynews.model.http.HttpService;

import java.util.List;

/**
 * Created by Li on 2017/2/14.
 */
public interface TypeNewsContract {

    interface View extends BaseView{
        void stopRefresh();

        void showList(List<HttpService.Result.ResultBean.DataBean> listData);
    }

    interface Presenter extends BasePresenter<View> {
        void getData(String type);
    }
}
