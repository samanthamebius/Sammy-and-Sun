package com.example.se306_project1.models;

import com.google.firebase.database.Exclude;

import java.util.ArrayList;

    public abstract class Product implements IProduct {

        // instance fields
        private final long productID;
        private final long categoryID;
        private final double productPrice;
        
        private final String productLongName;
        private final String productShortName;
        private final Brand brandName;

        private final String productDescription;
        private final String productDetails;
        private final String productCare;


        private final ColourType productColourType;
        private long productCountVisit = 0;
        private Boolean productIsFavourite;
        private final ArrayList<String> productImages;


        public Product(long productID, long categoryID, double productPrice,
                       String productLongName, String productShortName, Brand brandName,
                       String productDescription, String productDetails, String productCare,
                       ColourType productColourType, long productCountVisit, boolean isFavourite, ArrayList<String> productImages) {

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
    public long getProductID() {
        return productID;
    }
    @Exclude
    public long getCategoryID() {
        return categoryID;
    }
    @Exclude
    public double getProductPrice(){
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
    public Brand getBrandName() {
        return brandName;
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
    public ColourType getProductColourType() {
        return productColourType;
    }
    @Exclude
    public void addVisitCount() {
        this.productCountVisit++;
    }
    @Exclude
    public long getProductCountVisit() {
        return this.productCountVisit;
    }

    @Exclude
    public Boolean getIsFavourite() {
        return productIsFavourite;
    }
    @Exclude
    public void setProductIsFavourite(Boolean isFavourite) {
        this.productIsFavourite = isFavourite;
    }
   @Exclude
    public ArrayList<String> getProductImages() {
        return productImages;
    }

}

