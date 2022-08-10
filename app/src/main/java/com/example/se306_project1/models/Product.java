package com.example.se306_project1.models;

import com.google.firebase.database.Exclude;

import java.util.ArrayList;

    public class Product implements IProduct {

    // instance fields
    private final int productID;
    private final int categoryID;
    private final Double productPrice;

    private final String productLongName;
    private final String productShortName;
    private final Brand brandName;


    private final String productDescription;
    private final String productDetails;
    private final String productCare;


    private final ColourType productColourType;
    private int productCountVisit;
    private Boolean productIsFavourite;
    private final ArrayList<String> productImages;


    public Product(int productID, int categoryID, Double productPrice,
                   String productLongName, String productShortName, Brand brandName,
                   String productDescription, String productDetails, String productCare,
                   ColourType productColourType, int productCountVisit, boolean isFavourite, ArrayList<String> productImages){
        this.productID = productID;
        this.categoryID = categoryID;
        this.productPrice = productPrice;

        this.productLongName = productLongName;
        this.productShortName = productShortName;
        this.brandName = brandName;

        this.productDescription = productDescription;
        this.productDetails = productDetails;
        this.productCare = productCare;

        this.productColourType = productColourType;
        this.productCountVisit = productCountVisit;
        this.productIsFavourite = isFavourite;
        this.productImages = productImages;
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
    public Brand getBrandName(){
        return brandName;
    }
    @Exclude
    public ColourType getProductColourType(){
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
