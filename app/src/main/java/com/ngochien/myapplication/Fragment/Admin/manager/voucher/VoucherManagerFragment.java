package com.ngochien.myapplication.Fragment.Admin.manager.voucher;

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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.ngochien.myapplication.Activity.AddDichVuActivity;
import com.ngochien.myapplication.Activity.AddVoucherActivity;
import com.ngochien.myapplication.Activity.EditVoucherActivity;
import com.ngochien.myapplication.Adapter.AdminVoucherAdapter;
import com.ngochien.myapplication.Model.Voucher;
import com.ngochien.myapplication.databinding.VoucherManagerFragmentBinding;

import java.util.ArrayList;

public class VoucherManagerFragment extends Fragment {

    private VoucherManagerFragmentBinding binding;
    FirebaseFirestore firestore;
    ArrayList<Voucher> vouchers = new ArrayList<>();
    AdminVoucherAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = VoucherManagerFragmentBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        getData();
        binding.rvVoucherManagerContent.setAdapter(adapter);

    }

    private void initView() {
        adapter = new AdminVoucherAdapter(getContext(), vouchers);

        adapter.listener = new AdminVoucherAdapter.AdminVoucherListener() {
            @Override
            public void onClickDelete(Voucher item) {

            }

            @Override
            public void onClickEdit(Voucher item, int position) {
                Intent intent = new Intent(requireContext(), EditVoucherActivity.class);
                intent.putExtra("item", item);
                startActivity(intent);
            }
        };
        firestore = FirebaseFirestore.getInstance();

        binding.cvVoucherManagerAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AddVoucherActivity.class));
            }
        });

        binding.edtVoucherManagerSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    hideKeyboard(requireActivity());
                    if (!binding.edtVoucherManagerSearch.getText().equals(""))
                        filter(binding.edtVoucherManagerSearch.getText().toString());
                }
                return false;
            }
        });
    }

    private void getData() {
        vouchers.clear();
        CollectionReference productRefs = firestore.collection("Vouchers");
        productRefs.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot documentSnapshots) {
                if (documentSnapshots.isEmpty()) {
                    Log.d("TAG", "onSuccess: LIST EMPTY");
                    return;
                } else {
                    for (DocumentSnapshot document : documentSnapshots) {
                        String id = document.getId();
                        String code = document.getString("code");
                        String discount = document.getString("discount");
                        String owner = document.getString("owner");
                        Voucher voucher = new Voucher(id, owner, discount, code);
                        vouchers.add(voucher);
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
        ArrayList<Voucher> filteredlist = new ArrayList<>();

        for (Voucher item : vouchers) {
            if (item.code.toLowerCase().contains(text.toLowerCase())) {
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