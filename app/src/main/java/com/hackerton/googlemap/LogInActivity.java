package com.hackerton.googlemap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LogInActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    EditText mEmailText, mPasswordText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        firebaseAuth =  FirebaseAuth.getInstance();
        //버튼 등록하기
    }

    public void login_btn(View view) {
        mEmailText = findViewById(R.id.email_edit);
        mPasswordText = findViewById(R.id.password_edit);
        String email = mEmailText.getText().toString().trim();
        String pwd = mPasswordText.getText().toString().trim();
        firebaseAuth.signInWithEmailAndPassword(email,pwd)
                .addOnCompleteListener(LogInActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Intent intent = new Intent(LogInActivity.this, MainActivity.class);
                            startActivity(intent);

                        }else{
                            Toast.makeText(LogInActivity.this,"로그인 오류",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void register_btn(View view) {
        startActivity(new Intent(LogInActivity.this, RegisterActivity.class));
        finish();
    }
}