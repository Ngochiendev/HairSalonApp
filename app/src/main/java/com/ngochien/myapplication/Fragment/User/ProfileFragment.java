package com.ngochien.myapplication.Fragment.User;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.ngochien.myapplication.Activity.HelpCenterActivity;
import com.ngochien.myapplication.Activity.InfoProfileActivity;
import com.ngochien.myapplication.Activity.LichSuActivity;
import com.ngochien.myapplication.Activity.LoginActivity;
import com.ngochien.myapplication.Activity.StilvenMemberActivity;
import com.ngochien.myapplication.Model.User;
import com.ngochien.myapplication.R;

import de.hdodenhof.circleimageview.CircleImageView;


public class ProfileFragment extends Fragment {

    View view;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    ImageView imageUser;
    LinearLayout llProfile;
    TextView txtUsername, txtinfo;
    User user;
    Button btnLogout, btninfo, btHelpCenter, btStores, btStilvenMember, btnLichsucat;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.user_fragment_profile, container, false);
        imageUser = view.findViewById(R.id.imguser);
        btnLichsucat = view.findViewById(R.id.btnLichsucat);
        llProfile = view.findViewById(R.id.llProfile);
        txtUsername = view.findViewById(R.id.username);
        btnLogout = view.findViewById(R.id.btnlogout);
        btninfo = view.findViewById(R.id.btninfo);
        txtinfo = view.findViewById(R.id.txtchinhsua);
        btHelpCenter = view.findViewById(R.id.btHelpCenter);
        btStores = view.findViewById(R.id.btStores);
        btStilvenMember = view.findViewById(R.id.btStilvenMember);
        getUser();
        btnLogout.setOnClickListener(v -> {
            firebaseAuth.signOut();
            startActivity(new Intent(getActivity(), LoginActivity.class));
            getActivity().finish();
        });
        btninfo.setOnClickListener(v -> startActivity(new Intent(getActivity(), InfoProfileActivity.class)));
        btnLichsucat.setOnClickListener(v -> startActivity(new Intent(getActivity(), LichSuActivity.class)));
        llProfile.setOnClickListener(v -> startActivity(new Intent(getActivity(), InfoProfileActivity.class)));
        btHelpCenter.setOnClickListener(v -> startActivity(new Intent(getActivity(), HelpCenterActivity.class)));
        btStores.setOnClickListener(v -> startActivity(new Intent(getActivity(), StoresActivity.class)));
        btStilvenMember.setOnClickListener(v -> startActivity(new Intent(getActivity(), StilvenMemberActivity.class)));
        return view;

    }

    private void getUser() {
        DocumentReference documentReference = firestore.collection("Users").document(firebaseUser.getUid());
        documentReference.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    String Username = document.getString("Username");
                    String Ava = document.getString("Avatar");
                    String Email = document.getString("Email");
                    String Phone = document.getString("Phone");
                    String Address = document.getString("Address");
                    user = new User(firebaseUser.getUid(), Username, Email, Address, Phone, Ava);
                    Glide.with(getActivity())
                            .load(Ava).circleCrop()
                            .into(imageUser);
                    txtUsername.setText(Username);
                } else {
                    Log.d("TAG", "No such document");
                }
            } else {
                Log.d("TAG", "get failed with ", task.getException());
            }
        });
    }
}