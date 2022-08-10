package com.example.se306_project1.repository;


import android.util.Log;
import androidx.annotation.NonNull;

import com.example.se306_project1.models.Brand;
import com.example.se306_project1.models.ColourType;
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
    // dataset of all products
    public List<Product> productsDataSet = new ArrayList<>();

    private DatabaseReference databaseReference;

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
        CollectionReference collectionRef = FirebaseFirestore.getInstance().collection("products");
        collectionRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    Log.d("firebase", String.valueOf(task.getResult()));
                    // getting snapshot of all the documents in the collection
                    List<DocumentSnapshot> snapshots = task.getResult().getDocuments();
                    // looping through the documents
                    for(DocumentSnapshot singleBag : snapshots){
                        int productID = (int) singleBag.get("productID");
                        int categoryID = (int) singleBag.get("categoryID");
                        double productPrice = (double) singleBag.get("productPrice");
                        String productLongName = singleBag.get("productLongName").toString();
                        String productShortName = singleBag.get("productShortName").toString();
                        Brand brandName = Brand.valueOf(singleBag.get("brandName").toString().replaceAll(" ","_"));
                        String productDescription = singleBag.get("productDescription").toString();
                        String productDetails = singleBag.get("productDetails").toString();
                        String productCare = singleBag.get("productCare").toString();
                        ColourType productColourType = ColourType.valueOf(singleBag.get("productColourType").toString());
                        int productCountVisit = (int) singleBag.get("productCountVisit");
                        boolean isFavourite = (boolean) singleBag.get("isFavourite");
                        ArrayList<String> productImages = (ArrayList<String>) singleBag.get("productImages");
                        productsDataSet.add(new Product(productID, categoryID, productPrice, productLongName, productShortName, brandName,
                                productDescription, productDetails, productCare,
                                productColourType, productCountVisit, isFavourite, productImages));
                    }
                }
                else{
                    Log.d("firebase", "Error getting data!", task.getException());
                }
            }
        });
    }



}
