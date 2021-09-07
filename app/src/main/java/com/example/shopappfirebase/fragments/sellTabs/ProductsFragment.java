package com.example.shopappfirebase.fragments.sellTabs;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.shopappfirebase.R;
import com.example.shopappfirebase.adapters.ProductOwnerAdapter;
import com.example.shopappfirebase.models.ModelProduct;
import com.example.shopappfirebase.adapters.ProductAdapter;
import com.example.shopappfirebase.activities.AddProducActivity;
import com.example.shopappfirebase.databinding.FragmentProductsBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ProductsFragment extends Fragment {
    FragmentProductsBinding binding;

    private ArrayList<ModelProduct> productList;
    private RecyclerView productRv;
    private ProductOwnerAdapter productOwnerAdapter;


    public ProductsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProductsBinding.inflate(inflater, container, false);
        productRv = binding.productRv;
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loadUserProduct();

        binding.addProductBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AddProducActivity.class));
            }
        });
    }


    private void loadUserProduct() {
        productList = new ArrayList<>();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference productRef = FirebaseDatabase.getInstance("https://shop-app-firebase-180d0-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("products");
        productRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                productList.clear();
                if (snapshot.hasChildren()) {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        if (ds.child("productUid").getValue().toString().equals(user.getUid())) {
                            ModelProduct modelProduct = ds.getValue(ModelProduct.class);
                            productList.add(modelProduct);
                        }
                    }
                }
                productOwnerAdapter = new ProductOwnerAdapter(getActivity(), productList);
                productRv.setAdapter(productOwnerAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}