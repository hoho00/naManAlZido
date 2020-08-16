package com.hackerton.googlemap.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.hackerton.googlemap.View.GridItemView;
import com.hackerton.googlemap.model.GridViewItem;

import java.util.ArrayList;

public class GridViewAdapter extends BaseAdapter {

    ArrayList<GridViewItem> items = new ArrayList<GridViewItem>();
    Context context;

    public GridViewAdapter(Context context){
        this.context = context;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public void addItem(GridViewItem a){
        items.add(a);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        GridItemView itemView = null;

        if (view == null) {
            itemView = new GridItemView(context);
        } else {
            itemView = (GridItemView) view;
        }

        GridViewItem item = items.get(i);

        itemView.setImage(item.getImage());

        return itemView;
    }
}
