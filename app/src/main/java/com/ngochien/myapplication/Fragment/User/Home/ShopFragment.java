package com.ngochien.myapplication.Fragment.User.Home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.ngochien.myapplication.Adapter.ShopAdapter;
import com.ngochien.myapplication.Model.ShopItem;
import com.ngochien.myapplication.databinding.FragmentShopBinding;

import java.util.ArrayList;

public class ShopFragment extends Fragment {
    private FragmentShopBinding binding;
    private ShopAdapter adapter = new ShopAdapter();
    FirebaseFirestore firestore;
    ArrayList<ShopItem> list = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentShopBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        firestore = FirebaseFirestore.getInstance();

        adapter.context = this.getContext();
        adapter.listener = new ShopAdapter.ShopItemListener() {
            @Override
            public void onClickItem(ShopItem item) {
                Intent intent = new Intent(getActivity(), ShopItemDetailActivity.class);
                intent.putExtra("shopItem", item);
                startActivity(intent);
            }
        };
        getData();
        adapter.list = list;
        binding.rvMarket.setAdapter(adapter);
    }

    private void getData() {
        list.clear();
        CollectionReference productRefs = firestore.collection("Shop");
        productRefs.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot documentSnapshots) {
                if (documentSnapshots.isEmpty()) {
                    Log.d("TAG", "onSuccess: LIST EMPTY");
                    return;
                } else {
                    for (DocumentSnapshot document : documentSnapshots) {
                        ShopItem item = new ShopItem(document.getString("image"), document.getString("price"), document.getString("des"), document.getString("rate"));
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
