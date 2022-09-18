package com.ngochien.myapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ngochien.myapplication.Model.Dichvu;
import com.ngochien.myapplication.R;

import java.util.ArrayList;

public class DichvuDaChonAdapter extends RecyclerView.Adapter<DichvuDaChonAdapter.ViewHolder>{
    Context context;
    ArrayList<Dichvu> dichvus;

    public DichvuDaChonAdapter(Context context, ArrayList<Dichvu> dichvus) {
        this.context = context;
        this.dichvus = dichvus;
    }

    @NonNull
    @Override
    public DichvuDaChonAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View itemview= inflater.inflate(R.layout.item_da_chon_dich_vu,parent,false);
        return new ViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull DichvuDaChonAdapter.ViewHolder holder, int position) {
        holder.title.setText(dichvus.get(position).getTitle()+"");
    }

    @Override
    public int getItemCount() {
        return dichvus.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);

        }
    }
}
