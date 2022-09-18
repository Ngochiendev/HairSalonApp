package com.ngochien.myapplication.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.ngochien.myapplication.Fragment.Admin.QuanLyDatLichFragment;
import com.ngochien.myapplication.Fragment.Admin.QuanLyDichVuFragment;
import com.ngochien.myapplication.Fragment.Admin.QuanLyStoreFragment;
import com.ngochien.myapplication.Fragment.Admin.QuanLyStylistFragment;
import com.ngochien.myapplication.Fragment.Admin.manager.ManagerFragment;
import com.ngochien.myapplication.Fragment.User.CategoryFragment;
import com.ngochien.myapplication.Fragment.User.HomeFragment;
import com.ngochien.myapplication.Fragment.User.ProfileFragment;

public class ViewPagerAdminAdapter extends FragmentStatePagerAdapter {

    public ViewPagerAdminAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new QuanLyDatLichFragment();
            case 1:
                return new ManagerFragment();
            case 2:
                return new QuanLyStoreFragment();
//            case 3:
//                return new QuanLyStoreFragment();
            default:
                return new QuanLyDatLichFragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String Title = "";
        switch (position) {
            case 0:
                Title = "Đặt Lịch";
                break;
            case 1:
                Title = "Quản lý";
                break;
            case 2:
                Title = "Stores";
                break;
//            case 3:
//                Title = "Stores";
//                break;
        }
        return Title;
    }
}
