package com.example.se306_project1.models;

import com.google.firebase.database.Exclude;
import java.util.ArrayList;

    public class Product implements IProduct {

        private long productID;
        private long categoryID;
        private double productPrice;
        
        private String productLongName;
        private String productShortName;
        private Brand brandName;

        private String productDescription;
        private String productDetails;
        private String productCare;

        private ColourType productColourType;
        private long productCountVisit = 0;
        private Boolean productIsFavourite;
        private ArrayList<String> productImages;


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

    @Override
    public void increaseProductViewCount() {
        this.productCountVisit = productCountVisit + 1;
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

