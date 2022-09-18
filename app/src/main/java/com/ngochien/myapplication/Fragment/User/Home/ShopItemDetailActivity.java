package com.ngochien.myapplication.Fragment.User.Home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ngochien.myapplication.Model.ShopItem;
import com.ngochien.myapplication.databinding.ActivityShopItemDetailBinding;
import com.squareup.picasso.Picasso;

public class ShopItemDetailActivity extends AppCompatActivity {

    ActivityShopItemDetailBinding binding ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityShopItemDetailBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        Intent intent = getIntent();

        ShopItem shop = (ShopItem) intent.getSerializableExtra("shopItem");
        Picasso.with(this).load(shop.image).into(binding.ivShopDetailImage);
        binding.tvShopDetailPrice.setText(shop.price);
        binding.tvShopDetailName.setText(shop.des);

        binding.ivShopDetailBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
