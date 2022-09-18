package com.ngochien.myapplication.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ngochien.myapplication.ActivityOTP.SendOTPActivity;
import com.ngochien.myapplication.Adapter.ViewPagerUserAdapter;
import com.ngochien.myapplication.R;

public class UserActivity extends AppCompatActivity {
    private TabLayout tablayout;
    private ViewPager viewPager;
    private int[] tabIcons = {
           R.drawable.home,
            R.drawable.category,
            R.drawable.store,
            R.drawable.user1
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        tablayout=findViewById(R.id.tablayout);
        viewPager=findViewById(R.id.Viewpager);
        ViewPagerUserAdapter viewPagerAdapter= new ViewPagerUserAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(viewPagerAdapter);
        tablayout.setupWithViewPager(viewPager);
        setupTabIcons();
        tablayout.getTabAt(0).getIcon().setColorFilter(getResources().getColor(R.color.yellow), PorterDuff.Mode.SRC_IN);
        tablayout.getTabAt(1).getIcon().setColorFilter(getResources().getColor(R.color.noselect), PorterDuff.Mode.SRC_IN);
        tablayout.getTabAt(2).getIcon().setColorFilter(getResources().getColor(R.color.noselect), PorterDuff.Mode.SRC_IN);
        tablayout.getTabAt(3).getIcon().setColorFilter(getResources().getColor(R.color.noselect), PorterDuff.Mode.SRC_IN);

        tablayout.getTabAt(0).setText("Home");
        tablayout.getTabAt(1).setText("Khám phá");
        tablayout.getTabAt(2).setText("Shop");
        tablayout.getTabAt(3).setText("Tài khoản");

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
        tablayout.getTabAt(2).setIcon(tabIcons[2]);
        tablayout.getTabAt(3).setIcon(tabIcons[3]);
    }
}