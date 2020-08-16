package com.hackerton.googlemap.fragment;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;

import com.google.android.gms.maps.model.LatLng;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hackerton.googlemap.GpsTracker;

import com.hackerton.googlemap.R;
import com.hackerton.googlemap.model.ArticleItem;
import com.hackerton.googlemap.model.MapItem;
import com.hackerton.googlemap.model.ReviewItem;

import java.util.ArrayList;

public class CommunityMapfragment extends Fragment implements OnMapReadyCallback {
    GoogleMap MyMap;
    private MapView mapView = null;

    public CommunityMapfragment(){

    }

    public static CommunityMapfragment newInstance() {

        Bundle args = new Bundle();

        CommunityMapfragment fragment = new CommunityMapfragment();
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

        View layout = inflater.inflate(R.layout.fragment_community_map,container,false);
        mapView = (MapView) layout.findViewById(R.id.community_map);
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

        if(mapView != null)
        {
            mapView.onCreate(savedInstanceState);
        }
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {

        final MarkerOptions markerOptions = new MarkerOptions();
        FirebaseDatabase.getInstance().getReference("Article").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    double latitude = snapshot.getValue(ArticleItem.class).getLatitude();
                    double longitude = snapshot.getValue(ArticleItem.class).getLongitude();
                    markerOptions.position(new LatLng(latitude, longitude));
                    markerOptions.title(snapshot.getValue(ArticleItem.class).getTitle());
                    markerOptions.snippet(snapshot.getValue(ArticleItem.class).getTitle());
                    googleMap.addMarker(markerOptions).showInfoWindow();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        mCurrentLocation(googleMap);
    }

    public void mCurrentLocation(GoogleMap googleMap){
        GpsTracker gpsTracker = new GpsTracker(getContext());
        LatLng location = new LatLng(gpsTracker.getLatitude(), gpsTracker.getLongitude());

        MarkerOptions markerOptions = new MarkerOptions();

        BitmapDrawable bitmapdraw=(BitmapDrawable)getResources().getDrawable(R.drawable.icon);
        Bitmap b=bitmapdraw.getBitmap();
        Bitmap smallMarker = Bitmap.createScaledBitmap(b, 100, 100, false);
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(smallMarker));
        markerOptions.position(location);

        googleMap.addMarker(markerOptions);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location,14));

    }
}