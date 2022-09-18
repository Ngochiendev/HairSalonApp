package com.ngochien.myapplication.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ngochien.myapplication.Activity.ChonDichVuActivity;
import com.ngochien.myapplication.Activity.ShowYouTubeActivity;
import com.ngochien.myapplication.Model.Dichvu;
import com.ngochien.myapplication.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DichvuAdapter extends RecyclerView.Adapter<DichvuAdapter.ViewHolder>{
    Context context;
    ArrayList<Dichvu> dichvus;
    public static ArrayList<Dichvu> Dichvuchon = new ArrayList<>();
    boolean tam =true;
    public static int Price = 0;

    public DichvuAdapter(Context context, ArrayList<Dichvu> dichvus) {
        this.context = context;
        this.dichvus = dichvus;
    }

    @NonNull
    @Override
    public DichvuAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View itemview= inflater.inflate(R.layout.item_dichvu,parent,false);
        return new ViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull DichvuAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.title.setText(dichvus.get(position).getTitle());
        holder.description.setText(dichvus.get(position).getDescription());
        Picasso.with(context).load(dichvus.get(position).getImage()).into(holder.image);
        if(dichvus.get(position).getPrice().equals("0")){
            holder.price.setText("FREE");
        }
        else{
            holder.price.setText(dichvus.get(position).getPrice()+"K");
        }
        for(int i = 0;i<Dichvuchon.size();i++){
            if(dichvus.get(position).getTitle().equals(Dichvuchon.get(i).getTitle())){
                holder.btnChon.setText("Đã chọn");
                holder.btnChon.setTextColor(context.getResources().getColor(R.color.white));
                holder.btnChon.setBackground(context.getResources().getDrawable(R.drawable.background_btn_da_chon));
                tam=false;
            }
        }
        holder.btnChon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if(tam){
                        holder.btnChon.setText("Đã chọn");
                        Dichvuchon.add(dichvus.get(position));
                        Price+=Integer.parseInt(dichvus.get(position).getPrice());
                        Log.d("title",dichvus.get(position).getTitle());
                        Log.d("size",Dichvuchon.size()+"");
                        ChonDichVuActivity.btnChondichvu.setText("Chọn "+Dichvuchon.size()+" Dịch vụ");
                        holder.btnChon.setTextColor(context.getResources().getColor(R.color.white));
                        holder.btnChon.setBackground(context.getResources().getDrawable(R.drawable.background_btn_da_chon));
                        tam=false;
                    }
                    else{
                        holder.btnChon.setText("Chọn");
                        for(int i = 0;i<Dichvuchon.size();i++){
                            if(dichvus.get(position).getTitle().equals(Dichvuchon.get(i).getTitle())){
                                Dichvuchon.remove(i);
                                Price-=Integer.parseInt(dichvus.get(position).getPrice());
                                tam=true;
                            }
                        }
                        Log.d("title",dichvus.get(position).getTitle());
                        Log.d("size",Dichvuchon.size()+"");
                        ChonDichVuActivity.btnChondichvu.setText("Chọn "+Dichvuchon.size()+" Dịch vụ");
                        if(Dichvuchon.size()==0){
                            ChonDichVuActivity.btnChondichvu.setText("Chọn Dịch vụ");
                        }
                        holder.btnChon.setTextColor(context.getResources().getColor(R.color.black));
                        holder.btnChon.setBackground(context.getResources().getDrawable(R.drawable.button_chon));

                    }
                }
        });


    }

    @Override
    public int getItemCount() {
        return dichvus.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView title,description,price;
        Button btnChon;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.description);
            price = itemView.findViewById(R.id.price);
            btnChon = itemView.findViewById(R.id.btnChon);

        }
    }
}
