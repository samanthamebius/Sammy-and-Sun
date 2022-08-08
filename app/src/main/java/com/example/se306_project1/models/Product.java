package com.example.se306_project1.models;

import java.util.ArrayList;

abstract class Product {

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

    private Brand brandName;
    private String productColourType;
    private Boolean productIsFavourite;
    private int productCountVisit;


    // regular methods
    public int getProductID() {
        return productID;
    }

    public Double getProductPrice(){
        return productPrice;
    };
    public String getProductLongName(){
        return productLongName;
    };
    public String getProductShortName(){
        return productShortName;
    };
    public String getProductDescription(){
        return productDescription;
    };
    public String getProductDetails(){
        return productDetails;
    };
    public String getProductCare(){
        return productCare;
    };


    // abstract methods
    abstract int getCategoryID();
    abstract Brand getBrandName();
    abstract String getProductColourType();
    abstract void setProductIsFavourite(Boolean setFavourites);
    abstract int getProductCountVisit();





}
