package com.hackerton.googlemap.model;

import java.util.Date;

public class ArticleItem {

    private String Reg_time;
    private String Title;
    private String Content;

    private double latitude;
    private double longitude;

    public ArticleItem() { }

    public ArticleItem(String reg_time, String title, String content) {
        Reg_time = reg_time;
        Title = title;
        Content = content;
    }

    public String getReg_time() {
        return Reg_time;
    }

    public void setReg_time(String reg_time) {
        Reg_time = reg_time;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "ArticleItem{" +
                "Reg_time=" + Reg_time +
                ", Title='" + Title + '\'' +
                ", Content='" + Content + '\'' +
                '}';
    }
}
