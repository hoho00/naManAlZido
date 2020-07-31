package com.hackerton.googlemap;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.hackerton.googlemap.fragment.MapFragment;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private Context context = this;
    private FloatingActionButton fab;

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    MapFragment mapFragment;

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent(this.getIntent());
        String sendingString = intent.getStringExtra("recent_review");
        String formatdate = intent.getStringExtra("recent_date");
        if(sendingString != null) {
            Toast.makeText(context, sendingString + " success", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(MainActivity.this, Content_Activity.class);
            i.putExtra("recent_review", sendingString);
            i.putExtra("recent_date", formatdate);
            startActivity(i);
        }
        // 플롯팅 액션 버튼
        FloatingActionButton fab = findViewById(R.id.fab);
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

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();

                int id = menuItem.getItemId();
                String title = menuItem.getTitle().toString();

                if (id == R.id.account) {
                    Toast.makeText(context, title + ": 계정 정보를 확인합니다.", Toast.LENGTH_SHORT).show();
                } else if (id == R.id.setting) {
                    Toast.makeText(context, title + ": 설정 정보를 확인합니다.", Toast.LENGTH_SHORT).show();
                } else if (id == R.id.logout) {
                    Toast.makeText(context, title + ": 로그아웃 시도중", Toast.LENGTH_SHORT).show();
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
        super.onBackPressed();
        Intent intent = new Intent(this, LogInActivity.class);
        intent.putExtra("IsMainBack", 1);
        setResult(RESULT_OK);
        startActivity(new Intent(this, LogInActivity.class));
    }

    public void add_review(View view) {
        startActivity(new Intent(MainActivity.this, AddReview.class));
    }

}