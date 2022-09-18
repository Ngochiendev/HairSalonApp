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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.ngochien.myapplication.Activity.EditDichVuActivity;
import com.ngochien.myapplication.Model.Dichvu;
import com.ngochien.myapplication.Model.QuanLyDatLich;
import com.ngochien.myapplication.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterQuanLyDichVu extends RecyclerView.Adapter<AdapterQuanLyDichVu.ViewHolder>{
    Context context;
    ArrayList<Dichvu> arrayList;
    Dialog dialog;
    public AdapterQuanLyDichVu(Context context, ArrayList<Dichvu> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }
    public void filterList(ArrayList<Dichvu> filterllist) {
        // below line is to add our filtered
        // list in our course array list.
        arrayList = filterllist;
        // below line is to notify our adapter
        // as change in recycler view data.
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public AdapterQuanLyDichVu.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View itemview= inflater.inflate(R.layout.item_dichvu_admin,parent,false);
        return new ViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterQuanLyDichVu.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.title.setText(arrayList.get(position).getTitle());
        holder.description.setText(arrayList.get(position).getDescription());
        Picasso.with(context).load(arrayList.get(position).getImage()).into(holder.image);
        if(arrayList.get(position).getPrice().equals("0")){
            holder.price.setText("Giá: FREE");
        }
        else{
            holder.price.setText("Giá: "+arrayList.get(position).getPrice()+"K");
        }
        holder.btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Delete(position,holder);
            }
        });
        holder.btnedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EditDichVuActivity.class);
                intent.putExtra("item",arrayList.get(position));
                context.startActivity(intent);
            }
        });

    }
    public void Delete(int position, AdapterQuanLyDichVu.ViewHolder holder){
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
                db.collection("DichVu").document(arrayList.get(position).getId())
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
                arrayList.remove(holder.getAdapterPosition());
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
        return arrayList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView title,description,price;
        ImageButton btndelete,btnedit;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.description);
            price = itemView.findViewById(R.id.price);
            btndelete = itemView.findViewById(R.id.Delete);
            btnedit = itemView.findViewById(R.id.Edit);
        }
    }
}
