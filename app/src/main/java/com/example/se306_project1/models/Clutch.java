package com.example.se306_project1.models;

import com.google.firebase.database.Exclude;

public class Clutch extends Product{

    private int categoryID = 1;

    public Clutch(){

    }

    @Override
    @Exclude
    public int getCategoryID() {
        return categoryID;
    }

}
