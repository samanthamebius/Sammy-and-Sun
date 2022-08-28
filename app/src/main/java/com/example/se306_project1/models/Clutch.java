package com.example.se306_project1.models;

import com.google.firebase.database.Exclude;
import java.util.ArrayList;

public class Clutch extends Product{
    public Clutch(long productID, long categoryID, Double productPrice,
                  String productLongName, String productShortName, Brand brandName,
                  String productDescription, String productDetails, String productCare,
                  ColourType productColourType, long productCountVisit, boolean isFavourite, ArrayList<String> productImages) {
        super(productID, categoryID, productPrice, productLongName, productShortName, brandName, productDescription, productDetails, productCare, productColourType, productCountVisit, isFavourite, productImages);
    }


    @Exclude
    @Override
    public long getCategoryID() {
        return 0;
    }
}
