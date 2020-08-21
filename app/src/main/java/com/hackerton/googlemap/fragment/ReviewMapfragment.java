package com.hackerton.googlemap.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hackerton.googlemap.PopupActivity;
import com.hackerton.googlemap.R;
import com.hackerton.googlemap.model.MapItem;
import com.hackerton.googlemap.GpsTracker;
import com.hackerton.googlemap.R;
import com.hackerton.googlemap.model.MarkerItem;
import com.hackerton.googlemap.model.ReviewItem;

public class ReviewMapfragment extends Fragment implements
        OnMapReadyCallback {

    GoogleMap MyMap;
    private MapView mapView;
    public static final int REQUEST_CODE_PERMISSIONS = 1000; // 위치정보에 권한을 요구하는 코드


    public ReviewMapfragment() {

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

        View layout = inflater.inflate(R.layout.fragment_review_map, container, false);
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

        if (mapView != null) {
            mapView.onCreate(savedInstanceState);
        }
    }


    GoogleMap.OnMarkerClickListener markerClickListener = new GoogleMap.OnMarkerClickListener() {
        @Override
        public boolean onMarkerClick(Marker marker) {
            //선택한 타겟위치
            LatLng location = marker.getPosition();

            Intent intent = new Intent(getActivity(), PopupActivity.class);

            intent.putExtra("latitude",location.latitude);
            intent.putExtra("longitude",location.longitude);

            startActivity(intent);

            return true;
        }
    };

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        // 구글 맵 객체를 불러온다.
        final MarkerOptions markerOptions = new MarkerOptions();
        FirebaseDatabase.getInstance().getReference("reviews").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    double latitude = snapshot.getValue(ReviewItem.class).getLatitude();
                    double longitude = snapshot.getValue(ReviewItem.class).getLongitude();
                    int Score = snapshot.getValue(ReviewItem.class).getScore();

                    if(Score < 100)
                        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                    else if(Score < 500)
                        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
                    else
                        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));

                    markerOptions.position(new LatLng(latitude, longitude));
                    markerOptions.title("가게 입니다.");
                    googleMap.addMarker(markerOptions);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        googleMap.setOnMarkerClickListener(markerClickListener);
        mCurrentLocation(googleMap);

    }

    public void mCurrentLocation(GoogleMap googleMap){
        GpsTracker gpsTracker = new GpsTracker(getContext());
        LatLng location = new LatLng(gpsTracker.getLatitude(), gpsTracker.getLongitude());

        MarkerOptions markerOptions = new MarkerOptions();

        BitmapDrawable bitmapdraw=(BitmapDrawable)getResources().getDrawable(R.drawable.dot);
        Bitmap b=bitmapdraw.getBitmap();
        Bitmap smallMarker = Bitmap.createScaledBitmap(b, 100, 100, false);
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(smallMarker));
        markerOptions.position(location);

        googleMap.addMarker(markerOptions);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location,14));

    }

}
