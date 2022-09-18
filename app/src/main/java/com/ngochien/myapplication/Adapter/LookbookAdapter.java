package com.ngochien.myapplication.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ngochien.myapplication.Activity.ShowImageActivity;
import com.ngochien.myapplication.Model.LookBook;
import com.ngochien.myapplication.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class LookbookAdapter  extends RecyclerView.Adapter<LookbookAdapter.ViewHolder> {
    Context context;
    ArrayList<LookBook> list;

    public LookbookAdapter(Context context, ArrayList<LookBook> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public LookbookAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(parent.getContext());
        View itemview= inflater.inflate(R.layout.item_lookbook,parent,false);
        return new ViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull LookbookAdapter.ViewHolder holder, int position) {
        holder.onBind(list.get(position));
//        holder.name.setText(list.get(position).name);
//        Picasso.with(context).load(list.get(position).image).into(holder.image);
//        holder.price.setText(list.get(position).price);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView price;
        TextView name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            price = itemView.findViewById(R.id.price);
            name = itemView.findViewById(R.id.name);
        }

        public void onBind(LookBook item){
            name.setText(item.name);
            Picasso.with(context).load(item.image).into(image);
            price.setText(item.price);

            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ShowImageActivity.class);
                    intent.putExtra("item",item.image);
                    context.startActivity(intent);
                }
            });
        }
    }
}
