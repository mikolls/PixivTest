package com.example.administrator.pixivtest;

/**
 * Created by Administrator on 2018/8/21.
 */

class PixivData {

    private String pixiv_id;

    private String password;

    private String post_key;



    public String getEmail(){
        return pixiv_id;
    }
    public void setEmail(String pixiv_id){
        this.pixiv_id = pixiv_id;
    }

    public String getPassword(){
        return password;
    }
    public void setPassword(String password){
        this.password = password;
    }

    public String getPost_key(){
        return post_key;
    }
    public void setPost_key(String post_key){
        this.post_key = post_key;
    }

}
