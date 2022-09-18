package com.ngochien.myapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.ngochien.myapplication.Model.ShopItem;
import com.ngochien.myapplication.databinding.ShopItemItemBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.StoreVH> {

    public ArrayList<ShopItem> list = new ArrayList<>();
    public Context context;
    public ShopItemListener listener;

    @NonNull
    @Override
    public StoreVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ShopItemItemBinding binding = ShopItemItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new StoreVH(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull StoreVH holder, int position) {
        holder.onBind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class StoreVH extends RecyclerView.ViewHolder {

        ShopItemItemBinding binding;

        public StoreVH(ShopItemItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void onBind(ShopItem item) {
            Picasso.with(context).load(item.image).into(binding.ivImage);
            binding.tvDes.setText(item.des);
            binding.tvPrice.setText(item.price);
            binding.rating.setRating(Float.parseFloat(item.rate));
            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener!=null){
                        listener.onClickItem(item);
                    }
                }
            });
        }
    }

    public interface ShopItemListener{
        void onClickItem(ShopItem item);
    }
}
