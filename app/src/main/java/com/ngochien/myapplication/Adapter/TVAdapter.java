package com.ngochien.myapplication.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;

import com.ngochien.myapplication.Activity.ShowYouTubeActivity;
import com.ngochien.myapplication.Model.TV;
import com.ngochien.myapplication.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TVAdapter extends RecyclerView.Adapter<TVAdapter.ViewHolder> {
    Context context;
    ArrayList<TV> tvs;

    public TVAdapter(Context context, ArrayList<TV> tvs) {
        this.context = context;
        this.tvs = tvs;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View itemview= inflater.inflate(R.layout.item_tv,parent,false);
        return new ViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.txtTitle.setText(tvs.get(position).getTitle());
        holder.txtday.setText(tvs.get(position).getDay());
        Picasso.with(context).load(tvs.get(position).getImage()).into(holder.image);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ShowYouTubeActivity.class);
                intent.putExtra("item",tvs.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return tvs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView txtTitle,txtday;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            txtTitle = itemView.findViewById(R.id.title);
            txtday = itemView.findViewById(R.id.day);

        }
    }
}
