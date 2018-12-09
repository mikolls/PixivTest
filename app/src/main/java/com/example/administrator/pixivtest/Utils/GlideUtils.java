package com.example.administrator.pixivtest.Utils;

import android.app.Application;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.example.administrator.pixivtest.MyApplication;

/**
 * Created by Administrator on 2018/11/19.
 */

public class GlideUtils {

    private static GlideUtils instance;
    public static GlideUtils getInstance(){
        if(instance==null){
            synchronized (GlideUtils.class) {
                if(instance==null){
                    instance=new GlideUtils();
                }
            }
        }
        return instance;
    }

    /**
     *
     * @param url
     * @param imageView
     */

    public static void loadImg(String url, ImageView imageView){
        GlideUrl glideUrl = new GlideUrl(url,new LazyHeaders.Builder()
                .addHeader("Referer","https://www.pixiv.net/")
                .addHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.100 Safari/537.36")
                .build());
        Glide.with(MyApplication.getContext()).load(glideUrl).into(imageView);

    }

    public static void loadCircleImg(String url, ImageView imageView){
        GlideUrl glideUrl = new GlideUrl(url,new LazyHeaders.Builder()
                .addHeader("Referer","https://www.pixiv.net/")
                .addHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.100 Safari/537.36")
                .build());
        Glide.with(MyApplication.getContext()).load(glideUrl).centerCrop().transform(new GlideCircleTransform(MyApplication.getContext())).into(imageView);
    }

}
