package com.ngochien.myapplication.ActivityOTP;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.ngochien.myapplication.R;

import java.util.concurrent.TimeUnit;

public class SendOTPActivity extends AppCompatActivity {
    EditText edtPhone;
    Button btnSendOTP;
    ProgressBar progress_circular;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_o_t_p);
        String phoneNum = "+16505554567";
        String testVerificationCode = "123456";
        edtPhone = findViewById(R.id.edtPhone);
        btnSendOTP = findViewById(R.id.btnsendOTP);
        progress_circular = findViewById(R.id.progress_circular);
        btnSendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edtPhone.getText().toString().trim().isEmpty()){
                    edtPhone.setError("Enter Mobile");
                }
                progress_circular.setVisibility(View.VISIBLE);
                btnSendOTP.setVisibility(View.INVISIBLE);

                PhoneAuthOptions options = PhoneAuthOptions.newBuilder(auth)
                        .setPhoneNumber("+84" + edtPhone.getText().toString())
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setActivity(SendOTPActivity.this)
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                          @Override
                                          public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                              progress_circular.setVisibility(View.GONE);
                                              btnSendOTP.setVisibility(View.VISIBLE);
                                          }

                                          @Override
                                          public void onVerificationFailed(@NonNull FirebaseException e) {
                                              progress_circular.setVisibility(View.GONE);
                                              btnSendOTP.setVisibility(View.VISIBLE);
                                              Toast.makeText(SendOTPActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                              Log.d("Failed", e.getMessage());
                                          }

                                          @Override
                                          public void onCodeSent(@NonNull String verificationID, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                              progress_circular.setVisibility(View.GONE);
                                              btnSendOTP.setVisibility(View.VISIBLE);
                                              Intent intent = new Intent(getApplicationContext(), VerifyOTPActivity.class);
                                              intent.putExtra("mobile", edtPhone.getText().toString());
                                              intent.putExtra("verificationId", verificationID);
                                              startActivity(intent);
                                          }
                                      }).build();
                PhoneAuthProvider.verifyPhoneNumber(options);
//                                PhoneAuthProvider.getInstance().verifyPhoneNumber(
//                                        "+84" + edtPhone.getText().toString(),
//                                        60,
//                                        TimeUnit.SECONDS,
//                                        SendOTPActivity.this,
//                                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//                                            @Override
//                                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
//
//                                            }
//
//                                            @Override
//                                            public void onVerificationFailed(@NonNull FirebaseException e) {
//
//                                            }
//
//                                            @Override
//                                            public void onCodeSent(@NonNull String verificationID, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
//
//                                            }
//                                        }
//
//                                );


            }
        });
    }
}