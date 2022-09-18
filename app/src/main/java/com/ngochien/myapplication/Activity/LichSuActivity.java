package com.ngochien.myapplication.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.ngochien.myapplication.Adapter.LichSuDatLichAdapter;
import com.ngochien.myapplication.Adapter.StoreAdapter;
import com.ngochien.myapplication.Model.Dichvu;
import com.ngochien.myapplication.Model.QuanLyDatLich;
import com.ngochien.myapplication.Model.StoreData;
import com.ngochien.myapplication.R;
import com.ngochien.myapplication.databinding.ActivityLichSuBinding;
import com.ngochien.myapplication.databinding.ActivityStoresBinding;

import java.util.ArrayList;
import java.util.HashMap;

public class LichSuActivity extends AppCompatActivity {
    private ActivityLichSuBinding binding;
    ArrayList<QuanLyDatLich> list;
    LichSuDatLichAdapter adapter;
    FirebaseFirestore firestore;
    String uid = "none";
    SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLichSuBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


        sharedPref = this.getSharedPreferences("storeChoose", Context.MODE_PRIVATE);
        String address = sharedPref.getString("storeAddress", " ");
        uid = sharedPref.getString("uid", "none");

        firestore = FirebaseFirestore.getInstance();
        list = new ArrayList<>();

        adapter = new LichSuDatLichAdapter(this, list);

        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.rvStore.setAdapter(adapter);
        getData();
    }

    private void getData() {
        list.clear();
        CollectionReference productRefs = firestore.collection("Booking");
        productRefs.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot documentSnapshots) {
                if (documentSnapshots.isEmpty()) {
                    Log.d("TAG", "onSuccess: LIST EMPTY");
                    return;
                } else {
                    for (DocumentSnapshot document : documentSnapshots) {
                        if (uid.equals(document.get("iduser")) && document.get("State").equals("Đã thanh toán")) {
                            String id = document.getId();
                            String ngayDat = document.getString("Ngaydat");
                            String stylist = document.getString("Stylist");
                            String tongTien = document.getString("Price");

                            ArrayList<HashMap<String,String>> Dichvus = (ArrayList<HashMap<String,String>>) document.get("Dichvu");
                            ArrayList<Dichvu> dichvus = new ArrayList<>();
                            dichvus.clear();
                            for(int i=0;i<Dichvus.size();i++){
                                String title =Dichvus.get(i).get("title");
                                String ids =Dichvus.get(i).get("id");
                                String description =Dichvus.get(i).get("description");
                                String image =Dichvus.get(i).get("image");
                                String price =Dichvus.get(i).get("price");
                                String type =Dichvus.get(i).get("type");
                                Dichvu dichvu = new Dichvu(ids,title,description,image,price,type);
                                dichvus.add(dichvu);
                            }

                            QuanLyDatLich data = new QuanLyDatLich(id, ngayDat, stylist, tongTien, dichvus);
                            list.add(data);
                        }
                    }
                    adapter.notifyDataSetChanged();

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("TAG", "Error");
            }
        });
    }
}