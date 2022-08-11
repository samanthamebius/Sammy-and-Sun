package com.example.se306_project1.models;

import com.google.firebase.database.Exclude;

public class Category implements ICategory{

    // private instance fields
    private int categoryID;
    private String categoryName;
    private String categoryImage;

    public Category(int categoryID, String categoryName, String categoryImage){

        this.categoryID = categoryID;
        this.categoryName = categoryName;
        this.categoryImage = categoryImage;
    }

    @Exclude
    public int getCategoryID() {
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
