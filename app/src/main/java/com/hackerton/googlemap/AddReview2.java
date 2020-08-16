package com.hackerton.googlemap;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hackerton.googlemap.model.ReviewItem;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddReview2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_review2);
        final EditText review = (EditText) findViewById(R.id.review);

        Button submitButton = (Button)findViewById(R.id.submit);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long now = System.currentTimeMillis();
                // 현재시간을 date 변수에 저장한다.
                Date date = new Date(now);
                // 시간을 나타냇 포맷을 정한다 ( yyyy/MM/dd 같은 형태로 변형 가능 )
                SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                // nowDate 변수에 값을 저장한다.
                String formatDate = sdfNow.format(date);
                Log.d("AddReview", "current time: " + formatDate);

                String reviewString = review.getText().toString();
                GpsTracker gpsTracker = new GpsTracker(AddReview2.this);
                double latitude = gpsTracker.getLatitude();
                double longitude = gpsTracker.getLongitude();

                ReviewItem reviewItem = new ReviewItem();
                reviewItem.setUid(FirebaseAuth.getInstance().getUid());
                reviewItem.setReview(reviewString);
                reviewItem.setPhotoUrl("photo");
                reviewItem.setTime(formatDate);
                reviewItem.setScore(50);
                reviewItem.setLatitude(latitude);
                reviewItem.setLongitude(longitude);

                FirebaseDatabase reviewDatabase = FirebaseDatabase.getInstance();
                DatabaseReference reviewReference = reviewDatabase.getReference("reviews");
                reviewReference.push().setValue(reviewItem);

                startActivity(new Intent(AddReview2.this, MainActivity.class));
            }
        });
    }
}