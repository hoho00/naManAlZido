package com.hackerton.googlemap;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.hackerton.googlemap.Adapter.PageAdapter;
import com.hackerton.googlemap.fragment.CommunityMapfragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class MainActivity extends AppCompatActivity  {

    private DrawerLayout mDrawerLayout;
    private Context context = this;

    private TextView header_nameTextView;
    private TextView header_emailTextView;

    private TextView nameTextView;
    private TextView emailTextView;

    private FirebaseAuth auth;
    private FirebaseUser user;

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    CommunityMapfragment mapFragment;

    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    // 마지막으로 뒤로가기 버튼을 눌렀던 시간 저장
    private long backKeyPressedTime = 0;
    // 첫 번째 뒤로가기 버튼을 누를때 표시
    private Toast toast;

    //floating button
    private FloatingActionButton fab;

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        Intent intent = new Intent(this.getIntent());
        String sendingString = intent.getStringExtra("recent_review");
        String formatdate = intent.getStringExtra("recent_date");

        mTabLayout = (TabLayout) findViewById(R.id.main_tablayout);
        mTabLayout.addTab(mTabLayout.newTab().setText("Tab One"));
        mTabLayout.addTab(mTabLayout.newTab().setText("Tab Two"));
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);


        // Initializing ViewPager
        mViewPager = (ViewPager) findViewById(R.id.main_viewpager);

        // Creating TabPagerAdapter adapter
        PageAdapter pagerAdapter = new PageAdapter(getSupportFragmentManager(), mTabLayout.getTabCount());
        mViewPager.setAdapter(pagerAdapter);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));

        // Set TabSelectedListener
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        // 플롯팅 액션 버튼
        fab  = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add_review(view);
            }
        });

        //startActivity(intent);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true); // 햄버거 버튼 만들기
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_foreground); //햄버거 버튼 이미지 지정

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        DatabaseReference db = FirebaseDatabase.getInstance().getReference("Users");

        NavigationView navigationView = findViewById(R.id.nav_view);
        View view = navigationView.getHeaderView(0);

        // 네비게이션바 이름, 이메일 표시
        header_nameTextView = (TextView) view.findViewById(R.id.header_name_textView);
        header_emailTextView = (TextView) view.findViewById(R.id.header_email_textView);

        header_nameTextView.setText(user.getDisplayName());   // 파이어베이스 이름 불러오기
        header_emailTextView.setText(user.getEmail());        // 파이어베이스 이메일 불러오기




        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();

                int id = menuItem.getItemId();
                String title = menuItem.getTitle().toString();

                if (id == R.id.account) {
                    Toast.makeText(context, title + ": 계정 정보를 확인합니다.", Toast.LENGTH_SHORT).show();
                    if(auth != null){
                        Toast.makeText(context, " 정보 확인 ", Toast.LENGTH_SHORT).show();

                        Intent intent1 = new Intent(MainActivity.this, MyPage.class);
                        startActivity(intent1);

                    }
                } else if (id == R.id.setting) {
                    Toast.makeText(context, title + ": 설정 정보를 확인합니다.", Toast.LENGTH_SHORT).show();
                } else if (id == R.id.logout) {
                    Toast.makeText(context, title + ": 로그아웃 되었습니다.", Toast.LENGTH_SHORT).show();
                    auth.signOut();
                    finish();
                    Intent intent3 = new Intent(MainActivity.this, LogInActivity.class);
                    startActivity(intent3);
                }

                return true;
            }
        });

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: { // 햄버거 버튼 눌렀을 때
                mDrawerLayout.openDrawer(GravityCompat.START);

                return true;
            }
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis();
            toast = Toast.makeText(this, "\'뒤로\' 버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT);
            toast.show();

            return;
        }
        // 마지막으로 뒤로가기 버튼을 눌렀던 시간에 2초를 더해 현재시간과 비교 후
        // 마지막으로 뒤로가기 버튼을 눌렀던 시간이 2초가 지나지 않았으면 종료
        // 현재 표시된 Toast 취소
        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            finish();
            toast.cancel();
            ActivityCompat.finishAffinity(this);
        }
    }

    public void add_review(View view) {
        startActivity(new Intent(MainActivity.this, AddArticle.class));
    }


}