package com.ngochien.myapplication.Fragment.Admin;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SearchView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.ngochien.myapplication.Activity.AddDichVuActivity;
import com.ngochien.myapplication.Activity.LoginActivity;
import com.ngochien.myapplication.Adapter.AdapterQuanLyDichVu;
import com.ngochien.myapplication.Adapter.DichvuAdapter;
import com.ngochien.myapplication.Model.Dichvu;
import com.ngochien.myapplication.Model.QuanLyDatLich;
import com.ngochien.myapplication.R;

import java.util.ArrayList;


public class QuanLyDichVuFragment extends Fragment {

    View view;
    FirebaseFirestore firestore;
    FirebaseAuth firebaseAuth;
    RecyclerView recyclerView;
    ArrayList<Dichvu> dichvus;
    AdapterQuanLyDichVu adapter;
    SearchView searchView;
    ImageButton btnAdd;
    ImageView btnLogout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.admin_fragment_quan_ly_dich_vu, container, false);
        recyclerView = view.findViewById(R.id.recycler);
        searchView = view.findViewById(R.id.searchview);
        btnLogout= view.findViewById(R.id.btnLogout);
        btnAdd = view.findViewById(R.id.Add);
        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        dichvus = new ArrayList<>();
        adapter = new AdapterQuanLyDichVu(getContext(),dichvus);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(adapter);
        getData();
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().finish();
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // inside on query text change method we are
                // calling a method to filter our recycler view.
                filter(newText);
                return false;
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AddDichVuActivity.class));
                getActivity().finish();
            }
        });
        return view;
    }
    private void filter(String text) {
        ArrayList<Dichvu> filteredlist = new ArrayList<>();

        for (Dichvu item : dichvus) {
            if (item.getTitle().toLowerCase().contains(text.toLowerCase())) {
                filteredlist.add(item);
            }
        }
        if (filteredlist.isEmpty()) {

        } else {
            adapter.filterList(filteredlist);
        }
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
                    for (DocumentSnapshot document : documentSnapshots) {
                        String id           = document.getId();
                        String image = document.getString("image");
                        String title= document.getString("title");
                        String des = document.getString("description");
                        String price = document.getString("price");
                        String type = document.getString("type");
                        Dichvu dichvu = new Dichvu(id,title,des,image,price,type);
                        dichvus.add(dichvu);
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