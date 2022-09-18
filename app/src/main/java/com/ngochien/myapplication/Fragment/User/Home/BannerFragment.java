package com.ngochien.myapplication.Fragment.User.Home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
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
import com.ngochien.myapplication.Adapter.BannerAdapter;
import com.ngochien.myapplication.R;

import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator;


public class BannerFragment extends Fragment {


    View view;
    ViewPager viewPager;
    CircleIndicator circleIndicator;
    BannerAdapter bannerAdapter;
    ArrayList<String> quangcaos;
    FirebaseFirestore firestore;
    Runnable runnable;
    Handler handler;
    int currentItem;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.user_home_fragment_banner, container, false);
        init(view);
        return view;
    }
    private void init(View view){
        viewPager = view.findViewById(R.id.viewPagerBanner);
        circleIndicator = view.findViewById(R.id.Indicator);
        firestore = FirebaseFirestore.getInstance();
        quangcaos = new ArrayList<>();
        bannerAdapter = new BannerAdapter(getActivity(),quangcaos);
        viewPager.setAdapter(bannerAdapter);
        getQuangcao();

        circleIndicator.setViewPager(viewPager);
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                currentItem = viewPager.getCurrentItem();
                currentItem++;
                if(currentItem >= viewPager.getAdapter().getCount()){
                    currentItem =0;
                }
                viewPager.setCurrentItem(currentItem,true);
                handler.postDelayed(runnable,45000);
            }
        };
        handler.postDelayed(runnable,4500);
    }
    private void getQuangcao(){
        quangcaos.clear();
        CollectionReference productRefs = firestore.collection("Banner");
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

                        quangcaos.add(image);
                    }

                    bannerAdapter.notifyDataSetChanged();

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