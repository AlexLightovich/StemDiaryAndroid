package com.coistem.stemdiary;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class ListAdapter extends RecyclerView.Adapter {

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.listview_vknews,viewGroup,false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ((ListViewHolder) viewHolder).bindView(i);
    }

    @Override
    public int getItemCount() {
        return OurData.title.length;
    }


    private class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView itemTextView;
        private ImageView itemImageView;
        private TextView itemDateView;

        public ListViewHolder(View itemView) {
            super(itemView);
            itemTextView = (TextView) itemView.findViewById(R.id.newsText);
            itemImageView = (ImageView) itemView.findViewById(R.id.newsImage);
            itemDateView = (TextView) itemView.findViewById(R.id.newsDate);
            itemView.setOnClickListener(this);
        }
        public void bindView(int position) {
            itemTextView.setText(OurData.title[position]);
            Picasso.with(itemView.getContext()).load(OurData.imgUrls[position]).error(R.drawable.ic_example_avatar).into(itemImageView);
            itemDateView.setText(OurData.dates[position]);
        }

        public void onClick(View view) {

        }
    }

}
