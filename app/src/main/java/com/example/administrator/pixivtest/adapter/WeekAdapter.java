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
import com.example.administrator.pixivtest.Bean.Week;
import com.example.administrator.pixivtest.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/11/16.
 */

public class WeekAdapter extends RecyclerView.Adapter<WeekAdapter.ViewHolder> {
    private Context mContext;

    private ArrayList<Week> weekList ;

    public WeekAdapter(Context context,ArrayList<Week> weekList){
        this.mContext = context;
        this.weekList = weekList;
    }

    @Override
    public WeekAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemweek,parent,false);
        return new WeekAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        GlideUrl glideUrl = new GlideUrl(weekList.get(position).getImage_url(), new LazyHeaders.Builder()
                .addHeader("Referer","https://www.pixiv.net/")
                .addHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.100 Safari/537.36")
                .build());
        Glide.with(mContext).load(glideUrl).into(holder.dailyView);
        holder.painter.setText(weekList.get(position).getPainter());
        holder.works.setText(weekList.get(position).getWorks());
    }

    @Override
    public int getItemCount() {
        return weekList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        CardView cardView;
        ImageView dailyView;
        TextView works;
        TextView painter;

        public ViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView;
            dailyView = (ImageView) itemView.findViewById(R.id.week_img);
            works = (TextView)itemView.findViewById(R.id.week_works);
            painter = (TextView)itemView.findViewById(R.id.week_painter);

        }
    }
}
