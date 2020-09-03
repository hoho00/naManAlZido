package com.hackerton.googlemap;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hackerton.googlemap.Adapter.MyReviewAdapter;
import com.hackerton.googlemap.Adapter.PageAdapter;
import com.hackerton.googlemap.fragment.ReviewFragment;
import com.hackerton.googlemap.model.MapItem;
import com.hackerton.googlemap.model.ReviewItem;
import com.hackerton.googlemap.model.UserItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyPage extends AppCompatActivity {
    private CircleImageView profileImageView;
    private TextView nameTextView;
    private TextView add1TextView;
    private TextView add2TextView;
    private TextView scoreTextView;
    private Context context = this;

    private MyReviewAdapter myReviewAdapter;
    private ViewPager mViewpager;
    private FirebaseAuth auth;
    private FirebaseUser user;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);
        Intent intent = new Intent(getIntent());
        String photoURL = intent.getStringExtra("profile");
        profileImageView = (CircleImageView) findViewById(R.id.mypage_imageView);
        Picasso.with(this).load(photoURL).into(profileImageView);
        myReviewAdapter= new MyReviewAdapter(getSupportFragmentManager(),1);


        FirebaseDatabase.getInstance().getReference("reviews").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String content = snapshot.child("review").getValue(String.class);
                    String title = snapshot.child("title").getValue(String.class);
                    String uid = snapshot.child("uid").getValue(String.class);
                    String photoUrl = snapshot.child("photoUrl").getValue(String.class);
                    String time = snapshot.child("time").getValue(String.class);
                    double latitude = snapshot.child("latitude").getValue(double.class);
                    double longitude = snapshot.child("longitude").getValue(double.class);
                    int score = snapshot.getValue(ReviewItem.class).getScore();

                    if(uid.equals(user.getUid())) {
                        myReviewAdapter.addItem(new ReviewFragment(title, photoUrl));
                    }
                    mViewpager = findViewById(R.id.mypage_viewpager);
                    mViewpager.setAdapter(myReviewAdapter);
                    mViewpager.setClipToPadding(false);
                    mViewpager.setPadding(10,0,10,0);
                    mViewpager.setPageMargin(5);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        String uid = user.getUid();

        // 마이페이지 이름,이메일, 주소 표시
        nameTextView = (TextView) findViewById(R.id.mypage_name);
        add1TextView = (TextView) findViewById(R.id.mypage_add1);
        add2TextView = (TextView) findViewById(R.id.mypage_add2);
        scoreTextView =(TextView) findViewById(R.id.mypage_score);


//        Toast.makeText(this, userItem.getId(), Toast.LENGTH_SHORT).show();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("Users");
        reference.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // 유저, 주소1, 주소2, 점수 표시
                String name = snapshot.child("nickName").getValue(String.class);
                String add1 = snapshot.child("address1").getValue(String.class);
                String add2 = snapshot.child("address2").getValue(String.class);
                int score = snapshot.child("score").getValue(int.class);

                nameTextView.setText(name);
                add1TextView.setText(add1);
                add2TextView.setText(add2);
                scoreTextView.setText(String.valueOf(score));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    public void back_to_main(View view) {
        finish();
    }
}
