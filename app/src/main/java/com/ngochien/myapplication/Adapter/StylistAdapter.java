package com.ngochien.myapplication.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ngochien.myapplication.Activity.DatLichActivity;
import com.ngochien.myapplication.Model.Stylist;
import com.ngochien.myapplication.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class StylistAdapter extends RecyclerView.Adapter<StylistAdapter.ViewHolder>{
    Context context;
    ArrayList<Stylist> stylists;
    public static Stylist stylistdachon;
    boolean tam = true;
    private int selectedPosition = -1;
    public StylistAdapter(Context context, ArrayList<Stylist> stylists) {
        this.context = context;
        this.stylists = stylists;
    }

    @NonNull
    @Override
    public StylistAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View itemview= inflater.inflate(R.layout.item_stylist,parent,false);
        return new ViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull StylistAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.title.setText(stylists.get(position).getTitle());
        Glide.with(context)
                .load(stylists.get(position).getImage()).circleCrop()
                .into(holder.image);
        if (selectedPosition == position) {
            holder.title.setBackgroundColor(context.getColor(R.color.primary));
            holder.title.setTextColor(context.getColor(R.color.white));
            stylistdachon = stylists.get(position);
            DatLichActivity.Stylist.setText(stylists.get(position).getTitle());
            DatLichActivity.b2 = true;
            DatLichActivity.recyclerHour.setVisibility(View.VISIBLE);
        }
        else{
            holder.title.setBackgroundColor(context.getColor(R.color.white));
            holder.title.setTextColor(context.getColor(R.color.black));
            DatLichActivity.Stylist.setText("Chọn stylist");
            stylistdachon = stylists.get(position);
            DatLichActivity.b2 = false;
            DatLichActivity.recyclerHour.setVisibility(View.GONE);
        }
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(tam){
//
//                    tam=false;
//                }
//                else{
//
//                    tam=true;
//                }
//
//            }
//        });
        holder.itemView.setOnClickListener(v -> {
            if (selectedPosition >= 0)
                notifyItemChanged(selectedPosition);
            selectedPosition = holder.getAdapterPosition();
            notifyItemChanged(selectedPosition);
            //stylistdachon = stylists.get(position);
        });
    }

    @Override
    public int getItemCount() {
        return stylists.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView title;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            title = itemView.findViewById(R.id.title);
        }
    }
}
