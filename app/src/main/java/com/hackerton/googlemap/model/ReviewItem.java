package com.hackerton.googlemap.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ReviewItem {
    private String uid;
    private String title;
    private String review;
    private String photoUrl;
    private String time;
    private int score;
    private double latitude;
    private double longitude;

    public ReviewItem(){}

    public ReviewItem(String uid, String title, String review, String photoUrl, String time, int score, double latitude, double longitude) {
        this.uid = uid;
        this.title = title;
        this.review = review;
        this.photoUrl = photoUrl;
        this.time = time;
        score = score;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", uid);
        result.put("review",review);
        result.put("photoUrl", photoUrl);
        result.put("time", time);
        result.put("Score", score);

        return result;
    }

    public Map<String, Object> remove() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", null);
        result.put("review", null);
        result.put("photoUrl", null);
        result.put("time", null);
        result.put("score", null);

        return result;
    }

    public String getReview() {
        return review;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public int getScore() {
        return score;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public void setScore(int score) {
        score = score;
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