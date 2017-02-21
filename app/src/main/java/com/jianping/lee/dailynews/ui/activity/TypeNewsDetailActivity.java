package com.jianping.lee.dailynews.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.NestedScrollView;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.transition.Transition;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jianping.lee.dailynews.R;
import com.jianping.lee.dailynews.base.BaseActivity;
import com.jianping.lee.dailynews.engine.ImageLoaderProxy;
import com.jianping.lee.dailynews.presenter.TypeNewsDetailPresenter;
import com.jianping.lee.dailynews.presenter.contract.TypeNewsDetailContract;
import com.jianping.lee.dailynews.utils.ScreenUtils;
import com.jianping.lee.dailynews.utils.StatusBarUtils;

import net.opacapp.multilinecollapsingtoolbar.CollapsingToolbarLayout;

import butterknife.InjectView;
import butterknife.OnClick;

public class TypeNewsDetailActivity extends BaseActivity<TypeNewsDetailPresenter> implements TypeNewsDetailContract.View {

    @InjectView(R.id.iv_type_news_detail_bar)
    ImageView ivBgImage;

    @InjectView(R.id.tv_type_news_detail_title)
    TextView tvCopyright;

    @InjectView(R.id.tb_type_news_detail_toolbar)
    Toolbar toolbar;

    @InjectView(R.id.ctl_type_news_toolbar)
    CollapsingToolbarLayout ctlToolbar;

    @InjectView(R.id.wv_type_news_detail)
    WebView wvContent;

    @InjectView(R.id.nsv_type_news_detail)
    NestedScrollView nsvScroller;

    @InjectView(R.id.tv_type_news_detail_write)
    TextView tvWriteComment;

    @InjectView(R.id.tv_type_news_detail_comment)
    TextView tvBottomComment;

    @InjectView(R.id.tv_type_news_detail_like)
    TextView tvBottomLike;

    @InjectView(R.id.fl_type_news_detail_bottom)
    FrameLayout flDetailBottom;

    @InjectView(R.id.fab_type_news_detail)
    FloatingActionButton fabShare;

    boolean bottomShow = true;
    boolean imageShow = true;
    boolean transitionEnd = false;

    private String mImgUrl;
    private String mTitle;
    private String mUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_news_detail);
        mPresenter = new TypeNewsDetailPresenter(this);
        initView();
    }

    @Override
    protected void setStatusBar() {
        //不是全屏设置状态栏为透明
        if (!ScreenUtils.isFullScreen(this)){
            StatusBarUtils.setTransparent(this);
        }
    }

    private void initView() {
        toolbar.setTitleTextColor(Color.WHITE);
        setToolBar(toolbar, "");
        mUrl = getIntent().getStringExtra("url");
        mImgUrl = getIntent().getStringExtra("imgUrl");
        mTitle = getIntent().getStringExtra("title");
        ctlToolbar.setTitle(mTitle);
        WebSettings settings = wvContent.getSettings();
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        wvContent.loadUrl(mUrl);
        wvContent.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && wvContent.canGoBack()) {
                    wvContent.goBack();
                    return true;
                }
                return false;
            }
        });

        nsvScroller.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                //下移隐藏
                if (scrollY - oldScrollY > 0 && bottomShow) {
                    bottomShow = false;
                    flDetailBottom.animate().translationY(flDetailBottom.getHeight());
                } else if (scrollY - oldScrollY < 0 && !bottomShow) {//上移出现
                    bottomShow = true;
                    flDetailBottom.animate().translationY(0);
                }
            }
        });

        getWindow().getSharedElementEnterTransition().addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {

            }

            @Override
            public void onTransitionEnd(Transition transition) {

                transitionEnd = true;
                if (!TextUtils.isEmpty(mImgUrl)) {
                    imageShow = true;
                    ImageLoaderProxy.load(mContext, mImgUrl, ivBgImage);
                }
            }

            @Override
            public void onTransitionCancel(Transition transition) {

            }

            @Override
            public void onTransitionPause(Transition transition) {

            }

            @Override
            public void onTransitionResume(Transition transition) {

            }
        });
    }

    /**
     * 收藏
     */
    @OnClick(R.id.tv_type_news_detail_like)
    void onClickLike(){
        if (tvBottomLike.isSelected()){
            tvBottomLike.setSelected(false);
        }else {
            tvBottomLike.setSelected(true);
        }
    }

    /**
     * 分享
     */
    @OnClick(R.id.fab_type_news_detail)
    void onClickShare(){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, mUrl);
        startActivity(Intent.createChooser(intent, getString(R.string.share_info)));
    }

    @Override
    public void showMessage(String msg) {
        showToast(msg);
    }

    @Override
    public void onBackPressedSupport() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            pop();
        } else {
            finishAfterTransition();
        }
    }
}
