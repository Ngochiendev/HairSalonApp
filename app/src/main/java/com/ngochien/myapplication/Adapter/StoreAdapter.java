package com.ngochien.myapplication.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ngochien.myapplication.Activity.DatLichActivity;
import com.ngochien.myapplication.Model.StoreData;
import com.ngochien.myapplication.Model.Stylist;
import com.ngochien.myapplication.R;
import com.ngochien.myapplication.databinding.StoreItemBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class StoreAdapter extends RecyclerView.Adapter<StoreAdapter.StoreVH> {
    Context context;
    ArrayList<StoreData> list;
    public StoreListener listener;

    public StoreAdapter(Context context, ArrayList<StoreData> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public StoreAdapter.StoreVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        StoreItemBinding binding = StoreItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new StoreVH(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull StoreAdapter.StoreVH holder, @SuppressLint("RecyclerView") int position) {
        holder.onBind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class StoreVH extends RecyclerView.ViewHolder {

        StoreItemBinding binding;

        public StoreVH(StoreItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @SuppressLint("ResourceAsColor")
        public void onBind(StoreData item) {

            Picasso.with(context).load(item.image).into(binding.ivImage);
            binding.tvAdd.setText(item.address);
            binding.tvDetailAdd.setText(item.detailAddress);
            binding.tvBook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onClickBook(item);
                }
            });
        }
    }

    public interface StoreListener{
        void onClickBook(StoreData item);
    }
}
