package com.example.se306_project1.domain;

import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.se306_project1.models.Product;
import com.example.se306_project1.repository.IProductRepository;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.annotations.NotNull;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Source;

import java.util.LinkedHashMap;
import java.util.Map;

public class UpdateFavourite {

    // Need a method to find current boolean status and then call the functions in this class accordingly

//    public void updateFavourite(Product p){
//
//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//        DocumentSnapshot snapshot = db.collection("products").document(""+p.getProductID()).get();
//        boolean isFavourite = (boolean) snapshot.getBoolean("isFavourite");
//    }


    public void updateFavouriteBoolean(Product p, Boolean newStatus){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference productRef = db.collection("products").document(""+p.getProductID());

        productRef
                .update("isFavourite", newStatus)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("Favourites Boolean ", "product " + p.getProductID() + " updated to true.");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("Favourites Boolean ", "product " + p.getProductID() + " NOT updated to true.");
                    }
                });
    }

    public void removeFromFavouriteCollection(Product p) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("favourites").document("" + p.getProductID()).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("Favourites Collection ", "product " + p.getProductID() + " removed.");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("Favourites Collection ", "product " + p.getProductID() + " NOT removed.");
                    }
                });
    }

    public void addToFavouriteCollection(Product p) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        DocumentReference productRef = db.document("products/"+p.getProductID());

        Map<String, DocumentReference> favourite = new LinkedHashMap<String, DocumentReference>();
        favourite.put("ProductRef", productRef);

        db.collection("favourites").document("" + p.getProductID()).set(favourite).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.d("Favourites Collection Add", "product " + p.getProductID() + " added.");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Log.w("Favourites Collection Add", "product " + p.getProductID() + " NOT added.");
            }
        });
    }
}
