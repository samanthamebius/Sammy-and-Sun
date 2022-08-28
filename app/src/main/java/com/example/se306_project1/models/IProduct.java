package com.example.se306_project1.models;

import java.util.ArrayList;

public interface IProduct {
    long getProductID();
    long getCategoryID();
    double getProductPrice();

    String getProductLongName();
    String getProductShortName();
    Brand getBrandName();

    String getProductDescription();
    String getProductDetails();
    String getProductCare();

    long getProductCountVisit();
    void increaseProductViewCount();
    Boolean getIsFavourite();
    void setProductIsFavourite(Boolean isFavourite);
    ArrayList<String> getProductImages();

}
