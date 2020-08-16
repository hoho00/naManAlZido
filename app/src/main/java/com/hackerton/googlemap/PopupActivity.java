package com.hackerton.googlemap;

import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hackerton.googlemap.model.ReviewItem;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class PopupActivity extends Activity {

    TextView addressText, timeText, Review;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //타이틀바 없애기
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_popup);


        imageView = findViewById(R.id.imageView1);
        addressText = findViewById(R.id.addressText1);
        timeText = findViewById(R.id.timeText1);
        Review = findViewById(R.id.Review);

        Intent intent = getIntent();

        final double latitude = intent.getExtras().getDouble("latitude");
        final double longitude = intent.getExtras().getDouble("longitude");


        FirebaseDatabase.getInstance().getReference("reviews").addValueEventListener(new ValueEventListener() {
            final MarkerOptions markerOptions = new MarkerOptions();

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    //위치가 같으면
                    if(equal(latitude,snapshot.getValue(ReviewItem.class).getLatitude())
                        && equal(longitude, snapshot.getValue(ReviewItem.class).getLongitude())){

                        String address = getCurrentAddress(latitude, longitude);
                        addressText.setText(address);

                        String t = snapshot.getValue(ReviewItem.class).getTime();
                        timeText.setText(t);

                        String review = snapshot.getValue(ReviewItem.class).getReview();
                        Review.setText(review);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

            boolean equal(double a, double b){
                if(a == b)
                    return true;
                else
                    return false;

            }
        });
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        //바깥레이어 클릭시 안닫히게
        if(event.getAction() == MotionEvent.ACTION_OUTSIDE){
            finish();
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        //안드로이드 백버튼 막기
        finish();
        return;
    }

    public String getCurrentAddress(double latitude, double longitude) {

        //지오코더... GPS를 주소로 변환
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        List<Address> addresses;

        try {

            addresses = geocoder.getFromLocation(
                    latitude,
                    longitude,
                    7);
        } catch (IOException ioException) {
            //네트워크 문제
            Toast.makeText(this, "지오코더 서비스 사용불가", Toast.LENGTH_LONG).show();
            return "지오코더 서비스 사용불가";
        } catch (IllegalArgumentException illegalArgumentException) {
            Toast.makeText(this, "잘못된 GPS 좌표", Toast.LENGTH_LONG).show();
            return "잘못된 GPS 좌표";

        }


        if (addresses == null || addresses.size() == 0) {
            Toast.makeText(this, "주소 미발견", Toast.LENGTH_LONG).show();
            return "주소 미발견";

        }

        Address address = addresses.get(0);
        return address.getAddressLine(0).toString() + "\n";

    }

}
