package com.example.se306_project1.models;

import com.google.firebase.database.Exclude;

import java.util.ArrayList;

    public class Product implements IProduct {

    // instance fields
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
    private Boolean productIsFavourite = Boolean.FALSE;
    private int productCountVisit = 0;


    public Product(int productID, int categoryID, Double productPrice, String productLongName, String productShortName, ArrayList<String> productImages, String productDescription, String productDetails, String productCare){
        this.productID = productID;
        this.categoryID = categoryID;
        this.productPrice = productPrice;
        this.productLongName = productLongName;
        this.productShortName = productShortName;
        this.productImages = productImages;
        this.productDescription = productDescription;
        this.productDetails = productDetails;
        this.productCare = productCare;
        this.brandName = getBrandName();
        this.productColourType = getProductColourType();
    }



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
    @Exclude
    public String getBrandName(){
        return brandName;
    }
    @Exclude
    public String getProductColourType(){
        return productColourType;
    }

    @Exclude
    public Boolean getIsFavourite(){
        return productIsFavourite;
    }
    @Exclude
    public  int getProductCountVisit(){
        return productCountVisit;
    }

    @Exclude
    public int getCategoryID(){
        return categoryID;
    }

    @Exclude
    public void setProductIsFavourite(Boolean isFavourite){
        this.productIsFavourite = isFavourite;
    }

    @Exclude
    public void setVisitCount(){}
        
    }
