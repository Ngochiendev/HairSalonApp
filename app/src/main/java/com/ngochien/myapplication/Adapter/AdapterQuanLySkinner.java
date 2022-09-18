package com.ngochien.myapplication.Adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.ngochien.myapplication.Activity.EditSkinnerActivity;
import com.ngochien.myapplication.Activity.EditStylistActivity;
import com.ngochien.myapplication.Model.Stylist;
import com.ngochien.myapplication.R;

import java.util.ArrayList;

public class AdapterQuanLySkinner extends RecyclerView.Adapter<AdapterQuanLySkinner.ViewHolder>{
    Context context;
    ArrayList<Stylist> stylists;
    Dialog dialog;
    public AdapterQuanLySkinner(Context context, ArrayList<Stylist> stylists) {
        this.context = context;
        this.stylists = stylists;
    }
    public void filterList(ArrayList<Stylist> filterllist) {
        stylists = filterllist;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public AdapterQuanLySkinner.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View itemview= inflater.inflate(R.layout.item_stylist_admin,parent,false);
        return new ViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterQuanLySkinner.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.title.setText(stylists.get(position).getTitle());
        Glide.with(context)
                .load(stylists.get(position).getImage()).circleCrop()
                .into(holder.image);
        holder.btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Delete(position,holder);
            }
        });
        holder.btnedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EditSkinnerActivity.class);
                intent.putExtra("item",stylists.get(position));
                context.startActivity(intent);
            }
        });
    }
    public void Delete(int position, AdapterQuanLySkinner.ViewHolder holder){
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_delete);
        ImageView imageclear= dialog.findViewById(R.id.btnClear);
        Button btnyes = dialog.findViewById(R.id.btnyes);
        Button btnno = dialog.findViewById(R.id.btnno);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        btnyes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseFirestore db=FirebaseFirestore.getInstance();
                db.collection("Skinners").document(stylists.get(position).getId())
                        .delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d("TAG", "DocumentSnapshot successfully deleted!");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("TAG", "Error deleting document", e);
                            }
                        });
                stylists.remove(holder.getAdapterPosition());
                notifyItemRemoved(holder.getAdapterPosition());
                dialog.dismiss();
            }
        });
        btnno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        imageclear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();

    }
    @Override
    public int getItemCount() {
        return stylists.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView title;
        ImageButton btnedit,btndelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            title = itemView.findViewById(R.id.title);
            btndelete = itemView.findViewById(R.id.delete);
            btnedit = itemView.findViewById(R.id.edit);
        }
    }
}
