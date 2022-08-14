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

public class FavouritesRepository implements IFavouritesRepository{

    public List<Long> favouritesDataSet = new ArrayList<>();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();


    // singleton pattern
    private static ProductRepository instance;
    public static ProductRepository getInstance(){
        if(instance == null){
            instance = new ProductRepository();
        }
        return instance;
    }

    public LiveData<List<Long>> getFavourites() {
        favouritesDataSet.clear();
        fetchAllFavourites();
        MutableLiveData<List<Long>> data = new MutableLiveData<>();
        data.setValue(favouritesDataSet);
        return data;
    }

    public void fetchAllFavourites(){
        CollectionReference collectionRef = db.collection("favourites");
        collectionRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    Log.d("firebase", String.valueOf(task.getResult()));

                    List<DocumentSnapshot> snapshots = task.getResult().getDocuments();
                    for(DocumentSnapshot favouriteBag : snapshots){
                        long productID = (long) favouriteBag.get("productID");
                        favouritesDataSet.add(productID);
                    }
                }
                else{
                    Log.d("firebase", "Error getting favourites data!", task.getException());
                }
            }
        });
    }
}
