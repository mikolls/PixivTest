package com.example.administrator.pixivtest.ViewParger;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.pixivtest.Bean.Daily;
import com.example.administrator.pixivtest.Bean.DailyBean;
import com.example.administrator.pixivtest.CookieJarImpl;
import com.example.administrator.pixivtest.MainActivity;
import com.example.administrator.pixivtest.MyApplication;
import com.example.administrator.pixivtest.R;
import com.example.administrator.pixivtest.adapter.DailyAdapter;
import com.example.administrator.pixivtest.store.PersistentCookieStore;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/11/8.
 */

public class DailyFragment extends Fragment {
    private OkHttpClient.Builder builder;

    private OkHttpClient client;

    private PersistentCookieStore persistentCookieStore;

    private CookieJarImpl cookieJarImpl;

    private List<String> dailyList;

    private RecyclerView recyclerView;

    private ArrayList<Daily> androidDaily;

    private DailyAdapter adapter;

    private  Calendar cal;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    dailyList = (List<String>) msg.obj;
                    androidDaily = initData();
                    adapter = new DailyAdapter(MyApplication.getContext(),androidDaily);
                    recyclerView.setAdapter(adapter);
                   /* for (String s : dailyList){
                        Log.i("111",s);
                    }*/
                    break;
            }
        }
    };
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.daily_fragment,container,false);

        builder = new OkHttpClient.Builder();
        persistentCookieStore = new PersistentCookieStore(MyApplication.getContext());
        cookieJarImpl = new CookieJarImpl(persistentCookieStore);
        builder.cookieJar(cookieJarImpl);
        client = builder.build();

        getDailyJson();

        recyclerView = view.findViewById(R.id.daily_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MyApplication.getContext());
        recyclerView.setLayoutManager(layoutManager);


        return view;
    }

    private void getDailyJson(){    //从pixiv的API中获取日榜json并显示出来图片
        cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        String year = String.valueOf(cal.get(Calendar.YEAR));
        String month = String.valueOf(cal.get(Calendar.MONTH)+1);
        int day = cal.get(Calendar.DATE) - 2;        //2018-11-08

        String day1 = day + "";

        String dailyUrl = "https://api.imjad.cn/pixiv/v1/?type=rank&content=all&mode=daily&page=1&per_page=20&date="+year+"-"+month+"-"+day1;
        Log.i("dailyUrl",dailyUrl);
        Request request = new Request.Builder()
                .url(dailyUrl)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String dailyJson = response.body().string();
                Gson gson = new Gson();
                DailyBean dailyBean = gson.fromJson(dailyJson,DailyBean.class);
                dailyBean.getResponse().get(0).getWorks();
                List<String> list = new ArrayList<>();
                for (int i = 0;i<dailyBean.getResponse().get(0).getWorks().size();i++){
                    list.add(dailyBean.getResponse().get(0).getWorks().get(i).getWork().getImage_urls().getPx_480mw());
                    Log.i("url",dailyBean.getResponse().get(0).getWorks().get(i).getWork().getImage_urls().getPx_480mw());
                }
                Message message = mHandler.obtainMessage();
                message.obj = list;
                message.what = 1;
                mHandler.sendMessage(message);
            }
        });
    }

    private ArrayList<Daily> initData() {
        ArrayList<Daily> dailies = new ArrayList<>();
        for(int i=0; i<dailyList.size(); ++i){
            Daily daily = new Daily();
            daily.setImage_url(dailyList.get(i));
            dailies.add(daily);
        }
        return dailies;

    }
}
