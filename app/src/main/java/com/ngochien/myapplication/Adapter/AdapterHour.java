package com.ngochien.myapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.ngochien.myapplication.Activity.DatLichActivity;
import com.ngochien.myapplication.R;

import java.util.ArrayList;

public class AdapterHour extends RecyclerView.Adapter<AdapterHour.ViewHolder>{
    Context context;
    String[] Hour;
    private int selectedPosition = -1;
    public static String GioDaChon;
    public AdapterHour(Context context, String[] hour) {
        this.context = context;
        Hour = hour;
    }

    @NonNull
    @Override
    public AdapterHour.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View itemview= inflater.inflate(R.layout.item_hour,parent,false);
        return new ViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterHour.ViewHolder holder, int position) {
        holder.Hours.setText(Hour[position]);
        if (selectedPosition == position) {
            holder.itemView.setSelected(true);
            holder.itemView.setBackground(context.getResources().getDrawable(R.drawable.background_btn_da_chon));
            holder.Hours.setTextColor(ContextCompat.getColor(holder.Hours.getContext(),R.color.white));
            DatLichActivity.b3 =true;
            if(DatLichActivity.b3 && DatLichActivity.b2 && DatLichActivity.b1){
                DatLichActivity.btnSubmit.setEnabled(true);
                DatLichActivity.btnSubmit.setBackground(context.getResources().getDrawable(R.drawable.background_btn_da_chon));
                DatLichActivity.btnSubmit.setTextColor(ContextCompat.getColor(DatLichActivity.btnSubmit.getContext(),R.color.white));
            }
            if(!DatLichActivity.b1 || !DatLichActivity.b2 || !DatLichActivity.b3){
                DatLichActivity.btnSubmit.setEnabled(false);
                DatLichActivity.btnSubmit.setBackground(context.getResources().getDrawable(R.drawable.button_chon));
                DatLichActivity.btnSubmit.setTextColor(ContextCompat.getColor(DatLichActivity.btnSubmit.getContext(),R.color.black));
            }
        } else {
            holder.itemView.setSelected(false);
            holder.itemView.setBackground(context.getResources().getDrawable(R.drawable.button_chon));
            holder.Hours.setTextColor(ContextCompat.getColor(holder.Hours.getContext(),R.color.black));
            DatLichActivity.b3 = false;
        }

        holder.itemView.setOnClickListener(v -> {
            if (selectedPosition >= 0)
                notifyItemChanged(selectedPosition);
                selectedPosition = holder.getAdapterPosition();
                notifyItemChanged(selectedPosition);
                GioDaChon = Hour[position];
        });
    }

    @Override
    public int getItemCount() {
        return Hour.length;
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView Hours;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Hours = itemView.findViewById(R.id.title);
        }
    }
}
