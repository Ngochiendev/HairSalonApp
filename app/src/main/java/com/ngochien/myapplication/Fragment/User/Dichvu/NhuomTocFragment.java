package com.ngochien.myapplication.Fragment.User.Dichvu;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.ngochien.myapplication.Adapter.DichvuAdapter;
import com.ngochien.myapplication.Model.Dichvu;
import com.ngochien.myapplication.R;

import java.util.ArrayList;


public class NhuomTocFragment extends Fragment {

    View view;
    RecyclerView recyclerView;
    ArrayList<Dichvu> dichvus;
    DichvuAdapter adapter;
    FirebaseFirestore firestore;
    TextView txtdichvu;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.dichvu_fragment_nhuom_toc, container, false);
        firestore = FirebaseFirestore.getInstance();
        recyclerView = view.findViewById(R.id.recycler);
        txtdichvu = view.findViewById(R.id.txtDichvu);
        dichvus = new ArrayList<>();
        adapter = new DichvuAdapter(getContext(),dichvus);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        recyclerView.setAdapter(adapter);
        getData();
        return view;
    }
    private void getData(){
        dichvus.clear();
        CollectionReference productRefs = firestore.collection("DichVu");
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
                        String image = document.getString("image");
                        String title= document.getString("title");
                        String des = document.getString("description");
                        String price = document.getString("price");
                        String type = document.getString("type");
                        if(type.equals("nhuomtoc"))
                        {
                            Dichvu dichvu = new Dichvu(id,title,des,image,price,type);
                            dichvus.add(dichvu);
                        }
                    }
                    txtdichvu.setText(dichvus.size()+" dịch vụ");
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