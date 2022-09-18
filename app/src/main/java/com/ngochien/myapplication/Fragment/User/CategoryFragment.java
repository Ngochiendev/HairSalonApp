package com.ngochien.myapplication.Fragment.User;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.ngochien.myapplication.Adapter.categoryAdapter;
import com.ngochien.myapplication.Model.category;
import com.ngochien.myapplication.R;

import java.util.ArrayList;


public class CategoryFragment extends Fragment {
    View view;
    GridView gridView;
    ArrayList<category> danhmucArrayList;
    categoryAdapter Adapter;
    FirebaseFirestore firestore;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.user_fragment_category, container, false);
        firestore = FirebaseFirestore.getInstance();
        gridView = view.findViewById(R.id.rvStore);
        danhmucArrayList = new ArrayList<>();
        Adapter=new categoryAdapter(getContext(),R.layout.item_kieu_toc,danhmucArrayList);
        gridView.setAdapter(Adapter);
        getdata();
        return view;
    }
    private void getdata(){
        danhmucArrayList.clear();
        CollectionReference productRefs = firestore.collection("Categories");
        productRefs.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public  void onSuccess(QuerySnapshot documentSnapshots) {
                if (documentSnapshots.isEmpty()) {
                    Log.d("TAG", "onSuccess: LIST EMPTY");
                    return;
                } else {
//                    String id,title,tacgia,kichthuoc,namxuatban,sotrang,image;
//                    double price,Sale;
                    for (DocumentSnapshot document : documentSnapshots) {
                        String id           = document.getId();
                        String title = document.getString("title");
                        String image = document.getString("image");
                        category danhmuc = new category(id,title,image);
                        danhmucArrayList.add(danhmuc);
                    }
                    Adapter.notifyDataSetChanged();

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