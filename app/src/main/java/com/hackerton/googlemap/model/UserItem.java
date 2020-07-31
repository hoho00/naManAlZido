package com.hackerton.googlemap.model;

public class UserItem {
    private String id;
    private String nickName;
    private String photoUrl;
    private String address1;
    private String address2;
    private int Score;

    public UserItem(){}

    public UserItem(String id, String nickName, String photoUrl, String address1, String address2, int score) {
        this.id = id;
        this.nickName = nickName;
        this.photoUrl = photoUrl;
        this.address1 = address1;
        this.address2 = address2;
        Score = score;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public int getScore() {
        return Score;
    }

    public void setScore(int score) {
        Score = score;
    }
}
