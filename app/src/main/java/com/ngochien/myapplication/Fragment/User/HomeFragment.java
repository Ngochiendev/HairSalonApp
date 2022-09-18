package com.ngochien.myapplication.Fragment.User;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.ngochien.myapplication.Activity.DatLichActivity;
import com.ngochien.myapplication.Activity.LichSuActivity;
import com.ngochien.myapplication.Activity.StilvenMemberActivity;
import com.ngochien.myapplication.Model.User;
import com.ngochien.myapplication.R;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeFragment extends Fragment {

    View view;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    CircleImageView iamgeUser;
    TextView txtUsername;
    User user;
    RelativeLayout btnDatlich,btnLichsucat, rlStilvenMember;
    public static String Username;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.usser_fragment_home, container, false);
        iamgeUser = view.findViewById(R.id.imageuser);
        txtUsername = view.findViewById(R.id.username);
        btnDatlich = view.findViewById(R.id.btnDatlich);
        btnLichsucat = view.findViewById(R.id.btnLichsucat);
        rlStilvenMember = view.findViewById(R.id.rlStilvenMember);
        getUser();

        btnDatlich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), StoresActivity.class));
            }
        });
        btnLichsucat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), LichSuActivity.class));
            }
        });
        rlStilvenMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), StilvenMemberActivity.class));
            }
        });
        return view;

    }
    private void getUser() {
        DocumentReference documentReference = firestore.collection("Users").document(firebaseUser.getUid());
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Username = document.getString("Username");
                        String Ava = document.getString("Avatar");
                        String Email = document.getString("Email");
                        String Phone = document.getString("Phone");
                        String Address = document.getString("Address");
                        user = new User(firebaseUser.getUid(),Username,Email,Address,Phone,Ava);
                        Glide.with(getActivity())
                                .load(Ava).circleCrop()
                                .into(iamgeUser);
                        txtUsername.setText(Username);
                    }
                    else {
                        Log.d("TAG", "No such document");
                    }
                } else
                {
                    Log.d("TAG", "get failed with ", task.getException());
                }
            }
        });
    }
}