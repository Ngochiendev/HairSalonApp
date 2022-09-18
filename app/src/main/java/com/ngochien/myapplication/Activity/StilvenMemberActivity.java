package com.ngochien.myapplication.Activity;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ngochien.myapplication.databinding.ActivityShopItemDetailBinding;
import com.ngochien.myapplication.databinding.ActivityStilvenMemberBinding;

public class StilvenMemberActivity extends AppCompatActivity {
    ActivityStilvenMemberBinding binding ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStilvenMemberBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.ivShopDetailBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
