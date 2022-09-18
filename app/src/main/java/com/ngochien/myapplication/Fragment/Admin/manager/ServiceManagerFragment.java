package com.ngochien.myapplication.Fragment.Admin.manager;

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
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.ngochien.myapplication.Activity.AddDichVuActivity;
import com.ngochien.myapplication.Adapter.AdapterQuanLyDichVu;
import com.ngochien.myapplication.Adapter.AdapterQuanLyStylist;
import com.ngochien.myapplication.Model.Dichvu;
import com.ngochien.myapplication.databinding.ServiceManagerFragmentBinding;
import com.ngochien.myapplication.databinding.StaffManagerFragmentBinding;

import java.util.ArrayList;

public class ServiceManagerFragment extends Fragment {

    private ServiceManagerFragmentBinding binding;
    FirebaseFirestore firestore;
    ArrayList<Dichvu> dichvus = new ArrayList<>();
    AdapterQuanLyDichVu adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = ServiceManagerFragmentBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        getData();
        binding.rvServiceManagerContent.setAdapter(adapter);

    }

    private void initView() {
        adapter = new AdapterQuanLyDichVu(getContext(), dichvus);
        firestore = FirebaseFirestore.getInstance();

        binding.cvServiceManagerAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AddDichVuActivity.class));
            }
        });

        binding.edtServiceManagerSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    hideKeyboard(requireActivity());
                    if (!binding.edtServiceManagerSearch.getText().equals(""))
                        filter(binding.edtServiceManagerSearch.getText().toString());
                }
                return false;
            }
        });
    }

    private void getData() {
        dichvus.clear();
        CollectionReference productRefs = firestore.collection("DichVu");
        productRefs.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot documentSnapshots) {
                if (documentSnapshots.isEmpty()) {
                    Log.d("TAG", "onSuccess: LIST EMPTY");
                    return;
                } else {
                    for (DocumentSnapshot document : documentSnapshots) {
                        String id = document.getId();
                        String image = document.getString("image");
                        String title = document.getString("title");
                        String des = document.getString("description");
                        String price = document.getString("price");
                        String type = document.getString("type");
                        Dichvu dichvu = new Dichvu(id, title, des, image, price, type);
                        dichvus.add(dichvu);
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

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}
