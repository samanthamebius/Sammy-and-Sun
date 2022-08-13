package com.example.se306_project1.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.se306_project1.models.Brand;
import com.example.se306_project1.models.ColourType;
import com.example.se306_project1.models.Product;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class PopularRepository implements IPopularRepository{

    public List<Product> popularDataSet = new ArrayList<>();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DatabaseReference dref = FirebaseDatabase.getInstance().getReference();

    // singleton pattern
    private static ProductRepository instance;
    public static ProductRepository getInstance(){
        if(instance == null){
            instance = new ProductRepository();
        }
        return instance;
    }

    public LiveData<List<Product>> getPopular() {
        popularDataSet.clear();
        fetchAllProducts();
        MutableLiveData<List<Product>> data = new MutableLiveData<>();
        data.setValue(popularDataSet);
        return data;
    }

    public void fetchAllProducts(){
        CollectionReference collectionRef = db.collection("popular");
        collectionRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    Log.d("firebase", String.valueOf(task.getResult()));
                    List<DocumentSnapshot> snapshots = task.getResult().getDocuments();
                    for(DocumentSnapshot popularBag : snapshots){
                        long productID = (long) popularBag.get("productID");
                        long categoryID = (long) popularBag.get("categoryID");
                        double productPrice = (double) popularBag.get("productPrice");
                        String productLongName = popularBag.get("productLongName").toString();
                        String productShortName = popularBag.get("productShortName").toString();
                        String brandName = popularBag.get("brandName").toString();
                        String productDescription = popularBag.get("productDescription").toString();
                        String productDetails = popularBag.get("productDetails").toString();
                        String productCare = popularBag.get("productCare").toString();
                        String productColourType = popularBag.get("productColourType").toString();
                        long productCountVisit = (long) popularBag.get("productCountVisit");
                        boolean isFavourite = (boolean) popularBag.get("isFavourite");
                        ArrayList<String> productImages = (ArrayList<String>) popularBag.get("productImages");
                        popularDataSet.add(new Product(productID, categoryID, productPrice, productLongName, productShortName, brandName,
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
