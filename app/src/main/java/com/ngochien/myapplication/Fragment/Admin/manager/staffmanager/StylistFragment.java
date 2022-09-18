package com.ngochien.myapplication.Fragment.Admin.manager.staffmanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.ngochien.myapplication.Activity.AddStylistActivity;
import com.ngochien.myapplication.Adapter.AdapterQuanLyStylist;
import com.ngochien.myapplication.Model.Stylist;
import com.ngochien.myapplication.databinding.StylistsManagerFragmentBinding;

import java.util.ArrayList;

public class StylistFragment extends Fragment {

    private StylistsManagerFragmentBinding binding;
    FirebaseFirestore firestore;
    FirebaseAuth firebaseAuth;
    ArrayList<Stylist> stylists = new ArrayList<>();
    AdapterQuanLyStylist adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = StylistsManagerFragmentBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        binding.rvStylistManagerContent.setAdapter(adapter);
        binding.cvStylistManagerAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AddStylistActivity.class));
            }
        });


    }

    private void initView(){
        adapter = new AdapterQuanLyStylist(getContext(), stylists);
        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        binding.edtStylistManagerSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    hideKeyboard(requireActivity());
                    if (!binding.edtStylistManagerSearch.getText().equals(""))
                        filter(binding.edtStylistManagerSearch.getText().toString());
                }
                return false;
            }
        });
    }

    private void getData(){
        stylists.clear();
        CollectionReference productRefs = firestore.collection("Stylists");
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
                        Stylist s = new Stylist(id,image,title);
                        stylists.add(s);
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

    private void filter(String text) {
        ArrayList<Stylist> filteredlist = new ArrayList<>();

        for (Stylist item : stylists) {
            if (item.getTitle().toLowerCase().contains(text.toLowerCase())) {
                filteredlist.add(item);
            }
        }
        if (filteredlist.isEmpty()) {
        } else {
            adapter.filterList(filteredlist);
        }
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    @Override
    public void onResume() {
        super.onResume();
        getData();
    }
}