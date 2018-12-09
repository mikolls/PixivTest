package com.example.administrator.pixivtest;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Looper;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.example.administrator.pixivtest.Utils.GlideCircleTransform;
import com.example.administrator.pixivtest.Utils.GlideUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;

public class DailyActivity extends AppCompatActivity {

    public static final String Daily_img = "daily_img";

    public static final String Daily_title = "daily_title";

    public static final String Daily_painter = "daily_painter";

    public static final String Daily_painterImg = "daily_painterImg";

    public static final String largeImg = "largeImg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily);
        Intent intent = getIntent();

        String dailyImg = intent.getStringExtra(Daily_img);
        String dailyTitle = intent.getStringExtra(Daily_title);
        String dailyPainter1 = intent.getStringExtra(Daily_painter);
        String dailyPainterImg = intent.getStringExtra(Daily_painterImg);
        final String largeImage = intent.getStringExtra(largeImg);

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

        GlideUtils.loadImg(dailyImg,dailyImgView);
        GlideUtils.loadCircleImg(dailyPainterImg,dailyPainterImgView);

        dailyImgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(DailyActivity.this,OriginalimgActivity.class);
                intent1.putExtra(OriginalimgActivity.largeImage,largeImage);
                startActivity(intent1);
            }
        });

        Button download = (Button)findViewById(R.id.button_download);
        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                download(largeImage);
            }
        });

    }

    private void download(final String url){
        new Thread(new Runnable() {
            @Override
            public void run() {
                final Bitmap bitmap = getPic(url);//下载
                onSaveBitmap(bitmap, DailyActivity.this);//保存到本地
            }
        }).start();
    }
    public Bitmap getPic(String url) {
        //获取okHttp对象get请求
        try {
            OkHttpClient client = new OkHttpClient();
            //获取请求对象
            Request request = new Request.Builder()
                    .addHeader("Referer","https://www.pixiv.net/")
                    .addHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.100 Safari/537.36")
                    .url(url).build();
            //获取响应体
            ResponseBody body = client.newCall(request).execute().body();
            //获取流
            InputStream in = body.byteStream();
            //转化为bitmap
            Bitmap bitmap = BitmapFactory.decodeStream(in);

            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void onSaveBitmap(final Bitmap mBitmap, final Context context) {
        // 第一步：首先保存图片
        //将Bitmap保存图片到指定的路径/sdcard/Boohee/下，文件名以当前系统时间命名,但是这种方法保存的图片没有加入到系统图库中
        File appDir = new File(Environment.getExternalStorageDirectory(), "Pixiv(miko)");

        if (!appDir.exists()) {
            appDir.mkdir();
        }

        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);

        try {
            FileOutputStream fos = new FileOutputStream(file);
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Looper.prepare();
        Toast.makeText(DailyActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
        Looper.loop();
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
