package com.jianping.lee.dailynews.ui.fragment;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.jianping.lee.dailynews.R;
import com.jianping.lee.dailynews.adapter.ViewPagerAdapter;
import com.jianping.lee.dailynews.base.BaseFragment;
import com.jianping.lee.dailynews.base.ConstConfig;
import com.jianping.lee.dailynews.engine.RxBus;
import com.jianping.lee.dailynews.model.UserEvent;
import com.jianping.lee.dailynews.presenter.NewsMainPresenter;
import com.jianping.lee.dailynews.presenter.contract.NewsMainContract;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Li on 2017/2/10.
 */
public class NewsMainFragment extends BaseFragment<NewsMainContract.Presenter> implements NewsMainContract.View {
    String[][] newsType = new String[][]{{"头条", "top"}, {"社会", "shehui"}, {"国内", "guonei"},
            {"国际", "guoji"}, {"娱乐", "yule"}, {"体育", "tiyu"}, {"军事", "junshi"}, {"科技", "keji"},
            {"财经", "caijing"}, {"时尚", "shishang"}};

    @InjectView(R.id.tl_news)
    TabLayout mTabLayout;

    @InjectView(R.id.vp_news)
    ViewPager mViewPager;

    @InjectView(R.id.fab_news_main)
    FloatingActionButton fabTop;

    private List<Fragment> mViewList = new ArrayList<>();
    private List<String> mTitleList = new ArrayList<>();
    private ViewPagerAdapter mAdapter;

    @Override
    protected int getLayout() {
        return R.layout.fragment_news_main;
    }

    @Override
    protected void initPresenter() {
        mPresenter = new NewsMainPresenter(mContext);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        for (int i = 0; i < newsType.length; i ++){
            mViewList.add(TypeNewsFragment.newInstance(newsType[i][1]));
            mTitleList.add(newsType[i][0]);
            mTabLayout.addTab(mTabLayout.newTab().setText(newsType[i][0]));
        }
        mAdapter = new ViewPagerAdapter(getChildFragmentManager(), mViewList, mTitleList);
        mViewPager.setAdapter(mAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
    }

    @OnClick(R.id.fab_news_main)
    void onClickGoTop(){
        RxBus.getDefault().post(new UserEvent(ConstConfig.NEWS_TO_TOP, ""));
    }

    @Override
    public void showHideFab(boolean show) {
        if (show){
            fabTop.animate().alpha(1.0f);
        }else {
            fabTop.animate().alpha(0.0f);
        }
    }

    @Override
    public void showMessage(String msg) {
        showToast(msg);
    }
}
