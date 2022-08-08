package com.example.se306_project1.models;

import com.google.firebase.database.Exclude;

public class ToteBag extends Product{

    private int categoryID = 3;

    public ToteBag(){

    }

    @Override
    @Exclude
    public int getCategoryID() {
        return categoryID;
    }

}
