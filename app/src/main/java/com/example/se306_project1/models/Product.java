package com.example.se306_project1.models;

import com.google.firebase.database.Exclude;

import java.util.ArrayList;

abstract class Product implements IProduct {

    // instance fields
    private static int numberOfProducts = 0;
    private int productID;
    private int categoryID;
    private Double productPrice;
    private String productLongName;
    private String productShortName;

    private ArrayList<String> productImages;
    private String productDescription;
    private String productDetails;
    private String productCare;

    private String brandName;
    private String productColourType;
    private Boolean productIsFavourite;
    private int productCountVisit;


    // regular methods
    @Exclude
    public int getProductID() {
        return productID;
    }
    @Exclude
    public Double getProductPrice(){
        return productPrice;
    }
    @Exclude
    public String getProductLongName(){
        return productLongName;
    }
    @Exclude
    public String getProductShortName(){
        return productShortName;
    }
    @Exclude
    public String getProductDescription(){
        return productDescription;
    }
    @Exclude
    public String getProductDetails(){
        return productDetails;
    }
    @Exclude
    public String getProductCare(){
        return productCare;
    }

    // abstract methods
    public abstract int getCategoryID();
    public abstract String getBrandName();
    public abstract String getProductColourType();
    public abstract void setProductIsFavourite(Boolean isFavourite);
    public abstract Boolean getIsFavourite();
    public abstract int getProductCountVisit();



}
