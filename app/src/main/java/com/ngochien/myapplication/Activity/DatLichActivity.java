package com.ngochien.myapplication.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.ngochien.myapplication.Adapter.AdapterHour;
import com.ngochien.myapplication.Adapter.AdapterQuanLyDatLich;
import com.ngochien.myapplication.Adapter.DichvuAdapter;
import com.ngochien.myapplication.Adapter.DichvuDaChonAdapter;
import com.ngochien.myapplication.Adapter.StylistAdapter;
import com.ngochien.myapplication.Fragment.User.HomeFragment;
import com.ngochien.myapplication.Model.Dichvu;
import com.ngochien.myapplication.Model.StoreData;
import com.ngochien.myapplication.Model.Stylist;
import com.ngochien.myapplication.Model.User;
import com.ngochien.myapplication.Model.Voucher;
import com.ngochien.myapplication.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class DatLichActivity extends AppCompatActivity {

    boolean hasVoucher = false;

    final Calendar myCalendar = Calendar.getInstance();
    LinearLayout linnearchondichvu, linearChonStylist, linearchonngaygio;
    ImageView imageShowStylist, ivBack;
    Button btnChondichvu;
    public static Button btnSubmit;
    RecyclerView listViewchondichvu, recyclerStylist;
    public static RecyclerView recyclerHour;
    DichvuDaChonAdapter adapter;
    AppCompatEditText edtVoucher;
    AppCompatTextView totalPrice;
    AppCompatImageView ivCheckVoucher;
    StylistAdapter stylistAdapter;
    ArrayList<Stylist> stylists;
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
    String discount = "0";
    int finalPrice = 0;
    Voucher voucher;
    String uid = "none";
    SharedPreferences sharedPref;
    boolean stylist = true;
    public static TextView Stylist;
    TextView ChonHour, tvAddress;
    public static boolean b1 = false;
    public static boolean b2 = false;
    public static boolean b3 = false;
    AdapterHour adapterHour;
    String[] Hours = {"9h00", "9h40", "10h00", "10h40", "11h00", "11h40", "12h00", "12h40", "13h00",
            "13h40", "14h00", "14h40", "15h00", "15h40", "16h00", "16h40", "17h00", "17h40", "18h00", "18h40", "19h00"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dat_lich);
        init();
        btnChondichvu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listViewchondichvu.setVisibility(View.VISIBLE);
                startActivity(new Intent(getApplicationContext(), ChonDichVuActivity.class));
                finish();
            }
        });

        ivCheckVoucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtVoucher.clearFocus();
                finalPrice = DichvuAdapter.Price;
                totalPrice.setText("Tổng tiền: " + DichvuAdapter.Price + "");
                if (edtVoucher.getText() != null) {
                    if (!edtVoucher.getText().toString().equals("")) {
                        edtVoucher.clearFocus();
                        checkVoucher(edtVoucher.getText().toString());
                    }
                }
            }
        });

        edtVoucher.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ivCheckVoucher.setImageResource(R.drawable.ic_gray_search);
                discount = "";
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        if (DichvuAdapter.Dichvuchon.size() > 0) {
            btnChondichvu.setText("Chọn " + DichvuAdapter.Dichvuchon.size() + " dịch vụ");
            listViewchondichvu.setVisibility(View.VISIBLE);
            linnearchondichvu.setVisibility(View.GONE);
            linearchonngaygio.setVisibility(View.VISIBLE);
            b1 = true;
        } else {
            btnChondichvu.setText("Chọn dịch vụ");
            listViewchondichvu.setVisibility(View.GONE);
            linnearchondichvu.setVisibility(View.VISIBLE);
            linearchonngaygio.setVisibility(View.GONE);
            b1 = false;
        }

        linearChonStylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (stylist) {
                    imageShowStylist.setBackgroundResource(R.drawable.down_black);
                    recyclerStylist.setVisibility(View.VISIBLE);
                    stylist = false;

                } else {
                    imageShowStylist.setBackgroundResource(R.drawable.next_black);
                    recyclerStylist.setVisibility(View.GONE);
                    stylist = true;
                }
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveData();
                ClearData();
            }
        });
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClearData();
                finish();
            }
        });


    }

    private void ClearData() {
        DichvuAdapter.Dichvuchon.clear();
        ChonHour.setText("");
        AdapterHour.GioDaChon = "";
        StylistAdapter.stylistdachon = null;
        DichvuAdapter.Price = 0;
        startActivity(new Intent(getApplicationContext(), UserActivity.class));
        finish();
    }

    private void SaveData() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", "");
        map.put("iduser", firebaseUser.getUid());
        map.put("Dichvu", DichvuAdapter.Dichvuchon);
        map.put("Ngaydat", ChonHour.getText().toString());
        map.put("Giodat", AdapterHour.GioDaChon);
        map.put("Stylist", StylistAdapter.stylistdachon.getTitle());

        map.put("Price", finalPrice + "");
        map.put("Username", HomeFragment.Username);
        map.put("State", "Đặt lịch");
        firestore.collection("Booking")
                .add(map)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        firestore.collection("Booking").document(documentReference.getId()).update("id", documentReference.getId())
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(@NonNull Void unused) {
                                        Log.d("Success", "Success");
                                        if (voucher != null) {
                                            deleteVoucher();
                                        }
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d("Failed", "Failed");
                                    }
                                });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("TAG", "Error writing document", e);
                    }
                });
    }

    private void init() {
        ivBack = findViewById(R.id.ivBack);
        totalPrice = findViewById(R.id.totalPrice);
        ivCheckVoucher = findViewById(R.id.ivCheckVoucher);
        edtVoucher = findViewById(R.id.edtVoucher);
        tvAddress = findViewById(R.id.tvAddress);
        linnearchondichvu = findViewById(R.id.linearchondichvu);
        btnChondichvu = findViewById(R.id.btnChondichvu);
        listViewchondichvu = findViewById(R.id.listviewChondichvu);
        linearChonStylist = findViewById(R.id.lỉnerChonStylist);
        imageShowStylist = findViewById(R.id.imageShowStylist);
        linearchonngaygio = findViewById(R.id.linearngaygio);
        recyclerStylist = findViewById(R.id.recyclerStylist);
        recyclerHour = findViewById(R.id.recyclerHour);
        Stylist = findViewById(R.id.Stylist);
        ChonHour = findViewById(R.id.ChonHour);
        btnSubmit = findViewById(R.id.btnSubmit);
        adapter = new DichvuDaChonAdapter(getApplicationContext(), DichvuAdapter.Dichvuchon);
        listViewchondichvu.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        listViewchondichvu.setAdapter(adapter);
        stylists = new ArrayList<>();
        stylistAdapter = new StylistAdapter(getApplicationContext(), stylists);
        recyclerStylist.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerStylist.setAdapter(stylistAdapter);


        totalPrice.setText("Tổng tiền: " + DichvuAdapter.Price);
        try {
            sharedPref = this.getSharedPreferences("storeChoose", Context.MODE_PRIVATE);
            String address = sharedPref.getString("storeAddress", " ");
            uid = sharedPref.getString("uid", "none");
            tvAddress.setText(address);
        } catch (Exception e) {

        }

        RecyclerHour();
        ChonGio();
        getStylist();
    }

    private void ChonGio() {
        Date dateCurrent = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("E (dd/MM/yyyy)");
        String strDate = formatter.format(dateCurrent);
        ChonHour.setText(strDate);

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, day);
                updateLabel();
            }
        };

        ChonHour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(DatLichActivity.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
            }
        });
    }

    private void updateLabel() {
        String myFormat = "E (dd/MM/yyyy)";
        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);
        ChonHour.setText(dateFormat.format(myCalendar.getTime()));
    }

    private void RecyclerHour() {
        adapterHour = new AdapterHour(getApplicationContext(), Hours);
        recyclerHour.setLayoutManager(new GridLayoutManager(getApplicationContext(), 3, GridLayoutManager.HORIZONTAL, false));
        recyclerHour.setAdapter(adapterHour);
    }

    private void getStylist() {
        stylists.clear();
        CollectionReference productRefs = firestore.collection("Stylists");
        productRefs.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot documentSnapshots) {
                if (documentSnapshots.isEmpty()) {
                    Log.d("TAG", "onSuccess: LIST EMPTY");
                    return;
                } else {
                    for (DocumentSnapshot document : documentSnapshots) {
                        String id = document.getId();
                        String image = document.getString("image");
                        String title = document.getString("title");
                        Stylist s = new Stylist(id, image, title);
                        stylists.add(s);
                    }
                    stylistAdapter.notifyDataSetChanged();

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("TAG", "Error");
            }
        });
    }

    private void checkVoucher(String inputCode) {
        hasVoucher = false;
        CollectionReference productRefs = firestore.collection("Vouchers");
        productRefs.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot documentSnapshots) {
                if (documentSnapshots.isEmpty()) {
                    return;
                } else {
                    for (DocumentSnapshot document : documentSnapshots) {
                        Log.d("TAG", "onSuccess: " + uid);
                        voucher = new Voucher(document.getId(), document.getString("owner"), document.getString("discount"), document.getString("code"));
                        if (voucher.owner.equals(uid) && voucher.code.equals(inputCode)) {
                            discount = voucher.discount;
                            finalPrice = DichvuAdapter.Price - Integer.parseInt(discount);
                            if (finalPrice < 0) finalPrice = 0;
                            ivCheckVoucher.setImageResource(R.drawable.check);
                            totalPrice.setText("Tổng tiền: " + finalPrice);
                            hasVoucher = true;
                            Toast.makeText(DatLichActivity.this, "Dùng voucher thành công!", Toast.LENGTH_SHORT).show();
                            break;
                        }
                    }
                    if (!hasVoucher) {
                        finalPrice = DichvuAdapter.Price;
                        totalPrice.setText("Tổng tiền: " + finalPrice);

                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("TAG", "Error");
            }
        });
    }

    private void deleteVoucher() {
        CollectionReference productRefs = firestore.collection("Vouchers");
        productRefs.document(voucher.getId())
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("TAG", "DocumentSnapshot successfully deleted!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("TAG", "Error deleting document", e);
                    }
                });
    }
}