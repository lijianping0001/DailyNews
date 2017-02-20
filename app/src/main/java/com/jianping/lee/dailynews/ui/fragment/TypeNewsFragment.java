package com.jianping.lee.dailynews.ui.fragment;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jianping.lee.dailynews.R;
import com.jianping.lee.dailynews.adapter.TypeNewsAdapter;
import com.jianping.lee.dailynews.base.BaseFragment;
import com.jianping.lee.dailynews.model.http.HttpService;
import com.jianping.lee.dailynews.presenter.TypeNewsPresenter;
import com.jianping.lee.dailynews.presenter.contract.TypeNewsContract;
import com.jianping.lee.dailynews.ui.activity.TypeNewsDetailActivity;
import com.jianping.lee.dailynews.utils.LogUtils;
import com.jianping.lee.dailynews.widget.RotateLoading;

import java.util.List;

import butterknife.InjectView;

/**
 * Created by Li on 2017/2/10.
 */
public class TypeNewsFragment extends BaseFragment<TypeNewsPresenter> implements TypeNewsContract.View {

    @InjectView(R.id.sfl_type_news)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @InjectView(R.id.rv_type_news)
    RecyclerView mRecycleView;

    @InjectView(R.id.rl_loading)
    RotateLoading mLoading;

    private TypeNewsAdapter mAdapter;

    private String type;

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
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getData(type);
            }
        });

        mPresenter.getData(type);
        mLoading.start();
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
        mLoading.stop();
        if (mSwipeRefreshLayout.isRefreshing()){
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void showList(final List<HttpService.Result.ResultBean.DataBean> listData) {
        if (mAdapter == null){
            mAdapter = new TypeNewsAdapter(mContext, listData);
            mRecycleView.setLayoutManager(new LinearLayoutManager(mContext));
            mRecycleView.setAdapter(mAdapter);
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
}
