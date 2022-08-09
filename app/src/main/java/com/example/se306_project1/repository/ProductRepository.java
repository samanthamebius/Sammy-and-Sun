package com.example.se306_project1.repository;

import android.arch.lifecycle.MutableLiveData;
import android.util.Log;
import com.example.se306_project1.models.Product;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;
import java.util.ArrayList;
import java.util.List;

public class ProductRepository {

    // singleton pattern
    private static ProductRepository instance;
    private ArrayList<Product> productsDataSet = new ArrayList<>();

    public static ProductRepository getInstance(){
        if(instance == null){
            instance = new ProductRepository();
        }
        return instance;
    }

    // method to make database queries
    public MutableLiveData<List<Product>> getProducts() {
        // retrieve data from web service and add to productsDataSet

        MutableLiveData<List<Product>> data = new MutableLiveData<>();
        data.setValue(productsDataSet);
        return data;
    }


}
