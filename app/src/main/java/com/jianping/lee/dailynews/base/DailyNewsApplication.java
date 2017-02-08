package com.jianping.lee.dailynews.base;

import android.app.Activity;
import android.app.Application;
import android.support.annotation.NonNull;

import java.util.ArrayList;

/**
 * Created by Li on 2017/2/8.
 */
public class DailyNewsApplication extends Application{
    public static DailyNewsApplication instance;

    private ArrayList<Activity> activityList;

    public static synchronized DailyNewsApplication getInstance(){
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;
    }

    /**
     * 添加activity到列表中
     * @param activity
     */
    public void registerActivity(@NonNull Activity activity){
        if (activityList == null){
            activityList = new ArrayList<>();
        }
        activityList.add(activity);
    }

    /**
     * 删除activity
     * @param activity
     */
    public void unRegisterActivity(@NonNull Activity activity){
        if (activityList != null){
            activityList.remove(activity);
        }
    }

    /**
     * 结束所有的activity
     */
    public void finishAllactivities(){
        for (Activity activity : activityList){
            if (null != activity){
                activity.finish();
            }
        }
    }

    /**
     * 完全退出应用
     */
    public void exitApp(){
        if (activityList != null){
            synchronized (activityList){
                for (Activity activity : activityList){
                    if (activity != null && !activity.isFinishing()){
                        activity.finish();
                    }
                }
            }
        }
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }
}
