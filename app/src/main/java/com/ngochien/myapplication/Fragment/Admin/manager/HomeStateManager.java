package com.ngochien.myapplication.Fragment.Admin.manager;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

public class HomeStateManager extends FragmentStatePagerAdapter {
    private ArrayList<Fragment> fragments = new ArrayList<>();

    public HomeStateManager(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    public void resetList(ArrayList<Fragment> list){
        fragments.clear();
        fragments.addAll(list);
        notifyDataSetChanged();
    }
}
