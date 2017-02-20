package com.jianping.lee.dailynews.ui.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jianping.lee.dailynews.R;
import com.jianping.lee.dailynews.adapter.ViewPagerAdapter;
import com.jianping.lee.dailynews.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

/**
 * Created by Li on 2017/2/10.
 */
public class NewsMainFragment extends BaseFragment {
    String[][] newsType = new String[][]{{"头条", "top"}, {"社会", "shehui"}, {"国内", "guonei"},
            {"国际", "guoji"}, {"娱乐", "yule"}, {"体育", "tiyu"}, {"军事", "junshi"}, {"科技", "keji"},
            {"财经", "caijing"}, {"时尚", "shishang"}};

    @InjectView(R.id.tl_news)
    TabLayout mTabLayout;

    @InjectView(R.id.vp_news)
    ViewPager mViewPager;

    private List<Fragment> mViewList = new ArrayList<>();
    private List<String> mTitleList = new ArrayList<>();
    private ViewPagerAdapter mAdapter;

    @Override
    protected int getLayout() {
        return R.layout.fragment_news_main;
    }

    @Override
    protected void initPresenter() {

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
}
