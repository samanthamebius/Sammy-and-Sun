package com.example.se306_project1.models;

import com.google.firebase.database.Exclude;

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

    @Exclude
    public long getCategoryID() {
        return categoryID;
    }
    @Exclude
    public String getCategoryName(){
        return categoryName;
    }
    @Exclude
    public String getCategoryImage(){
        return categoryImage;
    }
}
