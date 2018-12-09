package com.example.administrator.pixivtest.adapter;

import android.content.Context;
import android.content.Intent;
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
import com.example.administrator.pixivtest.DailyActivity;
import com.example.administrator.pixivtest.MyApplication;
import com.example.administrator.pixivtest.R;
import com.example.administrator.pixivtest.Utils.GlideCircleTransform;
import com.example.administrator.pixivtest.Utils.GlideUtils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2018/11/10.
 */

public class DailyAdapter extends RecyclerView.Adapter<DailyAdapter.ViewHolder> {

    private OnItemClickListener mItemClickListener;

    private Context mContext;

    private ArrayList<Daily> dailyList ;

    public DailyAdapter(Context context,ArrayList<Daily> dailyList){
        this.mContext = context;
        this.dailyList = dailyList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null){
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.itemdaily,parent,false);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Daily daily = dailyList.get(position);
                Intent intent = new Intent(mContext, DailyActivity.class);
                intent.putExtra(DailyActivity.Daily_img,daily.getImage_url());
                intent.putExtra(DailyActivity.Daily_title,daily.getWorks());
                intent.putExtra(DailyActivity.Daily_painter,daily.getPainter());
                intent.putExtra(DailyActivity.Daily_painterImg,daily.getPainterImg());
                intent.putExtra(DailyActivity.largeImg,daily.getLargeImg());
                mContext.startActivity(intent);
            }
        });

        GlideUtils.loadImg(dailyList.get(position).getImage_url(),holder.dailyView);
        GlideUtils.loadCircleImg(dailyList.get(position).getPainterImg(),holder.painterImg);

        holder.painter.setText(dailyList.get(position).getPainter());
        holder.works.setText(dailyList.get(position).getWorks());
    }

    @Override
    public int getItemCount() {
        return dailyList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        CardView cardView;
        ImageView dailyView;
        ImageView painterImg;
        TextView works;
        TextView painter;

        public ViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView;
            dailyView = (ImageView) itemView.findViewById(R.id.daily_img);
            painterImg = (ImageView)itemView.findViewById(R.id.daily_painterImg);
            works = (TextView)itemView.findViewById(R.id.works);
            painter = (TextView)itemView.findViewById(R.id.painter);

        }
    }
    public interface OnItemClickListener{
        void onItemClick(int position);
    }
}
