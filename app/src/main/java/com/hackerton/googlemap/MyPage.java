package com.hackerton.googlemap;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.hackerton.googlemap.model.UserItem;

import androidx.appcompat.app.AppCompatActivity;

public class MyPage extends AppCompatActivity {
    private TextView nameTextView;
    private TextView emailTextView;

    private FirebaseAuth auth;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        final FirebaseUser user = auth.getCurrentUser();
        UserItem userItem = new UserItem();

        // 마이페이지 이름,이메일, 주소 표시
        nameTextView = (TextView) findViewById(R.id.name_text);
        emailTextView = (TextView) findViewById(R.id.email_text);

//        Toast.makeText(this, userItem.getId(), Toast.LENGTH_SHORT).show();

        nameTextView.setText(auth.getCurrentUser().getDisplayName()) ;
        emailTextView.setText(auth.getCurrentUser().getEmail());        // 파이어베이스 이메일 불러오기

    }
}
