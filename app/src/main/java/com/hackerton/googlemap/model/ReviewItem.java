package com.hackerton.googlemap.model;

import java.util.ArrayList;
import java.util.Date;

public class ReviewItem {
    private String uid;
    private String review;
    private String photoUrl;
    private String time;
    private int Score;
    private double latitude;
    private double longitude;

    public ReviewItem(){}

    public ReviewItem(String uid, String review, String photoUrl, String time, int score) {
        this.uid = uid;
        this.review = review;
        this.photoUrl = photoUrl;
        this.time = time;
        Score = score;
    }

    public String getReview() {
        return review;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public int getScore() {
        return Score;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public void setScore(int score) {
        Score = score;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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
}