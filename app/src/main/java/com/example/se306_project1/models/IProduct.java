package com.example.se306_project1.models;

public interface IProduct {
    public long getProductID();
    public Double getProductPrice();
    public String getProductLongName();
    public String getProductShortName();
    public String getProductDescription();
    public String getProductDetails();
    public String getProductCare();
    public long getCategoryID();
    public Brand getBrandName();
    public ColourType getProductColourType();
    public Boolean getIsFavourite();
    public void setProductIsFavourite(Boolean isFavourite);
    public long getProductCountVisit();
}
