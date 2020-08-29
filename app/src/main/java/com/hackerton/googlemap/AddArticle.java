package com.hackerton.googlemap;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hackerton.googlemap.model.ArticleItem;

import java.util.Date;

public class AddArticle extends AppCompatActivity {

    EditText EditText_Title;
    EditText EditText_Content;
    TextView Text_Time;
    ImageButton Button_Summit;

    GpsTracker gpsTracker;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_article);

        gpsTracker = new GpsTracker(this);

        EditText_Title = (EditText) findViewById(R.id.AddArticle_title);
        EditText_Content = (EditText) findViewById(R.id.AddArticle_content);
        Text_Time = (TextView) findViewById(R.id.AddArticle_time);
        Button_Summit = (ImageButton) findViewById(R.id.AddArticle_SummitBtn);

        Text_Time.setText(new Date(System.currentTimeMillis()).toString());

        Button_Summit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArticleItem articleItem = new ArticleItem();
                articleItem.setReg_time(new Date(System.currentTimeMillis()).toString());
                articleItem.setTitle(EditText_Title.getText().toString());
                articleItem.setContent(EditText_Content.getText().toString());
                articleItem.setLatitude(gpsTracker.getLatitude());
                articleItem.setLongitude(gpsTracker.getLongitude());

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference reference = database.getReference("Article");
                reference.push().setValue(articleItem);

                finish();
            }
        });

    }

    public void back_to(View view) {
        finish();
    }
}
