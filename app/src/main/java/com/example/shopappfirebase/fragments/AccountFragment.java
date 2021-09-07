package com.example.shopappfirebase.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.shopappfirebase.R;
import com.example.shopappfirebase.activities.LoginActivity;
import com.example.shopappfirebase.databinding.FragmentAccountBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;


public class AccountFragment extends Fragment {
    private FragmentAccountBinding binding;

    private FirebaseAuth firebaseAuth;

    private FirebaseDatabase db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAccountBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //init firebase auth and firebase database
        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance("https://shop-app-firebase-180d0-default-rtdb.asia-southeast1.firebasedatabase.app/");

        //check if login
        checkUser();

        binding.updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"Not implement yet", Toast.LENGTH_SHORT).show();
            }
        });

        binding.logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                checkUser();
            }
        });
    }

    //check login
    private void checkUser() {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if(firebaseUser == null){
            startActivity(new Intent(getActivity(), LoginActivity.class));
            getActivity().finish();
        }
        else{
            DatabaseReference userRef = db.getReference("users");
            userRef.child(firebaseUser.getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (!task.isSuccessful()) {
                        Log.e("firebase", "Error getting data", task.getException());
                    }
                    else {
                        binding.nameEt.setText(task.getResult().child("name").getValue().toString());
                        binding.emailEt.setText(task.getResult().child("email").getValue().toString());
                        binding.phoneEt.setText(task.getResult().child("phone").getValue().toString());
                        String imageUrl = task.getResult().child("profileImage").getValue().toString();
                        if(!imageUrl.isEmpty())
                            Picasso.get().load(imageUrl).into(binding.profileImv);
                    }
                }
            });
        }
    }
}