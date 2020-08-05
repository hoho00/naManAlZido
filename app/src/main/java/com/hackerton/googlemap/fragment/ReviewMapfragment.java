package com.hackerton.googlemap.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.hackerton.googlemap.Content_Activity;
import com.hackerton.googlemap.R;

public class ReviewMapfragment extends Fragment implements OnMapReadyCallback {

    GoogleMap MyMap;
    private MapView mapView;

    public ReviewMapfragment(){

    }

    public static ReviewMapfragment newInstance() {

        Bundle args = new Bundle();

        ReviewMapfragment fragment = new ReviewMapfragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View layout = inflater.inflate(R.layout.fragment_review_map,container,false);
        mapView = (MapView) layout.findViewById(R.id.review_map);
        mapView.getMapAsync(this);

        return layout;
    }
    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onLowMemory();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//액티비티가 처음 생성될 때 실행되는 함수

        if(mapView != null)
        {
            mapView.onCreate(savedInstanceState);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        // 구글 맵 객체를 불러온다.

        LatLng seoul = new LatLng(37.52487, 126.92723);

        MarkerOptions makerOptions = new MarkerOptions();

        for(int i = 0; i < 10; i++) {
            LatLng a = new LatLng(seoul.latitude+i,seoul.longitude+i);
            makerOptions
                    .position(a)
                    .title("원하는 위치(" + a.latitude +", 경도)에 마커를 표시했습니다.");

            googleMap.addMarker(makerOptions);
            googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                @Override
                public void onInfoWindowClick(Marker marker) {
                    Intent intent = new Intent(getContext(), Content_Activity.class);
                    startActivity(intent);
                }
            });
        }
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(seoul));
    }

}
