package com.example.se306_project1.viewmodel;

import android.widget.TextView;

import com.example.se306_project1.models.IProduct;

import java.util.ArrayList;
import java.util.List;

public interface ISearchViewModel {
    public IProduct getProductByID(long productID);
    public List<IProduct> getAllProducts();
    public List<IProduct> getIncompleteSearchList (String searchWord, ArrayList<IProduct> resultsList, List<IProduct> allProducts, TextView view);
}
