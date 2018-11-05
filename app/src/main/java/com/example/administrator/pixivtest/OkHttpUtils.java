package com.example.administrator.pixivtest;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;

import com.example.administrator.pixivtest.store.PersistentCookieStore;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by Administrator on 2018/11/2.
 */

public class OkHttpUtils {

    private CookieJarImpl cookieJarImpl;

    private PersistentCookieStore persistentCookieStore;

    public static final long DEFAULT_READ_TIMEOUT_MILLIS = 15 * 1000;
    public static final long DEFAULT_WRITE_TIMEOUT_MILLIS = 20 * 1000;
    public static final long DEFAULT_CONNECT_TIMEOUT_MILLIS = 20 * 1000;
    private static final long HTTP_RESPONSE_DISK_CACHE_MAX_SIZE = 10 * 1024 * 1024;
    private static volatile OkHttpUtils sInstance;
    private OkHttpClient mOkHttpClient;
    private OkHttpUtils() {

        cookieJarImpl = new CookieJarImpl(persistentCookieStore);
        persistentCookieStore = new PersistentCookieStore(MyApplication.getContext());

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        //包含header、body数据
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        mOkHttpClient = new OkHttpClient.Builder()
                .cookieJar(cookieJarImpl)
                .readTimeout(DEFAULT_READ_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS)
                .writeTimeout(DEFAULT_WRITE_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS)
                .connectTimeout(DEFAULT_CONNECT_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS)
                //FaceBook 网络调试器，可在Chrome调试网络请求，查看SharePreferences,数据库等
                .addNetworkInterceptor(new StethoInterceptor())
                //http数据log，日志中打印出HTTP请求&响应数据
                .addInterceptor(loggingInterceptor)
                .build();
    }

    public static OkHttpUtils getInstance() {
        if (sInstance == null) {
            synchronized (OkHttpUtils.class) {
                if (sInstance == null) {
                    sInstance = new OkHttpUtils();
                }
            }
        }
        return sInstance;
    }

    public OkHttpClient getOkHttpClient() {
        return mOkHttpClient;
    }

    public void setCache(Context appContext) {
        final File baseDir = appContext.getApplicationContext().getCacheDir();
        if (baseDir != null) {
            final File cacheDir = new File(baseDir, "HttpResponseCache");
            mOkHttpClient.newBuilder().cache((new Cache(cacheDir, HTTP_RESPONSE_DISK_CACHE_MAX_SIZE)));
        }
    }
}
