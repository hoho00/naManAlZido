package com.hackerton.googlemap;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.hackerton.googlemap.Adapter.GridViewAdapter;
import com.hackerton.googlemap.model.GridViewItem;
import com.hackerton.googlemap.model.ReviewItem;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;


public class Content_Activity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private static final int REQUEST_INVITE = 1000;

    public static final String MESSAGES_CHILD = "reviews";

    private DatabaseReference mFirebaseDatabaseReference;
    private FirebaseRecyclerAdapter<ReviewItem, ReviewViewHolder> mFirebaseAdapter;
    private RecyclerView mReviewRecyclerView;


    // Firebase 인스턴스 변수
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private FirebaseRemoteConfig mFirebaseRemoteConfig;


    // 사용자 이름과 사진
    private String mUsername;
    private String mPhotoUrl;

    private LatLng markerLocation;
    private GpsTracker gpsTracker;
    private String address;

    long now = System.currentTimeMillis();
    // 현재시간을 date 변수에 저장한다.
    Date date = new Date(now);
    // 시간을 나타냇 포맷을 정한다 ( yyyy/MM/dd 같은 형태로 변형 가능 )
    SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
    // nowDate 변수에 값을 저장한다.
    String formatDate = sdfNow.format(date);


    GridViewAdapter adapter;

    public static class ReviewViewHolder extends RecyclerView.ViewHolder {
        TextView messageTextView;
        ImageView messageImageView;
        TextView messengerTextView;
        CircleImageView messengerImageView;

        public ReviewViewHolder(View v) {
            super(v);
            messageTextView = itemView.findViewById(R.id.messageTextView);
            messageImageView = itemView.findViewById(R.id.messageImageView);
            messengerTextView = itemView.findViewById(R.id.messengerTextView);
            messengerImageView = itemView.findViewById(R.id.messengerImageView);

        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_activity);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        if (mFirebaseUser == null) {
            // 인증이 안 되었다면 인증 화면으로 이동
            startActivity(new Intent(this, LogInActivity.class));
            finish();
            return;
        } else {
            mUsername = mFirebaseUser.getDisplayName();
            if (mFirebaseUser.getPhotoUrl() != null) {
                mPhotoUrl = mFirebaseUser.getPhotoUrl().toString();
            }
        }

        mReviewRecyclerView = findViewById(R.id.message_recycler_view);

        // Firebase 리얼타임 데이터 베이스 초기화
        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();

        Query query = mFirebaseDatabaseReference.child(MESSAGES_CHILD);

        FirebaseRecyclerOptions<ReviewItem> options =
                new FirebaseRecyclerOptions.Builder<ReviewItem>()
                        .setQuery(query, ReviewItem.class)
                        .build();


        mFirebaseAdapter = new FirebaseRecyclerAdapter<ReviewItem, ReviewViewHolder>(options) {
            @Override
            protected void onBindViewHolder(ReviewViewHolder holder, final int position, ReviewItem model) {
                holder.messageTextView.setText(model.getReview());
                holder.messengerTextView.setText(model.getName() + "에 의해 작성된 리뷰입니다.");
                if (model.getPhotoUrl() == null) {
                    holder.messengerImageView.setImageDrawable(ContextCompat.getDrawable(Content_Activity.this,
                            R.drawable.ic_account_circle_black_24dp));
                } else {
                    Glide.with(Content_Activity.this)
                            .load(model.getPhotoUrl())
                            .into(holder.messengerImageView);
                }

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(Content_Activity.this, CheckMyReview.class));
                        Toast.makeText(Content_Activity.this, mFirebaseUser.getDisplayName() + "님께서 작성하신 리뷰 입니다." + (position  + 1) + "번째 리뷰 입니다.", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public ReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_review, parent, false);
                return new ReviewViewHolder(view);
            }
        };

        // 리사이클러뷰에 레이아웃 매니저와 어댑터 설정
        mReviewRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mReviewRecyclerView.setAdapter(mFirebaseAdapter);

        // Firebase Remote Config 초기화
        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();

        // Firebase Remote Config 설정
        FirebaseRemoteConfigSettings firebaseRemoteConfigSettings =
                new FirebaseRemoteConfigSettings.Builder()
                        .setDeveloperModeEnabled(true)
                        .build();

        // 인터넷 연결이 안 되었을 때 기본 값 정의
        Map<String, Object> defaultConfigMap = new HashMap<>();
        defaultConfigMap.put("message_length", 10L);

        // 설정과 기본 값 설정
        mFirebaseRemoteConfig.setConfigSettings(firebaseRemoteConfigSettings);
        mFirebaseRemoteConfig.setDefaults(defaultConfigMap);


        GridView gridView = (GridView) findViewById(R.id.ContentGridView);

        adapter = new GridViewAdapter(getApplicationContext());
        adapter.addItem(new GridViewItem(R.drawable.a));
        adapter.addItem(new GridViewItem(R.drawable.a));
        adapter.addItem(new GridViewItem(R.drawable.a));
        adapter.addItem(new GridViewItem(R.drawable.a));
        adapter.addItem(new GridViewItem(R.drawable.a));
        adapter.addItem(new GridViewItem(R.drawable.a));
        adapter.addItem(new GridViewItem(R.drawable.a));
        adapter.addItem(new GridViewItem(R.drawable.a));
        adapter.addItem(new GridViewItem(R.drawable.a));

        gridView.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // FirebaseRecyclerAdapter 실시간 쿼리 시작
        mFirebaseAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        // FirebaseRecyclerAdapter 실시간 쿼리 중지
        mFirebaseAdapter.stopListening();
    }
}
