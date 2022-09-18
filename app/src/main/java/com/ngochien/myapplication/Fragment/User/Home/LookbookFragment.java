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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.ngochien.myapplication.Adapter.LookbookAdapter;
import com.ngochien.myapplication.Adapter.TVAdapter;
import com.ngochien.myapplication.Model.LookBook;
import com.ngochien.myapplication.Model.TV;
import com.ngochien.myapplication.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class LookbookFragment extends Fragment {

    View view;
    RecyclerView recyclerView;
    ArrayList<LookBook> list;
    LookbookAdapter adapter;
    FirebaseFirestore firestore;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.user_home_fragment_lookbook, container, false);
        firestore = FirebaseFirestore.getInstance();
        recyclerView = view.findViewById(R.id.recycler);
        list = new ArrayList<>();
        adapter = new LookbookAdapter(getContext(),list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(adapter);
        getData();
        return view;
    }
    private void getData(){
        list.clear();
        CollectionReference productRefs = firestore.collection("Lookbooks");
        productRefs.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot documentSnapshots) {
                if (documentSnapshots.isEmpty()) {
                    Log.d("TAG", "onSuccess: LIST EMPTY");
                    return;
                } else {
                    for (DocumentSnapshot document : documentSnapshots) {
                        LookBook item = new LookBook(document.getString("image"), document.getString("name"), document.getString("price"));
                        list.add(item);
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