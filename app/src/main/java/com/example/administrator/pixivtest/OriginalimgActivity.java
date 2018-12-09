package com.example.administrator.pixivtest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.github.chrisbanes.photoview.PhotoView;

public class OriginalimgActivity extends AppCompatActivity {

    public static final String largeImage = "largeImg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_originalimg);
        Intent intent = getIntent();
        String largeImg1 = intent.getStringExtra(largeImage);
        Log.i("原图",largeImg1);
        PhotoView photoView = (PhotoView)findViewById(R.id.largeImage);
        GlideUrl glideUrl = new GlideUrl(largeImg1, new LazyHeaders.Builder()
                .addHeader("Referer","https://www.pixiv.net/")
                .addHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.100 Safari/537.36")
                .build());
        Glide.with(this).load(glideUrl).into(photoView);

    }
}
