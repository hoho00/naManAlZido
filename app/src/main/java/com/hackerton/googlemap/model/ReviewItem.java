package com.hackerton.googlemap.model;

import java.util.ArrayList;

public class ReviewItem {
    private String id;
    private String review;
    private String name;
    private String photoUrl;
    private ArrayList<String> imageUrls;
    private String time;
    private int Score;
    private double latitude;
    private double longitude;

    public ReviewItem(){}

    public ReviewItem(String id, String review, String name, String photoUrl, ArrayList<String> imageUrls, String time, int score, double latitude, double longitude) {
        this.id = id;
        this.review = review;
        this.name = name;
        this.photoUrl = photoUrl;
        this.imageUrls = imageUrls;
        this.time = time;
        Score = score;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getId() {
        return id;
    }

    public String getReview() {
        return review;
    }

    public String getName() {
        return name;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public ArrayList<String> getImageUrls() {
        return imageUrls;
    }

    public String getTime() {
        return time;
    }

    public int getScore() {
        return Score;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public void setImageUrls(ArrayList<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setScore(int score) {
        Score = score;
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
