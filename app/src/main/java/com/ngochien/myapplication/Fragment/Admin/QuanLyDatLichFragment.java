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
import android.widget.ImageView;
import android.widget.SearchView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.ngochien.myapplication.Activity.LoginActivity;
import com.ngochien.myapplication.Adapter.AdapterHour;
import com.ngochien.myapplication.Adapter.AdapterQuanLyDatLich;
import com.ngochien.myapplication.Adapter.DichvuAdapter;
import com.ngochien.myapplication.Adapter.StylistAdapter;
import com.ngochien.myapplication.Model.Dichvu;
import com.ngochien.myapplication.Model.QuanLyDatLich;
import com.ngochien.myapplication.Model.category;
import com.ngochien.myapplication.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class QuanLyDatLichFragment extends Fragment {
    RecyclerView recyclerView;
    ArrayList<QuanLyDatLich> arrayList;
    AdapterQuanLyDatLich adapterQuanLyDatLich;
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    View view;
    ImageView btnLogout;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    SearchView searchView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.admin_fragment_quan_ly_dat_lich, container, false);
        recyclerView = view.findViewById(R.id.recycler);
        btnLogout= view.findViewById(R.id.btnLogout);
        searchView = view.findViewById(R.id.searchview);
        arrayList = new ArrayList<>();
        adapterQuanLyDatLich = new AdapterQuanLyDatLich(getContext(),arrayList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(adapterQuanLyDatLich);
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
        return view;
    }
    private void filter(String text) {
        // creating a new array list to filter our data.
        ArrayList<QuanLyDatLich> filteredlist = new ArrayList<>();

        // running a for loop to compare elements.
        for (QuanLyDatLich item : arrayList) {
            if (item.getUsername().toLowerCase().contains(text.toLowerCase())) {
                filteredlist.add(item);
            }
        }
        if (filteredlist.isEmpty()) {
            // if no item is added in filtered list we are
            // displaying a toast message as no data found.
        } else {
            // at last we are passing that filtered
            // list to our adapter class.
            adapterQuanLyDatLich.filterList(filteredlist);
        }
    }
    private void getData(){
        arrayList.clear();
        CollectionReference productRefs = firestore.collection("Booking");
        productRefs.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public  void onSuccess(QuerySnapshot documentSnapshots) {
                if (documentSnapshots.isEmpty()) {
                    Log.d("TAG", "onSuccess: LIST EMPTY");
                    return;
                } else {

                    for (DocumentSnapshot document : documentSnapshots) {
                        String id           = document.getId();
                        String idUser = document.getString("iduser");
                        String Ngaydat = document.getString("Ngaydat");
                        String Giodat = document.getString("Giodat");
                        String Stylist = document.getString("Stylist");
                        String Price = document.getString("Price");
                        String State = document.getString("State");
                        String Username = document.getString("Username");
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

                        if(State.equals("Đặt lịch")){
                            QuanLyDatLich quanLyDatLich = new QuanLyDatLich(id,idUser,Ngaydat,Giodat,Stylist,Price,State,dichvus,Username);
                            arrayList.add(quanLyDatLich);
                        }
                    }
                    adapterQuanLyDatLich.notifyDataSetChanged();

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