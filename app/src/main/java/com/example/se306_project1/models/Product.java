package com.example.se306_project1.models;

import com.google.firebase.database.Exclude;

import java.util.ArrayList;

abstract class Product implements IProduct {

    // instance fields
    private int productID;
    private Double productPrice;
    private String productLongName;
    private String productShortName;

    private ArrayList<String> productImages;
    private String productDescription;
    private String productDetails;
    private String productCare;

    private String brandName;
    private String productColourType;
    private Boolean productIsFavourite = Boolean.FALSE;
    private int productCountVisit = 0;


    // regular methods
    @Exclude
    public int getProductID() {
        return productID;
    }
    @Exclude
    public void setProductID(int productID){ this.productID = productID;}
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
    @Exclude
    public String getBrandName(){
        return brandName;
    }
    @Exclude
    public String getProductColourType(){
        return productColourType;
    }
    @Exclude
    public void setProductIsFavourite(Boolean isFavourite){
        this.productIsFavourite = isFavourite;
    }
    @Exclude
    public Boolean getIsFavourite(){
        return productIsFavourite;
    }
    @Exclude
    public  int getProductCountVisit(){
        return productCountVisit;
    }


    // abstract methods
    public abstract int getCategoryID();




}
