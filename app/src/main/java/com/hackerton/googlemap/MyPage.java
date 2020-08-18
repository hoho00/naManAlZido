package com.hackerton.googlemap;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
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
import com.hackerton.googlemap.model.MapItem;
import com.hackerton.googlemap.model.ReviewItem;
import com.hackerton.googlemap.model.UserItem;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MyPage extends AppCompatActivity {
    private TextView nameTextView;
    private TextView emailTextView;
    private TextView add1TextView;
    private TextView add2TextView;
    private TextView scoreTextView;
    private Context context = this;

    // 리뷰 리스트
    ArrayList<ReviewItem> reviewItemList;

    private FirebaseAuth auth;
    private FirebaseUser user;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);

        reviewItemList = new ArrayList<ReviewItem>();


        FirebaseDatabase.getInstance().getReference("reviews").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String content = snapshot.child("review").getValue(String.class);
                    String uid = snapshot.child("uid").getValue(String.class);
                    String photoUrl = snapshot.child("photoUrl").getValue(String.class);
                    String time = snapshot.child("time").getValue(String.class);
                    int score = snapshot.getValue(ReviewItem.class).getScore();

                    if(uid.equals(user.getUid())) {
                        reviewItemList.add(new ReviewItem(uid, content, photoUrl, time, score));
                    }

                }
                ListView listView = (ListView)findViewById(R.id.listView);
                final MyReviewAdapter myReviewAdapter = new MyReviewAdapter(context, reviewItemList);

                listView.setAdapter(myReviewAdapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView parent, View v, int position, long id){
//                        Toast.makeText(context, " 정보 확인 ", Toast.LENGTH_SHORT).show();

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        String uid = user.getUid();

        // 마이페이지 이름,이메일, 주소 표시
        nameTextView = (TextView) findViewById(R.id.name_text);
        emailTextView = (TextView) findViewById(R.id.email_text);
        add1TextView = (TextView) findViewById(R.id.add1_text);
        add2TextView = (TextView) findViewById(R.id.add2_text);
        scoreTextView =(TextView) findViewById(R.id.score);


//        Toast.makeText(this, userItem.getId(), Toast.LENGTH_SHORT).show();

        emailTextView.setText(user.getEmail());        // 파이어베이스 이메일 불러오기

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

}
