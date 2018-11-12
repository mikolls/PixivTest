package com.example.administrator.pixivtest.ViewParger;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Administrator on 2018/11/8.
 */

public class WeekFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        TextView view = (TextView)View.inflate(getActivity(),android.R.layout.simple_list_item_1,null);
        view.setText("这是周榜");
        return view;
    }
}
