package com.example.se306_project1.models;

import java.util.ArrayList;

public interface IProduct {
    public int getProductID();
    public Double getProductPrice();
    public String getProductLongName();
    public String getProductShortName();
    public String getProductDescription();
    public String getProductDetails();
    public String getProductCare();
    public int getCategoryID();
    public String getBrandName();
    public String getProductColourType();
    public Boolean getIsFavourite();
    public void setProductIsFavourite(Boolean isFavourite);
    public int getProductCountVisit();
    public ArrayList<String> getProductImages();
}
