package com.hackerton.googlemap.fragment;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
import com.hackerton.googlemap.model.MarkerItem;
import com.hackerton.googlemap.model.ReviewItem;

import java.util.ArrayList;
import java.util.Date;

public class CommunityMapfragment extends Fragment implements OnMapReadyCallback {
    private MapView mapView = null;

    private View marker_root_view;
    private TextView MarkerTitle;
    private TextView MarkerTime;
    private Date Current;


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
        Current = new Date();

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


    public void setCustomMarkerView(){
        marker_root_view = LayoutInflater.from(getContext()).inflate(R.layout.marker_layout, null);
        MarkerTitle = (TextView) marker_root_view.findViewById(R.id.MarkerLayout_Title);
        MarkerTime = (TextView) marker_root_view.findViewById(R.id.MarkerLayout_Time);
    }

    private Marker addMarker(GoogleMap googleMap, ArticleItem markerItem) {


        LatLng position = new LatLng(markerItem.getLatitude(), markerItem.getLongitude());
        Date date = new Date(markerItem.getReg_time());

        int gap = Current.getHours() - date.getHours();
        if(gap < 0)
            gap = 24 + gap;
        String time = gap + "시간 전";
        String title = markerItem.getTitle();

        MarkerTitle.setText(title);
        MarkerTitle.setTextSize(10);
        MarkerTitle.setTextColor(Color.WHITE);
        MarkerTime.setText(time);
        MarkerTime.setTextSize(8);
        MarkerTime.setTextColor(Color.WHITE);

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.title(markerItem.getContent());
        markerOptions.position(position);
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(createDrawableFromView(getContext(), marker_root_view)));


        return googleMap.addMarker(markerOptions);

    }
    private Bitmap createDrawableFromView(Context context, View view) {

        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        view.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);

        return bitmap;
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {

        setCustomMarkerView();
        FirebaseDatabase.getInstance().getReference("Article").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    ArticleItem markerItem = new ArticleItem();
                    markerItem.setLatitude(snapshot.getValue(ArticleItem.class).getLatitude());
                    markerItem.setLongitude(snapshot.getValue(ArticleItem.class).getLongitude());
                    markerItem.setTitle(snapshot.getValue(ArticleItem.class).getTitle());
                    markerItem.setContent(snapshot.getValue(ArticleItem.class).getContent());
                    markerItem.setReg_time(snapshot.getValue(ArticleItem.class).getReg_time());

                    Date date = new Date(markerItem.getReg_time());

                    if(Current.getTime() - date.getTime() < 86400000) {
                        addMarker(googleMap, markerItem);
                    }
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