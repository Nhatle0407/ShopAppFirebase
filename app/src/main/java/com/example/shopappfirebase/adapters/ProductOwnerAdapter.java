package com.example.shopappfirebase.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopappfirebase.models.ModelProduct;
import com.example.shopappfirebase.R;
import com.example.shopappfirebase.activities.ProductDetailActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProductOwnerAdapter extends RecyclerView.Adapter<ProductOwnerAdapter.HolderProduct>{

    private Context context;
    public ArrayList<ModelProduct> productList;

    FirebaseDatabase db = FirebaseDatabase.getInstance("https://shop-app-firebase-180d0-default-rtdb.asia-southeast1.firebasedatabase.app/");

    public ProductOwnerAdapter(Context context, ArrayList<ModelProduct> productList) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public HolderProduct onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_product_owner, parent, false);
        return new HolderProduct(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderProduct holder, int position) {
        //get Data
        ModelProduct modelProduct = productList.get(position);

        String id = modelProduct.getProductId();
        String title = modelProduct.getProductTitle();
        String description = modelProduct.getProductDescription();
        String price = modelProduct.getProductPrice();
        String quantity = modelProduct.getProductQuantity();
        String uid = modelProduct.getProductUid();
        String image = modelProduct.getProductImage();

        //set data
        holder.productQuantity.setText(quantity);
        holder.productPrice.setText(price);
        holder.productDescription.setText(description);
        holder.productTitle.setText(title);

        try{
            Picasso.get().load(image).into(holder.productImage);
        }
        catch (Exception e){
            Log.d("product", "Fail to load image: " + e.getMessage());
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, ProductDetailActivity.class));
            }
        });

        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Delete")
                        .setMessage("Are you sure you want to delete product \""+ title + "\" ?")
                        .setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                deleteProduct(id);
                            }
                        }).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
            }
        });

    }

    private void deleteProduct(String id) {
        DatabaseReference productRef = db.getReference("products");
        productRef.child(id).removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        StorageReference photoRef = FirebaseStorage.getInstance().getReference().child("product_image/"+id);
                        photoRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(context, "Product deleted...", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(context, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class HolderProduct extends RecyclerView.ViewHolder{

        private ImageView productImage;
        private TextView productTitle, productDescription, productQuantity, productPrice;
        private ImageButton deleteBtn;

        public HolderProduct(@NonNull View itemView) {
            super(itemView);

            productImage = itemView.findViewById(R.id.productImv);
            productTitle = itemView.findViewById(R.id.productTitleTv);
            productDescription = itemView.findViewById(R.id.productDescriptionTv);
            productPrice = itemView.findViewById(R.id.priceTv);
            productQuantity = itemView.findViewById(R.id.quantityTv);
            deleteBtn = itemView.findViewById(R.id.deleteProductBtn);
        }
    }
}
