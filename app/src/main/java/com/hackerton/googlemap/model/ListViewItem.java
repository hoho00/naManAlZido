package com.hackerton.googlemap.model;

public class ListViewItem {
    private int image;
    private String UserName;
    private String Content;

    public ListViewItem(int image_source, String name, String content){
        this.image = image_source;
        this.UserName = name;
        this.Content = content;
    }

    public int getImage() {
        return image;
    }

    public String getContent() {
        return Content;
    }

    public String getUserName() {
        return UserName;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public void setContent(String content) {
        Content = content;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }
}
