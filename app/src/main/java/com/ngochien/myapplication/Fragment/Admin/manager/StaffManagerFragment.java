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
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.ngochien.myapplication.Activity.AddStylistActivity;
import com.ngochien.myapplication.Adapter.AdapterQuanLyStylist;
import com.ngochien.myapplication.Fragment.Admin.manager.staffmanager.SkinnerFragment;
import com.ngochien.myapplication.Fragment.Admin.manager.staffmanager.StaffStateManager;
import com.ngochien.myapplication.Fragment.Admin.manager.staffmanager.StylistFragment;
import com.ngochien.myapplication.Model.Stylist;
import com.ngochien.myapplication.databinding.FragmentQuanLyStoresBinding;
import com.ngochien.myapplication.databinding.StaffManagerFragmentBinding;

import java.util.ArrayList;

public class StaffManagerFragment extends Fragment {

    private StaffManagerFragmentBinding binding;

    private ArrayList<Fragment> fragments = new ArrayList<>();
    private StaffStateManager staffStateManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = StaffManagerFragmentBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        staffStateManager = new StaffStateManager(getActivity().getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initPage();
        initView();
        binding.tlStaffManager.getTabAt(0).setText("Stylist");
        binding.tlStaffManager.getTabAt(1).setText("Skinner");
    }

    private void initPage() {
        fragments.add(new StylistFragment());
        fragments.add(new SkinnerFragment());
        staffStateManager.resetList(fragments);
    }

    private void initView() {
        binding.vpStaffManager.setAdapter( staffStateManager);
        binding.tlStaffManager.setupWithViewPager(binding.vpStaffManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("anhnn", "onDestroy: ");
    }
}
