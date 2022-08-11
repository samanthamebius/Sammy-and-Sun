package com.example.se306_project1.models;

public class Category implements ICategory{

    // private instance fields
    private int categoryID;
    private String categoryName;
    private String categoryImage;


    public String getCategoryName(){
        return categoryName;
    }

    public String getCategoryImage(){
        return categoryImage;
    }
}
