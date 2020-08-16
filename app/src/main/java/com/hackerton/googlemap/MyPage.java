package com.hackerton.googlemap;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hackerton.googlemap.model.UserItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MyPage extends AppCompatActivity {
    private TextView nameTextView;
    private TextView emailTextView;
    private TextView add1TextView;
    private TextView add2TextView;
    private TextView scoreTextView;



    private FirebaseAuth auth;
    private FirebaseUser user;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);

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

           // 유저 닉네임 표시
        emailTextView.setText(user.getEmail());        // 파이어베이스 이메일 불러오기

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("Users");
        reference.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // 유저 주소1, 주소2, 점수 표시
                String name = snapshot.child("nickName").getValue(String.class);
                String add1 = snapshot.child("address1").getValue(String.class);
                String add2 = snapshot.child("address2").getValue(String.class);
                int score = snapshot.child("score").getValue(int.class);

                add1TextView.setText(add1);
                add2TextView.setText(add2);
                scoreTextView.setText(String.valueOf(score));
                nameTextView.setText(name);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}