package com.coistem.stemdiary;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class ImageListAdapter extends SimpleAdapter {
    private Context mContext;
    public LayoutInflater inflater = null;

    public ImageListAdapter(Context context,
                           List<? extends Map<String, ?>> data, int resource, String[] from,
                           int[] to) {
        super(context, data, resource, from, to);
        mContext = context;
        inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if (convertView == null)
            vi = inflater.inflate(R.layout.listview_vknews, null);

        HashMap<String, Object> data = (HashMap<String, Object>) getItem(position);

        Picasso.with(mContext).load((String) data.get("Image")).into((ImageView) vi.findViewById(R.id.newsImage));
        TextView viewById = vi.findViewById(R.id.newsText);
        viewById.setText(data.get("Text").toString());

        return vi;
    }

}