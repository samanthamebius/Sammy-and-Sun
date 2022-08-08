package com.example.se306_project1.models;

import com.google.firebase.database.Exclude;

import java.util.ArrayList;

public class CrossBodyBag extends Product{

    private int categoryID = 2;
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

    public CrossBodyBag(int productID, Double productPrice, String productLongName, String productShortName, ArrayList<String> productImages, String productDescription, String productDetails, String productCare){
        this.productID = productID;
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


    @Override
    @Exclude
    public int getCategoryID() {
        return categoryID;
    }

}
