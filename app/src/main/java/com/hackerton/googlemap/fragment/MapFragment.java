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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hackerton.googlemap.Content_Activity;
import com.hackerton.googlemap.GpsTracker;
import com.hackerton.googlemap.R;
import com.hackerton.googlemap.model.MapItem;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    private DatabaseReference mFirebaseDatabaseReference;
    private GpsTracker gpsTracker;
    private LatLng current;
    GoogleMap MyMap;
    private MapView mapView1;
    private MapView mapView2;
    private String sendingString;

    public MapFragment(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View layout = inflater.inflate(R.layout.fragment_map,container,true);
        mapView1 = (MapView) layout.findViewById(R.id.review_map);
        mapView1.getMapAsync(this);
        mapView2 = (MapView) layout.findViewById(R.id.community_map);
        mapView2.getMapAsync(this);

        TabHost tabHost1 = (TabHost) layout.findViewById(R.id.tabHost1);
        tabHost1.setup();

        // 첫 번째 Tab. (탭 표시 텍스트:"TAB 1"), (페이지 뷰:"content1")
        TabHost.TabSpec ts1 = tabHost1.newTabSpec("Tab Spec 1");
        ts1.setContent(R.id.content1);
        ts1.setIndicator("TAB 1");
        tabHost1.addTab(ts1);

        // 두 번째 Tab. (탭 표시 텍스트:"TAB 2"), (페이지 뷰:"content2")
        TabHost.TabSpec ts2 = tabHost1.newTabSpec("Tab Spec 2");
        ts2.setContent(R.id.content2);
        ts2.setIndicator("TAB 2");
        tabHost1.addTab(ts2);

        return layout;
    }
    @Override
    public void onStart() {
        super.onStart();
        gpsTracker = new GpsTracker(getActivity());
        current = new LatLng(gpsTracker.getLatitude(), gpsTracker.getLongitude());
        mapView1.onStart();
        Bundle extra = getArguments();

        if(extra != null){
            sendingString = extra.getString("recent_review");
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView1.onStop();
    }

//    @Override
//    public void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        mapView1.onSaveInstanceState(outState);
//    }


    @Override
    public void onResume() {
        super.onResume();
        mapView1.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView1.onPause();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView1.onLowMemory();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView1.onLowMemory();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//액티비티가 처음 생성될 때 실행되는 함수

        if(mapView1 != null || mapView2 != null)
        {
            mapView1.onCreate(savedInstanceState);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        // 구글 맵 객체를 불러온다.
        MyMap = googleMap;
        final MapItem[] mapItem = {new MapItem()};

        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();


        LatLng seoul = new LatLng(37.52487, 126.92723);

        MarkerOptions makerOptions = new MarkerOptions();
        makerOptions
                .position(seoul)
                .title("원하는 위치(위도, 경도)에 마커를 표시했습니다.");

        MyMap.addMarker(makerOptions);

        MarkerOptions makerOptions2 = new MarkerOptions();
        makerOptions2
                .position(current)
                .title("원하는 위치(위도, 경도)에 마커를 표시했습니다.");
        MyMap.addMarker(makerOptions2);



        MyMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Intent intent = new Intent(getContext(), Content_Activity.class);
                //intent.putExtra("recent_review", sendingString);
                //intent.putExtra("latlng",current);
                startActivity(intent);
            }
        });
        MyMap.moveCamera(CameraUpdateFactory.newLatLng(seoul));
    }
}
