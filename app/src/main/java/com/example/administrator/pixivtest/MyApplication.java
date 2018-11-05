package com.example.administrator.pixivtest;

import android.app.Application;
import android.content.Context;

import com.example.administrator.pixivtest.store.PersistentCookieStore;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by Administrator on 2018/10/31.
 */

public class MyApplication extends Application{

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();



    }

    public static Context getContext(){
        return context;
    }
}
