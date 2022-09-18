package com.ngochien.myapplication.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.ngochien.myapplication.R;

import java.util.HashMap;
import java.util.Map;

import static com.ngochien.myapplication.R.string.default_web_client_id;

public class LoginActivity extends AppCompatActivity {
    ImageButton btnBack;
    EditText edtPhone,edtPassword;
    Button btnLogin;
    TextView txtCreateAccount,txtForgotPassword;
    ImageView btnGoogle;
    FirebaseAuth fAuth;
    private String UserID;
    FirebaseFirestore fStore;
    ProgressBar progressBar;
    FirebaseUser fUser;
    private final static int RC_SIGN_IN=123;
    public static GoogleSignInClient googleSignInClient;

    SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Init();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(default_web_client_id))
                .requestEmail()
                .build();

        checkUser();
        googleSignInClient = GoogleSignIn.getClient(this, gso);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        txtCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),RegisterActivity.class));
            }
        });
        txtForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),ForgotPasswordActivity.class));
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Login();
            }
        });
        btnGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signInIntent = googleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });
    }
    private void Login(){
        String Phone=edtPhone.getText().toString().trim();
        String Password=edtPassword.getText().toString().trim();
        if(TextUtils.isEmpty(Phone)){
            edtPhone.setError("Phone chưa điền");
            return;
        }
        if(TextUtils.isEmpty(Password))
        {
            edtPassword.setError("Hãy nhập mật khẩu");
            return;
        }
        if(Password.length()<=6)
        {
            edtPassword.setError("Mật khẩu cần hơn 6 ký tự");
            return;
        }
        String Phonetrick = Phone+"@gmail.com";
        fAuth.signInWithEmailAndPassword(Phonetrick,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"Đăng nhập thành công",Toast.LENGTH_LONG).show();
//                    startActivity(new Intent(getApplicationContext(),Admin.class));
                    CheckRole();
                }
                else {
                    Toast.makeText(getApplicationContext().getApplicationContext(),"Lỗi ! ",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    private void Init()
    {
        sharedPref = this.getSharedPreferences("storeChoose", Context.MODE_PRIVATE);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        btnBack = findViewById(R.id.btnBack);
        edtPhone = findViewById(R.id.edtPhone);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        txtCreateAccount = findViewById(R.id.txtCreateAccount);
        txtForgotPassword = findViewById(R.id.txtForgotPassword);
        progressBar = findViewById(R.id.progress_circular);
        btnGoogle= findViewById(R.id.btnGoogle);
    }
    private void checkUser() {
        fUser = fAuth.getCurrentUser();
        if(fUser!=null){
            CheckRole();
        }
    }
    private void CheckRole() {
        progressBar.setVisibility(View.VISIBLE);
        UserID = fAuth.getCurrentUser().getUid();
        DocumentReference documentReference = fStore.collection("Users").document(UserID);
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d("TAG", "DocumentSnapshot data: " + document.getData());
                        String role = document.getString("role");


                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString("uid", document.getString("idUser"));
                        editor.apply();

                        Log.d("role", role);
                        Intent intent = new Intent();
                        switch (role) {
                            case "User":
                                intent = new Intent(getApplicationContext(), UserActivity.class);
                                break;
                            case "Admin":
                                intent = new Intent(getApplicationContext(), AdminActivity.class);
                                break;
                            default:
                                break;
                        }
                        startActivity(intent);
                        finish();
                    } else {
                        Log.d("TAG", "No such document");
                    }
                } else {
                    Log.d("TAG", "get failed with ", task.getException());
                }
            }

        });
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }
    private void handleSignInResult(GoogleSignInResult result) {
        Log.d("TAG", "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            AuthCredential credential = GoogleAuthProvider.getCredential(result.getSignInAccount().getIdToken(), null);
            fAuth.signInWithCredential(credential).
                    addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            if(authResult.getAdditionalUserInfo().isNewUser()){
                                Toast.makeText(getApplicationContext(),"Account Created ..", Toast.LENGTH_SHORT).show();
                                Log.d("123","Success: Logged In");

                                FirebaseUser firebaseUser = fAuth.getCurrentUser();

                                String id = firebaseUser.getUid();
                                String email = firebaseUser.getEmail();
                                String username = firebaseUser.getDisplayName();

                                DocumentReference documentReference = fStore.collection("Users").document(id);
                                Map<String, Object> User = new HashMap<>();
                                User.put("Username", username);
                                User.put("Email", email);
                                User.put("Phone", "");
                                User.put("Address", "");
                                User.put("Avatar","data:image/png;base64,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");
                                User.put("role","User");
                                User.put("idUser",id);
                                documentReference.set(User).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d("TAG", "onSuccess: user profile is create for "+ id);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d("TAG", "onFailure: "+ e.toString());
                                    }
                                });
                            }
                            else{
                                Toast.makeText(getApplicationContext(), "Existing user...", Toast.LENGTH_SHORT).show();
                            }

                            Intent intent = new Intent(getApplicationContext(), UserActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(), "Fail...", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}