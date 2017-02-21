package com.jianping.lee.dailynews.ui.fragment;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.NumberPicker;

import com.jianping.lee.dailynews.R;
import com.jianping.lee.dailynews.adapter.TypeNewsAdapter;
import com.jianping.lee.dailynews.base.BaseFragment;
import com.jianping.lee.dailynews.base.ConstConfig;
import com.jianping.lee.dailynews.engine.ImageLoaderProxy;
import com.jianping.lee.dailynews.engine.RxBus;
import com.jianping.lee.dailynews.model.UserEvent;
import com.jianping.lee.dailynews.model.http.HttpService;
import com.jianping.lee.dailynews.presenter.TypeNewsPresenter;
import com.jianping.lee.dailynews.presenter.contract.TypeNewsContract;
import com.jianping.lee.dailynews.ui.activity.TypeNewsDetailActivity;
import com.jianping.lee.dailynews.utils.LogUtils;
import com.jianping.lee.dailynews.widget.RotateLoading;

import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Li on 2017/2/10.
 */
public class TypeNewsFragment extends BaseFragment<TypeNewsPresenter> implements TypeNewsContract.View {

    @InjectView(R.id.sfl_type_news)
    SwipeRefreshLayout sflRefresh;

    @InjectView(R.id.rv_type_news)
    RecyclerView rvRecyclerView;

    @InjectView(R.id.rl_loading)
    RotateLoading rlLoading;

    private TypeNewsAdapter mAdapter;

    private String type;

    private LinearLayoutManager mLayoutManager;

    public static TypeNewsFragment newInstance(String type){
        TypeNewsFragment newsFragment = new TypeNewsFragment();
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        newsFragment.setArguments(bundle);
        return newsFragment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle bundle = getArguments();
        type = bundle.getString("type");
        sflRefresh.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        sflRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getData(type);
            }
        });

        mPresenter.getData(type);
        rlLoading.start();
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_type_news;
    }

    @Override
    protected void initPresenter() {
        mPresenter = new TypeNewsPresenter(mContext);
    }

    @Override
    public void showMessage(String msg) {
        showToast(msg);
    }

    @Override
    public void stopRefresh() {
        rlLoading.stop();
        if (sflRefresh.isRefreshing()){
            sflRefresh.setRefreshing(false);
        }
    }

    @Override
    public void showList(final List<HttpService.Result.ResultBean.DataBean> listData) {
        if (mAdapter == null){
            mAdapter = new TypeNewsAdapter(mContext, listData);
            mLayoutManager = new LinearLayoutManager(mContext);
            rvRecyclerView.setLayoutManager(mLayoutManager);
            rvRecyclerView.setAdapter(mAdapter);

//            rvRecyclerView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
//                @Override
//                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//                    if (scrollY == oldScrollY) {
//                        ImageLoaderProxy.resume(mContext);
//                    } else {
//                        ImageLoaderProxy.pause(mContext);
//                    }
//
//                    //下移隐藏
//                    if (scrollY - oldScrollY > 0) {
//                        LogUtils.i("向下滑动");
//                        RxBus.getDefault().post(new UserEvent(ConstConfig.FAB_TOP_SHOW_HIDE, "", false));
//                    } else if (scrollY - oldScrollY < 0) {//上移出现
//                        LogUtils.i("向上滑动");
//                        RxBus.getDefault().post(new UserEvent(ConstConfig.FAB_TOP_SHOW_HIDE, "", true));
//                    }
//                }
//            });
            rvRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    switch (newState) {
                        case NumberPicker.OnScrollListener.SCROLL_STATE_IDLE:
                            ImageLoaderProxy.resume(mContext);
                            break;
                        case NumberPicker.OnScrollListener.SCROLL_STATE_FLING:
                            ImageLoaderProxy.pause(mContext);
                            break;
                        case NumberPicker.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
                            ImageLoaderProxy.pause(mContext);
                            break;
                        default:
                            break;
                    }
                }

                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    //下移隐藏
                    if (dy > 0) {
                        LogUtils.i("向下滑动");
                        RxBus.getDefault().post(new UserEvent(ConstConfig.FAB_TOP_SHOW_HIDE, "", false));
                    } else if (dy < 0) {//上移出现
                        LogUtils.i("向上滑动");
                        RxBus.getDefault().post(new UserEvent(ConstConfig.FAB_TOP_SHOW_HIDE, "", true));
                    }
                }
            });

        }
        mAdapter.setOnItemClickListner(new TypeNewsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent();
                intent.setClass(mContext, TypeNewsDetailActivity.class);
                intent.putExtra("url", listData.get(position).getUrl());
                intent.putExtra("imgUrl", listData.get(position).getThumbnail_pic_s());
                intent.putExtra("title", listData.get(position).getTitle());
                ActivityOptions options = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    options = ActivityOptions.makeSceneTransitionAnimation((Activity) mContext, view, "imageView");
                }
                if (null == options){
                    mContext.startActivity(intent);
                }else {
                    mContext.startActivity(intent,options.toBundle());
                }
            }
        });
    }

    /**
     * 滑动到顶部
     */
    @Override
    public void listGoTop() {
        if (rvRecyclerView != null){
            rvRecyclerView.smoothScrollToPosition(0);
        }
    }

}
