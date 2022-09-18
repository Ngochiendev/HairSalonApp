package com.ngochien.myapplication.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ngochien.myapplication.Model.QuanLyDatLich;
import com.ngochien.myapplication.databinding.LichSuDatItemBinding;

import java.util.ArrayList;

public class LichSuDatLichAdapter extends RecyclerView.Adapter<LichSuDatLichAdapter.LichSuVH> {
    Context context;
    ArrayList<QuanLyDatLich> list;

    public LichSuDatLichAdapter(Context context, ArrayList<QuanLyDatLich> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public LichSuDatLichAdapter.LichSuVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LichSuDatItemBinding binding = LichSuDatItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new LichSuVH(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull LichSuDatLichAdapter.LichSuVH holder, @SuppressLint("RecyclerView") int position) {
        holder.onBind(list.get(position), holder);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class LichSuVH extends RecyclerView.ViewHolder {

        LichSuDatItemBinding binding;

        public LichSuVH(LichSuDatItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @SuppressLint("ResourceAsColor")
        public void onBind(QuanLyDatLich item, LichSuVH holder) {

            binding.ngaydat.setText(item.getNgaydat());
            binding.stylist.setText("Stylist: " + item.getStylist());
            binding.tongtien.setText("Price: " + item.getPrice());

            DichvuDaChonAdapter adapter = new DichvuDaChonAdapter(context, item.getDichvu());
            binding.listdichvu.setAdapter(adapter);
            binding.listdichvu.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
            binding.listdichvu.setAdapter(adapter);
        }
    }
}
