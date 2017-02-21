package com.jianping.lee.dailynews.engine;

import android.app.Activity;
import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jianping.lee.dailynews.R;

/**
 * Created by codeest on 2016/8/2.
 */
public class ImageLoaderProxy {

    public static void load(Context context, String url, ImageView iv) {    //使用Glide加载圆形ImageView(如头像)时，不要使用占位图
        Glide.with(context).load(url).crossFade().placeholder(R.drawable.image_news_default).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(iv);

    }

    public static void load(Activity activity, String url, ImageView iv) {    //使用Glide加载圆形ImageView(如头像)时，不要使用占位图
        Glide.with(activity).load(url).crossFade().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(iv);

    }

    public static void loadAll(Context context, String url, ImageView iv) {    //不缓存，全部从网络加载
        Glide.with(context).load(url).crossFade().skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).into(iv);
    }

    public static void loadAll(Activity activity, String url, ImageView iv) {    //不缓存，全部从网络加载
        Glide.with(activity).load(url).crossFade().skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).into(iv);
    }

    /**
     * 暂停加载
     * @param context
     */
    public static void pause(Context context){
        if (!Glide.with(context).isPaused()){
            Glide.with(context).pauseRequests();
        }
    }

    /**
     * 恢复加载
     * @param context
     */
    public static void resume(Context context){
        if (Glide.with(context).isPaused()){
            Glide.with(context).resumeRequests();
        }
    }
}
