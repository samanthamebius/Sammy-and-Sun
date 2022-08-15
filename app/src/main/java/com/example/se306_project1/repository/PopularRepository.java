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

    public List<Long> popularDataSet = new ArrayList<>();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    // singleton pattern
    private static PopularRepository instance;
    public static PopularRepository getInstance(){
        if(instance == null){
            instance = new PopularRepository();
        }
        return instance;
    }

    public LiveData<List<Long>> getPopular() {
        popularDataSet.clear();
        fetchAllPopular();
        MutableLiveData<List<Long>> data = new MutableLiveData<>();
        data.setValue(popularDataSet);
        return data;
    }

    public void fetchAllPopular(){
        CollectionReference collectionRef = db.collection("popular");
        collectionRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    Log.d("firebase", String.valueOf(task.getResult()));
                    List<DocumentSnapshot> snapshots = task.getResult().getDocuments();
                    // only getting the foreign keys of the data
                    for(DocumentSnapshot popularBag : snapshots){
                        long productID = (long) popularBag.get("productID");
                        popularDataSet.add(productID);
                    }
                }
                else{
                    Log.d("firebase", "Error getting popular data!", task.getException());
                }
            }
        });
    }
}
