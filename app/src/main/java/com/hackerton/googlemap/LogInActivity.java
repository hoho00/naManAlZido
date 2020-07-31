package com.hackerton.googlemap;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LogInActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    EditText mEmailText, mPasswordText;
    private Button login, register;
    boolean autoLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        Intent intent = new Intent(getIntent());
        String isMainBack;


        firebaseAuth =  FirebaseAuth.getInstance();
        login = (Button)findViewById(R.id.login_btn);
        register = (Button)findViewById(R.id.register_btn);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LogInActivity.this, RegisterActivity.class));
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        //firebaseAuth.startActivityForSignInWithProvider()
        if(user != null) {
            Toast.makeText(this, "자동 로그인", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, MainActivity.class));
        }
    }

//    public void login_btn(View view) {
//        //firebaseAuth =  FirebaseAuth.getInstance();
//        mEmailText = findViewById(R.id.email_edit);
//        mPasswordText = findViewById(R.id.password_edit);
//        String email = mEmailText.getText().toString().trim();
//        String pwd = mPasswordText.getText().toString().trim();
//        firebaseAuth.signInWithEmailAndPassword(email,pwd)
//                .addOnCompleteListener(LogInActivity.this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if(task.isSuccessful()){
//                            Intent intent = new Intent(LogInActivity.this, MainActivity.class);
//                            startActivity(intent);
//
//                        }else{
//                            Toast.makeText(LogInActivity.this,"로그인 오류",Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
//    }


    public void auto_login_btn(View view) {
        startActivity(new Intent(this, MainActivity.class));
    }
}