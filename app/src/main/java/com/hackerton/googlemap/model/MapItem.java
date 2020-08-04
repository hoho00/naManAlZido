package com.hackerton.googlemap.model;

import com.google.android.gms.maps.model.LatLng;

public class MapItem {
    private String address;
    private LatLng latLng;

    public MapItem(){}

    public MapItem(String address, LatLng latLng) {
        this.address = address;
        this.latLng = latLng;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }
}
