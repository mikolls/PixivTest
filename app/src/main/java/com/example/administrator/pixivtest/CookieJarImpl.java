package com.example.administrator.pixivtest;



import com.example.administrator.pixivtest.store.CookieStore;

import java.util.List;

import okhttp3.CookieJar;
import okhttp3.Cookie;
import okhttp3.HttpUrl;

/**
 * Created by Administrator on 2018/8/19.
 */

public class CookieJarImpl implements CookieJar{
    private CookieStore cookieStore;
    public CookieJarImpl(CookieStore cookieStore) {
        if (cookieStore == null) new IllegalArgumentException("cookieStore can not be null.");
        this.cookieStore = cookieStore;
    }

    @Override
    public synchronized void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        cookieStore.add(url, cookies);
    }

    @Override
    public synchronized List<Cookie> loadForRequest(HttpUrl url) {
        return cookieStore.get(url);
    }

    public CookieStore getCookieStore() {
        return cookieStore;
    }
}
