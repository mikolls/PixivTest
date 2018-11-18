package com.example.administrator.pixivtest;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.pixivtest.Bean.DailyBean;
import com.example.administrator.pixivtest.ViewParger.DailyFragment;
import com.example.administrator.pixivtest.ViewParger.WeekFragment;
import com.example.administrator.pixivtest.adapter.Myadapter;
import com.example.administrator.pixivtest.store.PersistentCookieStore;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener, ViewPager.OnPageChangeListener{

    private DrawerLayout mdrawerLayout;

    private OkHttpClient.Builder builder;

    private OkHttpClient client;

    private PersistentCookieStore persistentCookieStore;

    private CookieJarImpl cookieJarImpl;

    private TextView userName;

    private CircleImageView userImg;

    private Elements selecct;

    private Element element;

    private Message message;

    private ImageView imageView;

    private String UserData;

    private String UserImg;

    //屏幕一半的宽度
    private int width;

    private RadioGroup radioGroup;
    //定义布局内的控件
    private View view;
    //自定义指示器
    private ViewPager viewPager;
    //数据源的集合
    List<Fragment> list = new ArrayList<>();

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1 :
                    //获取用户名并显示
                    String username = (String)msg.obj;
                    userName.setText(username);
                    break;
                case 2 :
                    byte[] userPicture = (byte[])msg.obj;
                    Bitmap bitmap = BitmapFactory.decodeByteArray(userPicture,0,userPicture.length);
                    userImg.setImageBitmap(bitmap);
                    break;
                case 3 :
                    UserData = msg.obj.toString();
                    getuserData();
                    break;
                case 4 :
                    UserImg = msg.obj.toString();
                    getuserImg();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        list.add(new DailyFragment());
        list.add(new WeekFragment());
        setContentView(R.layout.activity_main);
        ActivityCollector.addActivity(this);

        builder = new OkHttpClient.Builder();
        persistentCookieStore = new PersistentCookieStore(getApplicationContext());
        cookieJarImpl = new CookieJarImpl(persistentCookieStore);
        builder.cookieJar(cookieJarImpl);
        client = builder.build();

        bindCompoent();//绑定组件

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.leftmenu);
        }
        getUserName();
    }

    private void bindCompoent(){

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mdrawerLayout = (DrawerLayout)findViewById(R.id.MainLayout);
        NavigationView navigationView = (NavigationView)findViewById(R.id.nav_view);

        View headerView = navigationView.getHeaderView(0);
        userName = (TextView)headerView.findViewById(R.id.user_name);

        userImg = (CircleImageView)headerView.findViewById(R.id.user_img);

        radioGroup = (RadioGroup) findViewById(R.id.home_rg);
        view = findViewById(R.id.home_wl_view);
        viewPager = (ViewPager)findViewById(R.id.home_ViewPager);
        FragmentStatePagerAdapter adapter = new Myadapter(getSupportFragmentManager(),list);
        viewPager.setAdapter(adapter);
        radioGroup.setOnCheckedChangeListener(this);
        viewPager.addOnPageChangeListener(this);
        initVi();
    }

    private void initVi() {
        width = getResources().getDisplayMetrics().widthPixels / 2;
        //设置下划线View的长度
        FrameLayout.LayoutParams par = new FrameLayout.LayoutParams(width, ViewGroup.LayoutParams.MATCH_PARENT);
        view.setLayoutParams(par);

    }

    private void getuserData(){
        Request request = new Request.Builder()
                .removeHeader("User-Agent")
                .addHeader("Referer","https://www.pixiv.net/")
                .addHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.100 Safari/537.36")
                .url("https://www.pixiv.net/setting_profile_img.php")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseContent = response.body().string();
                Document parse = Jsoup.parse(responseContent);
                selecct = parse.select("div[class=_user-icon size-170 cover-texture]");
                element = selecct.get(0);
                String userImg = element.attr("style");
                String removeStr = "background-image: url(\'";
                UserImg = userImg.replace(removeStr,"").replace("\')","");

                message = mHandler.obtainMessage();
                message.what = 4;
                message.obj = UserImg;
                mHandler.sendMessage(message);
            }
        });
    }


    private void getuserImg(){

                Log.i("Avatar_img",UserImg);
                Request request = new Request.Builder()
                        .removeHeader("User-Agent")
                        .addHeader("Referer","https://www.pixiv.net/")
                        .addHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.100 Safari/537.36")
                        .url(UserImg)
                        .build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        byte[] Picture_img = response.body().bytes();
                        Message message = mHandler.obtainMessage();
                        message.obj = Picture_img;
                        message.what = 2;
                        mHandler.sendMessage(message);

                    }
                });

    }


    private void getUserName(){


        final Request request = new Request.Builder()
                .url("https://www.pixiv.net/")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseContent = response.body().string();

                //获取用户名
                Document parse = Jsoup.parse(responseContent);
                selecct = parse.getElementsByClass("user-name js-click-trackable-later");
                String user_name = selecct.text();
                Log.i("username:",user_name);


                //获取用户信息地址
                selecct = parse.select("a[class=_user-icon size-40 cover-texture js-click-trackable-later]");
                element = selecct.get(0);
                String userData = element.attr("href");
                String addStr = "https://www.pixiv.net";

                UserData = addStr+userData;     //用户信息界面地址
                Log.i("UserData",UserData);

/*                selecct = parse.select("ul[class=_image-items gtm-illust-recommend-zone]");
                Log.i("recommend",selecct.outerHtml());*/


                message = mHandler.obtainMessage();
                message.what = 1;
                message.obj = user_name;
                mHandler.sendMessage(message);

                message = mHandler.obtainMessage();
                message.what = 3;
                message.obj = UserData;
                mHandler.sendMessage(message);

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                mdrawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.homePage:
                Toast.makeText(this,"该功能尚未完成!",Toast.LENGTH_SHORT).show();
                break;
            case R.id.new_n:
                Toast.makeText(this,"该功能尚未完成!",Toast.LENGTH_SHORT).show();
                break;
            case R.id.search:
                Toast.makeText(this,"该功能尚未完成!",Toast.LENGTH_SHORT).show();
                break;
            default:
        }
        return true;
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        //"页面:" + position + "
        // offset偏移百分比" + positionOffset
        // pix像素" + positionOffsetPixels
        //设置下划线的属性
        //设置下划线View的长度
        FrameLayout.LayoutParams par = (FrameLayout.LayoutParams) view.getLayoutParams();
        //设置下划线距离左边的位置长度
        int left = (int) ((positionOffset + position) * width);
        par.setMargins(left, 0, 0, 0);
        view.setLayoutParams(par);
    }

    @Override
    public void onPageSelected(int position) {
        radioGroup.check(position == 0 ? R.id.home_DailyList : R.id.home_WeekList);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        viewPager.setCurrentItem(checkedId == R.id.home_DailyList ? 0 : 1);
    }
}
