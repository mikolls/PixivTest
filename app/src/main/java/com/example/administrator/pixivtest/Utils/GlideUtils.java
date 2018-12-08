package com.example.administrator.pixivtest.Utils;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;

/**
 * Created by Administrator on 2018/11/19.
 */

public class GlideUtils {

    public static void GlideUtils(String s){
        GlideUrl glideUrl = new GlideUrl(s,new LazyHeaders.Builder()
                .addHeader("Referer","https://www.pixiv.net/")
                .addHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.100 Safari/537.36")
                .build());
    }

}
