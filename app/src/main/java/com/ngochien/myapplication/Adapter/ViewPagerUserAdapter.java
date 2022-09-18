package com.ngochien.myapplication.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.ngochien.myapplication.Fragment.User.CategoryFragment;
import com.ngochien.myapplication.Fragment.User.Home.ShopFragment;
import com.ngochien.myapplication.Fragment.User.HomeFragment;
import com.ngochien.myapplication.Fragment.User.ProfileFragment;

public class ViewPagerUserAdapter extends FragmentStatePagerAdapter {
    public ViewPagerUserAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new HomeFragment();
            case 1:
                return new CategoryFragment();
            case 2:
                return new ShopFragment();
            case 3:
                return new ProfileFragment();
            default:
                return new HomeFragment();
        }
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String Title = "";
        switch (position) {
            case 0:
                Title = "Home";
                break;
            case 1:
                Title = "Khám phá";
                break;
            case 2:
                Title = "Shop";
                break;
            case 3:
                Title = "Tài khoản";
                break;
        }
        return Title;
    }
}
