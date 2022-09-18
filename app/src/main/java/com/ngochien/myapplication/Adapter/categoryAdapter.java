package com.ngochien.myapplication.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.internal.InternalTokenProvider;
import com.ngochien.myapplication.Activity.ShowImageActivity;
import com.ngochien.myapplication.Model.category;
import com.ngochien.myapplication.R;

import java.util.ArrayList;

public class categoryAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private ArrayList<category> danhmucArrayList;

    public categoryAdapter(Context context, int layout, ArrayList<category> danhmucArrayList) {
        this.context = context;
        this.layout = layout;
        this.danhmucArrayList = danhmucArrayList;
    }

    @Override
    public int getCount() {
        return danhmucArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    private class ViewHorder{
        ImageView imgHinh;
        TextView txtTen;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHorder viewHorder;
        if(convertView==null)
        {
            viewHorder=new ViewHorder();
            LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(layout,null);
            viewHorder.imgHinh=convertView.findViewById(R.id.image);
            viewHorder.txtTen=convertView.findViewById(R.id.title);
            convertView.setTag(viewHorder);
        }
        else {
            viewHorder= (ViewHorder) convertView.getTag();
        }
        category danhmuc=danhmucArrayList.get(position);
        Glide.with(context)
                .load(danhmuc.getImage())
                .into(viewHorder.imgHinh);
        viewHorder.txtTen.setText(danhmuc.getTitle());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ShowImageActivity.class);
                intent.putExtra("item",danhmucArrayList.get(position).getImage());
                context.startActivity(intent);
            }
        });
        return convertView;
    }
}
