package com.example.se306_project1.models;

import java.util.ArrayList;

public interface IProduct {
    public long getProductID();
    public long getCategoryID();
    public Double getProductPrice();

    public String getProductLongName();
    public String getProductShortName();
    public Brand getBrandName();

    public String getProductDescription();
    public String getProductDetails();
    public String getProductCare();

    public ColourType getProductColourType();
    public long getProductCountVisit();
    public Boolean getIsFavourite();
    public void setProductIsFavourite(Boolean isFavourite);
    public ArrayList<String> getProductImages();

}
