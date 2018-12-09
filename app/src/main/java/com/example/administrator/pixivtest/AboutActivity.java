package com.example.administrator.pixivtest;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        TextView textView = (TextView)findViewById(R.id.by);
        Typeface typeface = Typeface.createFromAsset(getAssets(),"liana.ttf");
        textView.setTypeface(typeface);
    }
}
