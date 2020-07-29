package com.hackerton.googlemap.View;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.hackerton.googlemap.R;
import com.hackerton.googlemap.model.GridViewItem;

public class GridItemView extends LinearLayout {

    ImageView GridImage;
    
    public GridItemView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.gridview_item, this, true);

        GridImage = (ImageView) findViewById(R.id.ItemImageView);
    }


    public void setImage(int resId) {
        GridImage.setImageResource(resId);
    }

}
