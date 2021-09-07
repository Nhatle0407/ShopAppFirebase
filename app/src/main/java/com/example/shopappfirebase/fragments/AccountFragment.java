package com.example.shopappfirebase.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
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
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;


public class AccountFragment extends Fragment {
    private FragmentAccountBinding binding;

    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    private FirebaseDatabase db = FirebaseDatabase.getInstance("https://shop-app-firebase-180d0-default-rtdb.asia-southeast1.firebasedatabase.app/");

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAccountBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //check if login
        checkUser();

        binding.editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.editBtn.setVisibility(View.GONE);
                binding.updateBtn.setVisibility(View.VISIBLE);
                binding.emailEt.setEnabled(true);
                binding.nameEt.setEnabled(true);
                binding.phoneEt.setEnabled(true);
            }
        });

        binding.updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateData();
                checkUser();
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

    private void updateData() {
        //get data
        String name = binding.nameEt.getText().toString();
        String email = binding.emailEt.getText().toString().trim();
        String phone = binding.phoneEt.getText().toString().trim();

        //validate data
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.emailEt.setError("Invalid email!");
            return;
        }
        if (TextUtils.isEmpty(name)){
            binding.nameEt.setError("Input your name!");
            return;
        }
        if(TextUtils.isEmpty(phone)) {
            binding.phoneEt.setError("Input your phone number!");
            return;
        }

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("name", "" + name);
        hashMap.put("email", "" + email);
        hashMap.put("phone", "" + phone);
        DatabaseReference userRef = db.getReference("users");
        FirebaseUser user = firebaseAuth.getCurrentUser();
        userRef.child(user.getUid()).updateChildren(hashMap, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                Toast.makeText(getActivity(), "Updated infomation", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //check login
    private void checkUser() {
        binding.editBtn.setVisibility(View.VISIBLE);
        binding.updateBtn.setVisibility(View.GONE);
        binding.emailEt.setEnabled(false);
        binding.nameEt.setEnabled(false);
        binding.phoneEt.setEnabled(false);

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