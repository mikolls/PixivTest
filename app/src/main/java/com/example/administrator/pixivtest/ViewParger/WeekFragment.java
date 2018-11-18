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
import com.example.administrator.pixivtest.Bean.Week;
import com.example.administrator.pixivtest.Bean.WeekBean;
import com.example.administrator.pixivtest.CookieJarImpl;
import com.example.administrator.pixivtest.MyApplication;
import com.example.administrator.pixivtest.R;
import com.example.administrator.pixivtest.adapter.DailyAdapter;
import com.example.administrator.pixivtest.adapter.WeekAdapter;
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

public class WeekFragment extends Fragment {

    private OkHttpClient.Builder builder;

    private OkHttpClient client;

    private PersistentCookieStore persistentCookieStore;

    private CookieJarImpl cookieJarImpl;

    private RecyclerView recyclerView;

    private ArrayList<Week> androidWeek;

    private WeekAdapter adapter;

    private List<String> weekList;

    private List<String> worksList;

    private List<String> painterList;

    private Message message;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    androidWeek = (ArrayList<Week>) msg.obj;
                    adapter = new WeekAdapter(MyApplication.getContext(),androidWeek);
                    recyclerView.setAdapter(adapter);
                    break;
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.week_fragment,container,false);

        builder = new OkHttpClient.Builder();
        persistentCookieStore = new PersistentCookieStore(MyApplication.getContext());
        cookieJarImpl = new CookieJarImpl(persistentCookieStore);
        builder.cookieJar(cookieJarImpl);
        client = builder.build();

        getWeekJson();

        recyclerView = view.findViewById(R.id.week_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MyApplication.getContext());
        recyclerView.setLayoutManager(layoutManager);
        return view;
    }

    private void getWeekJson(){    //从pixiv的API中获取周榜json并显示出来图片


        String dailyUrl = "https://api.imjad.cn/pixiv/v1/?type=rank&content=all&mode=weekly";
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
                String weekJson = response.body().string();
                Gson gson = new Gson();
                WeekBean weekBean = gson.fromJson(weekJson,WeekBean.class);
                weekBean.getResponse().get(0).getWorks();
                List<String> imgurl = new ArrayList<>();
                List<String> works = new ArrayList<>();
                List<String> painter = new ArrayList<>();
                for (int i = 0;i<weekBean.getResponse().get(0).getWorks().size();i++){
                    imgurl.add(weekBean.getResponse().get(0).getWorks().get(i).getWork().getImage_urls().getPx_480mw());
                    painter.add(weekBean.getResponse().get(0).getWorks().get(i).getWork().getUser().getName());
                    works.add(weekBean.getResponse().get(0).getWorks().get(i).getWork().getTitle());
                    Log.i("url",weekBean.getResponse().get(0).getWorks().get(i).getWork().getImage_urls().getPx_480mw());
                }
                weekList = imgurl;
                worksList = works;
                painterList = painter;
                ArrayList<Week> WeeklList = initData();
                message = mHandler.obtainMessage();
                message.obj = WeeklList;
                message.what = 1;
                mHandler.sendMessage(message);

            }
        });
    }
    private ArrayList<Week> initData() {
        ArrayList<Week> weeks = new ArrayList<>();

        for(int i=0; i<weekList.size(); ++i){
            Week week = new Week();
            week.setImage_url(weekList.get(i));
            week.setWorks(worksList.get(i));
            week.setPainter(painterList.get(i));
            weeks.add(week);
        }
        return weeks;

    }
}
