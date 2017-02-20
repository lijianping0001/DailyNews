package com.jianping.lee.dailynews.ui.activity;

import android.graphics.Color;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.jianping.lee.dailynews.R;
import com.jianping.lee.dailynews.base.BaseActivity;
import com.jianping.lee.dailynews.ui.fragment.NewsMainFragment;

import butterknife.InjectView;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    @InjectView(R.id.toolbar)
    Toolbar mToolbar;

    @InjectView(R.id.dl_main_drawer_layout)
    DrawerLayout mDrawerLayout;

    @InjectView(R.id.nv_main_nav)
    NavigationView mNavView;

    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mToolbar.setTitleTextColor(Color.WHITE);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.app_name,
                R.string.news){
            @Override
            public void onDrawerClosed(View drawerView) {
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };

        mDrawerToggle.syncState();
        mDrawerLayout.addDrawerListener(mDrawerToggle);

        mNavView.setNavigationItemSelectedListener(this);
//        replaceFragment(R.id.fl_main_container, new NewsMainFragment());
        loadMultipleRootFragment(R.id.fl_main_container, 0, new NewsMainFragment());
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_news:
                replaceFragment(R.id.fl_main_container, new NewsMainFragment());
                break;
        }

        mDrawerLayout.closeDrawers();
        return true;
    }

    @Override
    public void showMessage(String msg) {

    }
}
