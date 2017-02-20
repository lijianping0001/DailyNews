package com.jianping.lee.dailynews.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * @fileName: ViewPagerAdapter
 * @Author: Li Jianping
 * @Date: 2016/7/18 13:24
 * @Description:
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {

    private List<String> mTitleList;

    private List<Fragment> mViewList;

    public ViewPagerAdapter(FragmentManager manager, List<Fragment> viewList, List<String> titleList){
        super(manager);
        this.mViewList = viewList;
        this.mTitleList = titleList;
    }
    @Override
    public int getCount() {
        return mViewList.size();
    }

    @Override
    public Fragment getItem(int position) {
        return mViewList.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitleList.get(position);
    }


}
