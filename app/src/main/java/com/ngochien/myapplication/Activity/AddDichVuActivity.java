package com.ngochien.myapplication.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.ngochien.myapplication.Model.Dichvu;
import com.ngochien.myapplication.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gun0912.tedbottompicker.TedBottomPicker;
import gun0912.tedbottompicker.TedBottomSheetDialogFragment;

public class AddDichVuActivity extends AppCompatActivity {
    ImageView imageView;
    EditText edtTitle,edtDes,edtPrice,edtType;
    ListView listtype;
    Button btnSave;
    ArrayList<String> arrayListType;
    boolean type=true;
    FirebaseStorage storage=FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    Uri file;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dich_vu);
        init();
        edtType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(type){
                    listtype.setVisibility(View.VISIBLE);
                    type = false;
                }
                else{
                    listtype.setVisibility(View.GONE);
                    type = true;
                }
            }
        });
        listtype.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                edtType.setText(arrayListType.get(position));
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Permission();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddDichvu();
                Toast.makeText(getApplicationContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),AdminActivity.class));
                finish();
            }
        });
    }
    private void Permission(){
        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                openImagePicker();
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                Toast.makeText(getApplicationContext(), "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
            }


        };
        TedPermission.with(getApplicationContext())
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .check();
    }
    private void openImagePicker(){
        TedBottomPicker.with(AddDichVuActivity.this)
                .show(new TedBottomSheetDialogFragment.OnImageSelectedListener() {
                    @Override
                    public void onImageSelected(Uri uri) {
                        // here is selected image uri
                        Bitmap bitmap= null;
                        try {
                            bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(),uri);
                            imageView.setImageBitmap(bitmap);
                            file=uri;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                });
    }
    private void init(){
        imageView = findViewById(R.id.image);
        edtTitle = findViewById(R.id.editTitle);
        edtDes = findViewById(R.id.editDes);
        edtPrice = findViewById(R.id.editPrice);
        edtType = findViewById(R.id.editType);
        listtype = findViewById(R.id.listtype);
        btnSave = findViewById(R.id.btnSave);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),AdminActivity.class));
                finish();
            }
        });
        toolbar.setNavigationIcon(R.drawable.back_white);
        getlistview();
    }
    private void getlistview(){
        arrayListType = new ArrayList<>();
        arrayListType.add("massage");
        arrayListType.add("chamda");
        arrayListType.add("uontoc");
        arrayListType.add("nhuomtoc");
        ArrayAdapter adapter= new ArrayAdapter(AddDichVuActivity.this, android.R.layout.simple_list_item_1,arrayListType);
        listtype.setAdapter(adapter);
    }
    private void AddDichvu(){
        Map<String, Object> map = new HashMap<>();
        map.put("id","");
        map.put("title",edtTitle.getText().toString());
        map.put("description",edtDes.getText().toString());
        map.put("type", edtType.getText().toString());
        map.put("price",edtPrice.getText().toString());
        firestore.collection("DichVu")
                .add(map)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("TAG", "DocumentSnapshot successfully written!");
                        UploadImage(documentReference.getId());

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w("TAG", "Error writing document", e);
            }
        });
    }
    private void UploadImage(String Dichvuid) {
        if (file != null) {
            StorageReference ref = storageRef.child("Dichvu/image/" + Dichvuid);
            ref.putFile(file)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                        }
                    });
            GetImageUrl(ref, Dichvuid);
        }
    }
    private void GetImageUrl(StorageReference ref, String Dichvuid) {
        UploadTask uploadTask = ref.putFile(file);
        uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }
                return ref.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                    String imgUrl = downloadUri.toString();
                    Map<String, Object> mapbook = new HashMap<>();
                    mapbook.put("image",imgUrl);
                    mapbook.put("id",Dichvuid);
                    firestore.collection("DichVu").document(Dichvuid).update(mapbook).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d("TAG", "DocumentSnapshot successfully updated!");
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w("TAG", "Error updating document", e);
                        }
                    });
                }
                else {
                }
            }
        });
    }

}