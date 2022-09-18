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
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.ngochien.myapplication.Activity.EditStylistActivity;
import com.ngochien.myapplication.Activity.EditVoucherActivity;
import com.ngochien.myapplication.Model.Dichvu;
import com.ngochien.myapplication.Model.StoreData;
import com.ngochien.myapplication.Model.Voucher;
import com.ngochien.myapplication.R;
import com.ngochien.myapplication.databinding.AdminItemVoucherBinding;
import com.ngochien.myapplication.databinding.AdminStoreItemBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdminVoucherAdapter extends RecyclerView.Adapter<AdminVoucherAdapter.AdminVoucherVH> {
    Context context;
    ArrayList<Voucher> list;
    public AdminVoucherListener listener;
    Dialog dialog;

    public AdminVoucherAdapter(Context context, ArrayList<Voucher> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public AdminVoucherVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AdminItemVoucherBinding binding = AdminItemVoucherBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new AdminVoucherVH(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminVoucherVH holder, @SuppressLint("RecyclerView") int position) {
        holder.onBind(list.get(position), position, holder);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class AdminVoucherVH extends RecyclerView.ViewHolder {

        AdminItemVoucherBinding binding;

        public AdminVoucherVH(AdminItemVoucherBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @SuppressLint("ResourceAsColor")
        public void onBind(Voucher item, int position, AdminVoucherVH holder) {

            binding.code.setText(item.code);
            binding.discount.setText(item.discount);
            binding.owner.setText(item.owner);
            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onClickDelete(item);
                }
            });

            binding.Edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClickEdit(item, position);
                }
            });

            binding.Delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Delete(position, holder);
                }
            });
        }
    }

    public void Delete(int position, AdminVoucherVH holder) {
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_delete);
        ImageView imageclear = dialog.findViewById(R.id.btnClear);
        Button btnyes = dialog.findViewById(R.id.btnyes);
        Button btnno = dialog.findViewById(R.id.btnno);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        btnyes.setOnClickListener(view -> {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("Vouchers").document(list.get(position).getId())
                    .delete()
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d("TAG", "DocumentSnapshot successfully deleted!");
                        }
                    });
            list.remove(holder.getAdapterPosition());
            notifyItemRemoved(holder.getAdapterPosition());
            dialog.dismiss();
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

    public void filterList(ArrayList<Voucher> filterllist) {
        list = filterllist;
        notifyDataSetChanged();
    }

    public interface AdminVoucherListener {
        void onClickDelete(Voucher item);
        void onClickEdit(Voucher item, int position);
    }
}
