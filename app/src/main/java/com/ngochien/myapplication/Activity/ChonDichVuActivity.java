package com.ngochien.myapplication.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.ngochien.myapplication.Adapter.DichvuAdapter;
import com.ngochien.myapplication.R;

public class ChonDichVuActivity extends AppCompatActivity {
    public static Button btnChondichvu;
    ImageButton btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chon_dich_vu);
        btnChondichvu = findViewById(R.id.btnChondichvu);
        btnBack= findViewById(R.id.btnBack);
        btnChondichvu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),DatLichActivity.class));
                finish();
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),DatLichActivity.class));
                finish();
            }
        });
       if (DichvuAdapter.Dichvuchon.size()>0){
           btnChondichvu.setText("Chọn "+DichvuAdapter.Dichvuchon.size()+" Dịch vụ");
       }
       else{
           btnChondichvu.setText("Chọn Dịch vụ");
       }
    }
}