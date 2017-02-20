package com.jianping.lee.dailynews.ui.activity;

import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.transition.Transition;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jianping.lee.dailynews.R;
import com.jianping.lee.dailynews.base.BaseActivity;
import com.jianping.lee.dailynews.engine.ImageLoader;
import com.jianping.lee.dailynews.presenter.TypeNewsDetailPresenter;
import com.jianping.lee.dailynews.presenter.contract.TypeNewsDetailContract;

import net.opacapp.multilinecollapsingtoolbar.CollapsingToolbarLayout;

import butterknife.InjectView;

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

    @InjectView(R.id.tv_type_news_detail_like)
    TextView tvBottomLike;

    @InjectView(R.id.tv_type_news_detail_comment)
    TextView tvBottomComment;

    @InjectView(R.id.tv_type_news_detail_share)
    TextView tvShare;

    @InjectView(R.id.ll_type_news_detail_bottom)
    LinearLayout llDetailBottom;

    @InjectView(R.id.fab_type_news_detail)
    FloatingActionButton fabLike;

    boolean bottomShow = true;
    boolean imageShow = true;
    boolean transitionEnd = false;
    boolean noTransition = false;

    private String imgUrl;
    private String title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_news_detail);
        mPresenter = new TypeNewsDetailPresenter(this);
        initView();
    }

    private void initView() {
        toolbar.setTitleTextColor(Color.WHITE);
        setToolBar(toolbar, "");
        String url = getIntent().getStringExtra("url");
        imgUrl = getIntent().getStringExtra("imgUrl");
        title = getIntent().getStringExtra("title");
        ctlToolbar.setTitle(title);
        WebSettings settings = wvContent.getSettings();
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        wvContent.loadUrl(url);
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
                    llDetailBottom.animate().translationY(llDetailBottom.getHeight());
                } else if (scrollY - oldScrollY < 0 && !bottomShow) {//上移出现
                    bottomShow = true;
                    llDetailBottom.animate().translationY(0);
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
                if (!TextUtils.isEmpty(imgUrl)) {
                    imageShow = true;
                    ImageLoader.load(mContext, imgUrl, ivBgImage);
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
