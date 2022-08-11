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

public class FavouritesRepository {

    public List<Product> favouritesDataSet = new ArrayList<>();
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

    public LiveData<List<Product>> getProducts() {
        favouritesDataSet.clear();
        fetchAllProducts();
        MutableLiveData<List<Product>> data = new MutableLiveData<>();
        data.setValue(favouritesDataSet);
        return data;
    }

    public void fetchAllProducts(){
        CollectionReference collectionRef = db.collection("favourites");
        collectionRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    Log.d("firebase", String.valueOf(task.getResult()));

                    List<DocumentSnapshot> snapshots = task.getResult().getDocuments();
                    for(DocumentSnapshot favouriteBag : snapshots){
                        long productID = (long) favouriteBag.get("productID");
                        long categoryID = (long) favouriteBag.get("categoryID");
                        double productPrice = (double) favouriteBag.get("productPrice");
                        String productLongName = favouriteBag.get("productLongName").toString();
                        String productShortName = favouriteBag.get("productShortName").toString();
                        Brand brandName = Brand.valueOf(favouriteBag.get("brandName").toString());
                        String productDescription = favouriteBag.get("productDescription").toString();
                        String productDetails = favouriteBag.get("productDetails").toString();
                        String productCare = favouriteBag.get("productCare").toString();
                        ColourType productColourType = ColourType.valueOf(favouriteBag.get("productColourType").toString());
                        long productCountVisit = (long) favouriteBag.get("productCountVisit");
                        boolean isFavourite = (boolean) favouriteBag.get("isFavourite");
                        ArrayList<String> productImages = (ArrayList<String>) favouriteBag.get("productImages");
                        favouritesDataSet.add(new Product(productID, categoryID, productPrice, productLongName, productShortName, brandName,
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
