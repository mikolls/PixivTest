package com.example.administrator.pixivtest.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.example.administrator.pixivtest.Bean.Daily;
import com.example.administrator.pixivtest.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/11/10.
 */

public class DailyAdapter extends RecyclerView.Adapter<DailyAdapter.ViewHolder> {

    private Context mContext;

    private ArrayList<Daily> dailyList ;

    public DailyAdapter(Context context,ArrayList<Daily> dailyList){
        this.mContext = context;
        this.dailyList = dailyList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemdaily,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        GlideUrl glideUrl = new GlideUrl(dailyList.get(position).getImage_url(), new LazyHeaders.Builder()
                .addHeader("Referer","https://www.pixiv.net/")
                .addHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.100 Safari/537.36")
                .build());
        Glide.with(mContext).load(glideUrl).into(holder.dailyView);
    }

    @Override
    public int getItemCount() {
        return dailyList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        CardView cardView;
        ImageView dailyView;

        public ViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView;
            dailyView = (ImageView) itemView.findViewById(R.id.daily_img);



        }
    }
}
