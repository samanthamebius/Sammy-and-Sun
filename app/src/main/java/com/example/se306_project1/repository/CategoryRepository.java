package com.example.se306_project1.repository;

import android.util.Log;

import androidx.annotation.NonNull;

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

public class CategoryRepository {

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

    public void fetchCategories (){
        CollectionReference colRef = db.collection("categories");
        colRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    Log.d("firebase", String.valueOf(task.getResult()));
                    List<DocumentSnapshot> snapshots = task.getResult().getDocuments();
                    for(DocumentSnapshot snap: snapshots){
                        long categoryID = (long) snap.get("categoryID");
                        String categoryImage = snap.get("categoryImage").toString();
                        String categoryName = snap.get("categoryName").toString();
                    }

                }
                else {
                    Log.d("Firebase", "error getting data!", task.getException());
                }
            }
        });
    }
}
