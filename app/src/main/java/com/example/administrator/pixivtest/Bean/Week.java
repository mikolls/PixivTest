package com.example.administrator.pixivtest.Bean;

/**
 * Created by Administrator on 2018/11/16.
 */

public class Week {
    private String image_url;

    private String works;   //作品名字

    private String painter;     //作者

    private String painterImg;      //作者头像

    public Week(){}

    public String getPainterImg() {
        return painterImg;
    }

    public void setPainterImg(String painterImg) {
        this.painterImg = painterImg;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getWorks() {
        return works;
    }

    public void setWorks(String works) {
        this.works = works;
    }

    public String getPainter() {
        return painter;
    }

    public void setPainter(String painter) {
        this.painter = painter;
    }
}
