package com.example.administrator.pixivtest;

import android.content.Intent;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.administrator.pixivtest.store.PersistentCookieStore;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WelcomeActivity extends AppCompatActivity {

    private OkHttpClient.Builder builder;

    private OkHttpClient client;

    private PersistentCookieStore persistentCookieStore;

    private CookieJarImpl cookieJarImpl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_activity);

        builder = new OkHttpClient.Builder();
        persistentCookieStore = new PersistentCookieStore(getApplicationContext());
        cookieJarImpl = new CookieJarImpl(persistentCookieStore);
        builder.cookieJar(cookieJarImpl);
        client = builder.build();

        getIndex();
    }

    private void getIndex(){


                Request request = new Request.Builder()
                        .url("https://www.pixiv.net/")
                        .build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String responseContent = response.body().string();

//                        Log.i("getUserName:  ",response.body().string().toString());

                        Document parse = Jsoup.parse(responseContent);
                        Elements selecct = parse.getElementsByTag("title");

                        String title = selecct.text();
                        String title_index = "イラスト コミュニケーションサービス[pixiv(ピクシブ)]";
                        Log.i("title:",title);
                        if (title.equals(title_index)){

                            Intent intent = new Intent(WelcomeActivity.this,LoginActivity.class);
                            startActivity(intent);
                        }else {
                            Intent intent = new Intent(WelcomeActivity.this,MainActivity.class);
                            startActivity(intent);
                        }
                    }
                });
            }

}
