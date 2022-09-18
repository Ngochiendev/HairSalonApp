package com.ngochien.myapplication.Activity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.ngochien.myapplication.Model.Dichvu;
import com.ngochien.myapplication.Model.Voucher;
import com.ngochien.myapplication.R;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gun0912.tedbottompicker.TedBottomPicker;
import gun0912.tedbottompicker.TedBottomSheetDialogFragment;

public class EditVoucherActivity extends AppCompatActivity {
    EditText edtTitle, edtDes, edtPrice;
    Button btnSave;
    Voucher item;
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_voucher);
        init();
        intent();
        getData();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditVoucher();
                Toast.makeText(getApplicationContext(), "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), AdminActivity.class));
                finish();
            }
        });
    }

    private void intent() {
        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra("item")) {
                item = (Voucher) intent.getSerializableExtra("item");
            }
        }
    }

    private void getData() {
        edtTitle.setText(item.code);
        edtDes.setText(item.discount);
        edtPrice.setText(item.owner);
    }

    private void init() {
        edtTitle = findViewById(R.id.editTitle);
        edtDes = findViewById(R.id.editDes);
        edtPrice = findViewById(R.id.editPrice);
        btnSave = findViewById(R.id.btnSave);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AdminActivity.class));
                finish();
            }
        });
        toolbar.setNavigationIcon(R.drawable.back_white);

    }

    private void EditVoucher() {
        Map<String, Object> map = new HashMap<>();
        map.put("code", edtTitle.getText().toString());
        map.put("discount", edtDes.getText().toString());
        map.put("owner", edtPrice.getText().toString());
        firestore.collection("Vouchers").document(item.getId()).update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("TAG", "Successfully updated!");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("TAG", "Unsuccessfully updated!");
            }
        });
    }
}