package com.example.administrator.pixivtest;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.example.administrator.pixivtest.Utils.GlideCircleTransform;

public class DailyActivity extends AppCompatActivity {

    public static final String Daily_img = "daily_img";

    public static final String Daily_title = "daily_title";

    public static final String Daily_painter = "daily_painter";

    public static final String Daily_painterImg = "daily_painterImg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily);
        Intent intent = getIntent();

        String dailyImg = intent.getStringExtra(Daily_img);
        String dailyTitle = intent.getStringExtra(Daily_title);
        String dailyPainter1 = intent.getStringExtra(Daily_painter);
        String dailyPainterImg = intent.getStringExtra(Daily_painterImg);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout)findViewById(R.id.collapsing_toolbar);
        ImageView dailyImgView = (ImageView)findViewById(R.id.daily_image_view);
        ImageView dailyPainterImgView = (ImageView)findViewById(R.id.PainterImg);
        TextView dailyPainter = (TextView)findViewById(R.id.daily_painter_text);
        dailyPainter.setText(dailyPainter1);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar!= null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        collapsingToolbarLayout.setTitle(dailyTitle);
        GlideUrl glideUrl1 = new GlideUrl(dailyImg,new LazyHeaders.Builder()
                .addHeader("Referer","https://www.pixiv.net/")
                .addHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.100 Safari/537.36")
                .build());
        Glide.with(this).load(glideUrl1).into(dailyImgView);
        GlideUrl glideUrl = new GlideUrl(dailyPainterImg,new LazyHeaders.Builder()
                .addHeader("Referer","https://www.pixiv.net/")
                .addHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.100 Safari/537.36")
                .build());
        Glide.with(this).load(glideUrl).centerCrop().transform(new GlideCircleTransform(MyApplication.getContext())).into(dailyPainterImgView);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
