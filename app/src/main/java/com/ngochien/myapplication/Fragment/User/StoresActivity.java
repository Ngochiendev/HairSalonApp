package com.ngochien.myapplication.Fragment.User;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.ngochien.myapplication.Activity.DatLichActivity;
import com.ngochien.myapplication.Adapter.StoreAdapter;
import com.ngochien.myapplication.Model.StoreData;
import com.ngochien.myapplication.databinding.ActivityStoresBinding;

import java.util.ArrayList;


public class StoresActivity extends AppCompatActivity {
    private ActivityStoresBinding binding;
    ArrayList<StoreData> list;
    StoreAdapter adapter;
    FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStoresBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        SharedPreferences sharedPref = this.getSharedPreferences("storeChoose", Context.MODE_PRIVATE);

        firestore = FirebaseFirestore.getInstance();
        list = new ArrayList<>();

        adapter=new StoreAdapter(this,list);
        adapter.listener = new StoreAdapter.StoreListener() {
            @Override
            public void onClickBook(StoreData item) {
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("storeAddress", item.detailAddress);
                editor.apply();

                Intent intent = new Intent(getApplicationContext(), DatLichActivity.class);
                startActivity(intent);
            }
        };

        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.rvStore.setAdapter(adapter);
        getData();
    }

    private void getData(){
        list.clear();
        CollectionReference productRefs = firestore.collection("Stores");
        productRefs.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public  void onSuccess(QuerySnapshot documentSnapshots) {
                if (documentSnapshots.isEmpty()) {
                    Log.d("TAG", "onSuccess: LIST EMPTY");
                    return;
                } else {
                    for (DocumentSnapshot document : documentSnapshots) {
                        String id           = document.getId();
                        String address = document.getString("address");
                        String detailAddress = document.getString("detailAddress");
                        String avt = document.getString("avt");
                        String sdt = document.getString("sdt");
                        StoreData data = new StoreData(id,detailAddress,address,sdt,avt);
                        list.add(data);
                    }
                    adapter.notifyDataSetChanged();

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("TAG","Error");
            }
        });
    }
}