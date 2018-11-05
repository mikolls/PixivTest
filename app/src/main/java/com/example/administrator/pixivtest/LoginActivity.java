package com.example.administrator.pixivtest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.administrator.pixivtest.store.CookieStore;
import com.example.administrator.pixivtest.store.PersistentCookieStore;
import com.google.gson.Gson;

import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Cookie;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity{

    private EditText emailText;

    private  EditText passwordText;

    private CookieJarImpl cookieJarImpl;

    private OkHttpClient.Builder builder;

    private OkHttpClient client;

    private PersistentCookieStore persistentCookieStore;

    private SharedPreferences sharedPreferences;

    private SharedPreferences.Editor editor;

    private CheckBox rememberPwd;   //记住密码



    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            //进行登陆操作
            switch (msg.what){
                case 1:
                    loginPixiv(msg.obj.toString());
                    break;
            }
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ActivityCollector.addActivity(this);        //将当前页面添加到activity管理器中


        builder = new OkHttpClient.Builder();
        persistentCookieStore = new PersistentCookieStore(MyApplication.getContext());
        cookieJarImpl = new CookieJarImpl(persistentCookieStore);
        builder.cookieJar(cookieJarImpl);

        client = builder.build();



        /*CookieStore cookieStore = cookieJarImpl.getCookieStore();

        List<Cookie> cookieList = persistentCookieStore.getCookies();
        //获取cookie并打印
        for (int a = 0; a < cookieList.size();a++){
            Log.i("cookies",cookieList.get(a).toString());
        }*/

            bindCompoent();//绑定组件
    }

    private void loginPixiv(String post_key){
        //登陆操作
        FormBody formBody = new FormBody.Builder()
                .add("pixiv_id",emailText.getText().toString())
//                .add("captcha","")
//                .add("g_recaptcha_response","")
                .add("password",passwordText.getText().toString())
                .add("post_key",post_key)
//                .add("source","pc")
//                .add("ref","wwwtop_accounts_index")
                .add("return_to","https://www.pixiv.net/")
                .build();


        Request request = new Request.Builder()
                .url("https://accounts.pixiv.net/api/login?lang=en")
                .post(formBody)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseContent = response.body().string().toString();
                com.alibaba.fastjson.JSONObject responseJson = JSON.parseObject(responseContent);
                com.alibaba.fastjson.JSONObject responseJsonBody = JSON.parseObject(responseJson.get("body").toString());
                //根据返回的json判断是否验证成功
                if (responseJsonBody.containsKey("success")) {
                    Log.i("Pixiv","onResponse" + "登录成功");
                    List<Cookie> list = persistentCookieStore.getCookies();
                    for (Cookie i:list){
                        Log.i("cookie:",i.toString());
                    }

                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);
                } else {

                }
                Log.i("Pixiv", "onResponse: " + responseContent);
            }
        });
    }

    private void bindCompoent(){



        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        emailText = (EditText)findViewById(R.id.email);//账号

        passwordText = (EditText)findViewById(R.id.password);//密码
        passwordText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        rememberPwd = (CheckBox)findViewById(R.id.rememberPwd);//记住密码
        Button login = (Button)findViewById(R.id.login);//登录
        boolean isRemember_1 = sharedPreferences.getBoolean("remember_password",false);
        if (isRemember_1){
            String email = sharedPreferences.getString("email","");
            String password = sharedPreferences.getString("password","");
            emailText.setText(email);
            passwordText.setText(password);
            rememberPwd.setChecked(true);
        }

/*        boolean isRemember_2 = sharedPreferences.getBoolean("remember_autoLogin",false);
        if (isRemember_2){
            autoLogin.setChecked(true);
        }*/

        Button clearCookie = (Button)findViewById(R.id.clearCookie);
        clearCookie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                persistentCookieStore.removeAll();
            }
        });



        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailText.getText().toString();
                String password = passwordText.getText().toString();
                editor = sharedPreferences.edit();
                if (rememberPwd.isChecked()){
                    editor.putBoolean("remember_password",true);
                    editor.putString("email",email);
                    editor.putString("password",password);
                }else {
                    editor.clear();
                }
                editor.apply();

                getPost_key();
            }
        });
    }


    private void getPost_key() {

        //获取post_key
                    Request request = new Request.Builder()
                            .url("https://accounts.pixiv.net/login?lang=zh&source=pc&view_type=page&ref=wwwtop_accounts_index")     //登录页面
                            .build();

                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {

                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            String resp = response.body().string();
                            Document parse = Jsoup.parse(resp);
                            Elements select = parse.select("input[name=post_key]");
                            Element element = select.get(0);
                            String post_key = element.attr("value");
                            Message msg = mHandler.obtainMessage();
                            msg.what = 1;
                            msg.obj = post_key;
                            Log.i("post_key:  ",post_key);
                            mHandler.sendMessage(msg);
                        }
                    });
            }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
        persistentCookieStore.removeAll();
    }
    private void getIndex(){
        new Thread(new Runnable() {
            @Override
            public void run() {

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
                            Toast.makeText(LoginActivity.this,"登录信息失效！请重新登陆！",Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            }
        }).start();
    }

}

