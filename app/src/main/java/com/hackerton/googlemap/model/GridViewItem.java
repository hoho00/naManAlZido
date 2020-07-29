package com.hackerton.googlemap.model;

public class GridViewItem {
    private int image;

    public GridViewItem(int image_source){
        this.image = image_source;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
