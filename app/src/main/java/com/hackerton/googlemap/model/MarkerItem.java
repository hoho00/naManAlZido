package com.hackerton.googlemap.model;

import com.google.android.gms.maps.model.Marker;

public class MarkerItem {
    private double latitude;
    private double longitude;
    private String title;
    private String content;

    public MarkerItem(){ }

    public MarkerItem(double latitude, double longitude, String title, String content) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.title = title;
        this.content = content;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "MarkerItem{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
