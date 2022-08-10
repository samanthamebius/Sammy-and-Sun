package com.example.se306_project1.repository;


import android.util.Log;
import androidx.annotation.NonNull;

import com.example.se306_project1.models.Product;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
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
//    public MutableLiveData<List<Product>> getProducts() {
//        // retrieve data from web service and add to productsDataSet
//
//        MutableLiveData<List<Product>> data = new MutableLiveData<>();
//        data.setValue(productsDataSet);
//        return data;
//    }

    public void getAllProducts(){
        CollectionReference colref = FirebaseFirestore.getInstance().collection("clutches");
        colref.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                // unsuccessful load
                if(!task.isSuccessful()){
                    Log.d("firebase", "Error getting data!", task.getException());
                }
                else{
                    // successful load
                    Log.d("firebase", String.valueOf(task.getResult()));
                    List<DocumentSnapshot> snap = task.getResult().getDocuments();
                    for(DocumentSnapshot singleBag : snap){
                        String productLongName = singleBag.get("productLongName").toString();
                        String productShortName = singleBag.get("productShortName").toString();
                        System.out.println(productLongName);
                    }
                }
            }
        });
    }



}
