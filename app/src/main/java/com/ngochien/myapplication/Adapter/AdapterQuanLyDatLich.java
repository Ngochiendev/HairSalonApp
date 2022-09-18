package com.ngochien.myapplication.Adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.ngochien.myapplication.Model.Dichvu;
import com.ngochien.myapplication.Model.QuanLyDatLich;
import com.ngochien.myapplication.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterQuanLyDatLich extends RecyclerView.Adapter<AdapterQuanLyDatLich.ViewHolder> {
    Context context;
    ArrayList<QuanLyDatLich> arrayList;
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    Dialog dialog;
    private LinearLayoutManager lln;
    public AdapterQuanLyDatLich(Context context, ArrayList<QuanLyDatLich> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }
    public void filterList(ArrayList<QuanLyDatLich> filterllist) {
        // below line is to add our filtered
        // list in our course array list.
        arrayList = filterllist;
        // below line is to notify our adapter
        // as change in recycler view data.
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public AdapterQuanLyDatLich.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View itemview= inflater.inflate(R.layout.item_dat_lich_admin,parent,false);
        return new ViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterQuanLyDatLich.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        firestore.collection("Users").document(arrayList.get(position).getIduser()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                       String Username = document.getString("Username");
                       String Phone = document.getString("Phone");
                       String ava = document.getString("Avatar");
                       holder.TenUser.setText(Username);
                       holder.phone.setText("SĐT: "+Phone);
                        Glide.with(context)
                                .load(ava)
                                .into(holder.image);
                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                e.printStackTrace();
            }
        });
        holder.stylist.setText("Stylist: "+arrayList.get(position).getStylist());
        holder.ngaydat.setText("Ngày đặt: "+arrayList.get(position).getNgaydat() +" "+arrayList.get(position).getGiodat());
        holder.tongdichvu.setText("Dịch vụ đã đặt: "+arrayList.get(position).getDichvu().size());
        holder.tongtien.setText("Tổng tiền: "+arrayList.get(position).getPrice() +"K");
        lln = new LinearLayoutManager(context);
        holder.listdichvu.setLayoutManager(lln);
        DichvuDaChonAdapter adapter = new DichvuDaChonAdapter(context,arrayList.get(position).getDichvu());
        holder.listdichvu.setAdapter(adapter);
        Log.d("dichvu",arrayList.get(position).getDichvu().get(0).getTitle()+"");
        holder.listdichvu.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
        holder.listdichvu.setAdapter(adapter);

        holder.check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Check(position,holder);
            }
        });

        holder.exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Delete(position,holder);
            }
        });
    }
    public void Check(int position, AdapterQuanLyDatLich.ViewHolder holder){
        firestore.collection("Booking").document(arrayList.get(position).getId())
                .update("State","Đã thanh toán")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                arrayList.remove(holder.getAdapterPosition());
                notifyItemRemoved(holder.getAdapterPosition());
                Toast.makeText(context, "Đã thanh toán", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                e.printStackTrace();
            }
        });
    }
    public void Delete(int position, AdapterQuanLyDatLich.ViewHolder holder){
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
                db.collection("Booking").document(arrayList.get(position).getId())
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
        TextView TenUser,phone,ngaydat,tongdichvu,tongtien,stylist;
        RecyclerView listdichvu;
        ImageButton check,exit;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            TenUser = itemView.findViewById(R.id.TenUser);
            phone = itemView.findViewById(R.id.phone);
            ngaydat = itemView.findViewById(R.id.ngaydat);
            tongdichvu = itemView.findViewById(R.id.tongdichvu);
            tongtien = itemView.findViewById(R.id.tongtien);
            listdichvu = itemView.findViewById(R.id.listdichvu);
            check = itemView.findViewById(R.id.check);
            exit = itemView.findViewById(R.id.exit);
            stylist= itemView.findViewById(R.id.stylist);
        }
    }
}
