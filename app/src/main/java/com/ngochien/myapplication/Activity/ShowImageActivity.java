package com.ngochien.myapplication.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.ngochien.myapplication.Model.category;
import com.ngochien.myapplication.R;
import com.squareup.picasso.Picasso;

public class ShowImageActivity extends AppCompatActivity {
    ImageView imageView;
    String image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_image);
        imageView = findViewById(R.id.image);

        intent();

        Picasso.with(getApplicationContext()).load(image).into(imageView);

    }
    private void intent(){
        Intent intent = getIntent();
        if(intent!=null) {
            if (intent.hasExtra("item")) {
                image = intent.getStringExtra("item");
            }
        }
    }
}