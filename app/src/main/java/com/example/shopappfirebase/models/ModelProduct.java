package com.example.shopappfirebase.models;

public class ModelProduct {
    private String productId = "", productTitle = "", productDescription = "", productPrice = "", productQuantity = "", productImage = "", productUid = "";

    public ModelProduct() {
    }

    public ModelProduct(String productId, String productTitle, String productDescription, String productPrice, String productQuantity,
                        String productImage, String productUid) {
        this.productId = productId;
        this.productTitle = productTitle;
        this.productDescription = productDescription;
        this.productPrice = productPrice;
        this.productQuantity = productQuantity;
        this.productImage = productImage;
        this.productUid = productUid;
    }

    public String getProductId(){return productId;};

    public void setProductId(String productId){this.productId = productId;}

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(String productQuantity) {
        this.productQuantity = productQuantity;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getProductUid() {
        return productUid;
    }

    public void setProductUid(String productUid) {
        this.productUid = productUid;
    }
}
