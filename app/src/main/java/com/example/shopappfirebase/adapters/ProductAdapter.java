package com.example.shopappfirebase.adapters;

import android.content.Context;
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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.HolderProduct>{

    private Context context;
    public ArrayList<ModelProduct> productList;

    public ProductAdapter(Context context, ArrayList<ModelProduct> productList) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public HolderProduct onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false);
        return new HolderProduct(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderProduct holder, int position) {
        //get Data
        ModelProduct modelProduct = productList.get(position);

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

        holder.addToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Added to cart!", Toast.LENGTH_SHORT).show();
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
        private ImageButton addToCartBtn;

        public HolderProduct(@NonNull View itemView) {
            super(itemView);

            productImage = itemView.findViewById(R.id.productImv);
            productTitle = itemView.findViewById(R.id.productTitleTv);
            productDescription = itemView.findViewById(R.id.productDescriptionTv);
            productPrice = itemView.findViewById(R.id.priceTv);
            productQuantity = itemView.findViewById(R.id.quantityTv);
            addToCartBtn = itemView.findViewById(R.id.addToCartBtn);
        }
    }
}
