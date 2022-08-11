package com.example.se306_project1.models;

public class Category implements ICategory{

    // private instance fields
    private long categoryID;
    private String categoryName;
    private String categoryImage;

    public Category(long categoryID, String categoryImage, String categoryName){
        this.categoryID = categoryID;
        this.categoryImage = categoryImage;
        this.categoryName = categoryName;
    }

    public String getCategoryName(){
        return categoryName;
    }

    public String getCategoryImage(){
        return categoryImage;
    }
}
