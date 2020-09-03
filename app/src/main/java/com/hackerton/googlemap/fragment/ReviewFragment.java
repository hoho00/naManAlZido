package com.hackerton.googlemap.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.FirebaseDatabase;
import com.hackerton.googlemap.R;
import com.hackerton.googlemap.model.ReviewItem;
import com.squareup.picasso.Picasso;

public class ReviewFragment extends Fragment {

    private String review_title;
    private String photoURL;

    private ImageView thumbnail;
    private TextView Title;

    public ReviewFragment(){ }

    public ReviewFragment(String review_title, String photoURL) {
        this.review_title = review_title;
        this.photoURL = photoURL;
    }

    public ReviewFragment(int contentLayoutId, String review_title, String photoURL) {
        super(contentLayoutId);
        this.review_title = review_title;
        this.photoURL = photoURL;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View layout = inflater.inflate(R.layout.fragment_review,container,false);
        thumbnail = layout.findViewById(R.id.fragment_review_image);
        Title = layout.findViewById(R.id.fragment_review_title);

        Picasso.with(getContext()).load(photoURL).into(thumbnail);
        Title.setText(review_title);

        return layout;
    }

    public static ReviewFragment newInstance(String review, String photo) {

        Bundle args = new Bundle();

        ReviewFragment fragment = new ReviewFragment();
        args.putString("review_title",review);
        args.putString("photoURL",photo);
        fragment.setArguments(args);
        return fragment;
    }
}
