package com.example.se306_project1.repository;


import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.se306_project1.models.Product;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;


import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ProductRepository {

    // singleton pattern
    private static ProductRepository instance;
    private List<Product> productsDataSet = new ArrayList<>();

    private DatabaseReference dref;

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

    private void getAllProducts(){
        dref = FirebaseDatabase.getInstance().getReference();
        dref.child("clutches").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(!task.isSuccessful()){
                    Log.d("firebase", "Error getting data", task.getException());
                }
                else{
                    Log.d("firebase", String.valueOf(task.getResult().getValue()));
                    task.getResult(); // pass through to productDataSet
                }
            }
        });
    }


}
