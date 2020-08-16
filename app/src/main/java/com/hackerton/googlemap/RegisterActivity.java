package com.hackerton.googlemap;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.CursorLoader;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.installations.local.PersistedInstallation;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.hackerton.googlemap.model.UserItem;
import com.squareup.picasso.Picasso;

import java.io.File;

public class RegisterActivity extends AppCompatActivity {
    private static final int CHOOSE_IMAGE = 1;
    private static final String TAG = "RegisterActivity";
    private EditText mEmailText, mPasswordText, mPasswordcheckText, mName;
    private FirebaseAuth firebaseAuth;
    private Button chooseImage;
    private Uri imgUrl;
    private ImageView imgPreview;
    private String pathUri;
    private StorageReference storageReference;


    private static final int SEARCH_ADDRESS_ACTIVITY = 10000;

    private EditText et_address, et_address2;
    private static boolean address1, address2;

    private FirebaseDatabase mDatabase;
    private FirebaseStorage mStorage;

    private StorageTask mUploadTask;

    private ProgressBar uploadProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        chooseImage = findViewById(R.id.chooseImage);
        imgPreview = findViewById(R.id.imgPreview);
        firebaseAuth = FirebaseAuth.getInstance();
        mEmailText = findViewById(R.id.email_edit);
        mPasswordText = findViewById(R.id.password_edit);
        mPasswordcheckText = findViewById(R.id.passwordcheck_edit);
        mName = findViewById(R.id.name_edit);
        et_address = findViewById(R.id.et_address);
        et_address2 = findViewById(R.id.et_address2);
        mStorage = FirebaseStorage.getInstance();
        mDatabase = FirebaseDatabase.getInstance();

        chooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFileChoose();
            }
        });
    }

    private void showFileChoose() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, CHOOSE_IMAGE);
    }


    public void register_success_btn(View view) {
        final String email = mEmailText.getText().toString().trim();
        String pwd = mPasswordText.getText().toString().trim();
        String pwdcheck = mPasswordcheckText.getText().toString().trim();


        if (pwd.equals(pwdcheck)) {
            Log.d(TAG, "등록 버튼 " + email + " , " + pwd);
            try {
                //파이어베이스에 신규계정 등록하기
                firebaseAuth.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //가입 성공시
                        if (task.isSuccessful()) {
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            final String email = user.getEmail();
                            final String uid = user.getUid();
                            final String name = mName.getText().toString().trim();
                            final String address1 = et_address.getText().toString();
                            final String address2 = et_address2.getText().toString();

                            final Uri file = Uri.fromFile(new File(pathUri)); // path

                            storageReference = mStorage.getReference()
                                    .child("usersprofileImages").child("uid/"+uid);
                            storageReference.putFile(imgUrl).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                    final Task<Uri> imageUrl = task.getResult().getStorage().getDownloadUrl();
                                    while (!imageUrl.isComplete()) ;
                                    UserItem userItem = new UserItem();
                                    userItem.setId(email);
                                    userItem.setNickName(name);
                                    userItem.setAddress1(address1);
                                    userItem.setAddress2(address2);
                                    userItem.setScore(0);
                                    userItem.setPhotoUrl(imageUrl.getResult().toString());
                                    // database에 저장
                                    mDatabase.getReference().child("Users").child(uid)
                                            .setValue(userItem);
                                }
                            });
                            //가입이 이루어져을시 가입 화면을 빠져나감.
                            Intent intent = new Intent(RegisterActivity.this, LogInActivity.class);
                            startActivity(intent);
                            finish();
                            Toast.makeText(RegisterActivity.this, "회원가입에 성공하셨습니다.", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(RegisterActivity.this, "이미 존재하는 아이디 입니다.", Toast.LENGTH_SHORT).show();
                            return;  //해당 메소드 진행을 멈추고 빠져나감.
                        }
                    }
                });
            }catch (Exception e) {
                e.printStackTrace();
            }

            //비밀번호 오류시
        } else {
            Toast.makeText(RegisterActivity.this, "비밀번호가 틀렸습니다. 다시 입력해 주세요.", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    public boolean onSupportNavigateUp() {
        onBackPressed(); // 뒤로가기 버튼이 눌렸을시
        return super.onSupportNavigateUp(); // 뒤로가기 버튼
    }

    public void register_address1_btn(View view) {
        address1 = true;
        address2 = false;
        Intent intent = new Intent(this, WebViewActivity.class);
        startActivityForResult(intent, SEARCH_ADDRESS_ACTIVITY);
    }

    public void register_address2_btn(View view) {
        address1 = false;
        address2 = true;
        Intent intent = new Intent(this, WebViewActivity.class);
        startActivityForResult(intent, SEARCH_ADDRESS_ACTIVITY);
    }

    public void onActivityResult(int requestCode, int resultCode,  @Nullable Intent intent){
        super.onActivityResult(requestCode, resultCode, intent);
        switch(requestCode){
            case SEARCH_ADDRESS_ACTIVITY:
                if(resultCode == RESULT_OK){
                    String data = intent.getExtras().getString("data");
                    if (data != null)
                        if(address1 && !address2) {
                            et_address.setText(data);
                        }
                        else if(!address1 && address2) {
                            et_address2.setText(data);
                        }
                }
                break;
            case CHOOSE_IMAGE:
                if(intent != null && intent.getData() != null) {
                    imgUrl = intent.getData();
                    pathUri = getPath(intent.getData());
                    Picasso.with(this).load(imgUrl).into(imgPreview);
                }
                break;
        }
    }

    public String getPath(Uri uri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader cursorLoader = new CursorLoader(this, uri, proj, null, null, null);
        Cursor cursor = cursorLoader.loadInBackground();
        int index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(index);
    }

}