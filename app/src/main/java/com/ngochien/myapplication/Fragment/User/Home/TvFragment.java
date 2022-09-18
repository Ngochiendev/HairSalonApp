package com.ngochien.myapplication.Fragment.User.Home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.ngochien.myapplication.Adapter.TVAdapter;
import com.ngochien.myapplication.Model.TV;
import com.ngochien.myapplication.R;

import java.util.ArrayList;


public class TvFragment extends Fragment {

    View view;
    RecyclerView recyclerView;
    ArrayList<TV> tvs;
    TVAdapter adapter;
    FirebaseFirestore firestore;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.user_home_fragment_tv, container, false);
        firestore = FirebaseFirestore.getInstance();
        recyclerView = view.findViewById(R.id.recycler);
        tvs = new ArrayList<>();
        adapter = new TVAdapter(getContext(),tvs);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        recyclerView.setAdapter(adapter);
        getData();
        return view;
    }
    private void getData(){
        tvs.clear();
        CollectionReference productRefs = firestore.collection("Tvs");
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
                        String linkvideo = document.getString("links");
                        String day = document.getString("day");
                        TV tv = new TV(id,title,day,linkvideo,image);
                        tvs.add(tv);
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