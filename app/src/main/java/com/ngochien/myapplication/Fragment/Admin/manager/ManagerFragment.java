package com.ngochien.myapplication.Fragment.Admin.manager;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;

import com.ngochien.myapplication.Fragment.Admin.manager.voucher.VoucherManagerFragment;
import com.ngochien.myapplication.databinding.ManagerFragmentBinding;

import java.util.ArrayList;

public class ManagerFragment extends Fragment {

    private ManagerFragmentBinding binding;
    private ArrayList<Fragment> fragments = new ArrayList<>();
    private HomeStateManager homeStateManager;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("anhnn", "onCreateView: ");
        binding = ManagerFragmentBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d("anhnn", "onViewCreated: ");

        homeStateManager = new HomeStateManager(getActivity().getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        initView();
        initPage();

        binding.tlManager.getTabAt(1).setText("Nhân viên");
        binding.tlManager.getTabAt(0).setText("Dịch vụ");
        binding.tlManager.getTabAt(2).setText("Voucher");
    }

    private void initView(){
        binding.vpManager.setAdapter( homeStateManager);
        binding.tlManager.setupWithViewPager(binding.vpManager);
    }


    private void initPage(){
        fragments.add(new ServiceManagerFragment());
        fragments.add(new StaffManagerFragment());
        fragments.add(new VoucherManagerFragment());
        homeStateManager.resetList(fragments);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("anhnn", "onDestroy: ");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("anhnn", "onResume: ");
    }
}

