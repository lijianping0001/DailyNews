package com.jianping.lee.dailynews.presenter.contract;

import com.jianping.lee.dailynews.base.BasePresenter;
import com.jianping.lee.dailynews.base.BaseView;
import com.jianping.lee.dailynews.model.http.HttpService;

import java.util.List;

/**
 * Created by Li on 2017/2/21.
 */
public interface NewsMainContract {

    interface View extends BaseView {
        void showHideFab(boolean show);
    }

    interface Presenter extends BasePresenter<View> {
    }
}
