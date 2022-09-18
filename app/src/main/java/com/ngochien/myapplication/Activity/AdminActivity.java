package com.ngochien.myapplication.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.graphics.PorterDuff;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.ngochien.myapplication.Adapter.ViewPagerAdminAdapter;
import com.ngochien.myapplication.Adapter.ViewPagerUserAdapter;
import com.ngochien.myapplication.R;

public class AdminActivity extends AppCompatActivity {
    private TabLayout tablayout;
    private ViewPager viewPager;
    private int[] tabIcons = {
            R.drawable.datlich,
            R.drawable.service,
            R.drawable.hair_dresses,
            R.drawable.store
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        tablayout=findViewById(R.id.tablayout);
        viewPager=findViewById(R.id.Viewpager);
        ViewPagerAdminAdapter viewPagerAdapter= new ViewPagerAdminAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(viewPagerAdapter);
        tablayout.setupWithViewPager(viewPager);
        setupTabIcons();
        tablayout.getTabAt(0).getIcon().setColorFilter(getResources().getColor(R.color.yellow), PorterDuff.Mode.SRC_IN);
        tablayout.getTabAt(1).getIcon().setColorFilter(getResources().getColor(R.color.noselect), PorterDuff.Mode.SRC_IN);
        tablayout.getTabAt(2).getIcon().setColorFilter(getResources().getColor(R.color.noselect), PorterDuff.Mode.SRC_IN);
//        tablayout.getTabAt(3).getIcon().setColorFilter(getResources().getColor(R.color.noselect), PorterDuff.Mode.SRC_IN);

        tablayout.getTabAt(0).setText("Đặt lịch");
        tablayout.getTabAt(1).setText("Quản lý");
//        tablayout.getTabAt(2).setText("Stylist");
        tablayout.getTabAt(2).setText("Branch");

        tablayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tab.getIcon().setColorFilter(getResources().getColor(R.color.yellow), PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.getIcon().setColorFilter(getResources().getColor(R.color.noselect), PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
    private void setupTabIcons() {
        tablayout.getTabAt(0).setIcon(tabIcons[0]);
        tablayout.getTabAt(1).setIcon(tabIcons[1]);
//        tablayout.getTabAt(2).setIcon(tabIcons[2]);
        tablayout.getTabAt(2).setIcon(tabIcons[2]);
    }
}