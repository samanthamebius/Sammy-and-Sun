package com.example.se306_project1.domain;

import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;

import com.example.se306_project1.MainActivity;
import com.example.se306_project1.models.Product;
import com.example.se306_project1.repository.IProductRepository;
import com.example.se306_project1.repository.ProductRepository;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.annotations.NotNull;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.LinkedHashMap;
import java.util.Map;

public class UpdateFavourite {
    static FirebaseFirestore db = FirebaseFirestore.getInstance();

    public static void updateFavourite(Product p, Boolean favouriteStatus){
        if(favouriteStatus == false) {
            System.out.println("detected false");
            updateFavouriteBoolean(p, true);
            addToFavouriteCollection(p);
        } else {
            updateFavouriteBoolean(p, false);
            removeFromFavouriteCollection(p);
        }
    }

    public static void updateFavouriteBoolean(Product p, Boolean newStatus){
        DocumentReference productRef = db.collection("products").document(""+p.getProductID());

        productRef
                .update("isFavourite", newStatus)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("Favourites Boolean ", "product " + p.getProductID() + " updated.");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("Favourites Boolean ", "product " + p.getProductID() + " NOT updated.");
                    }
                });
    }

    public static void removeFromFavouriteCollection(Product p) {
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

    public static void addToFavouriteCollection(Product p) {
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
