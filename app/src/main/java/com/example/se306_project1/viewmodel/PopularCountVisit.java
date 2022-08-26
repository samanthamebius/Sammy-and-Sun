package com.example.se306_project1.viewmodel;

import android.util.Log;
import androidx.annotation.NonNull;
import com.example.se306_project1.models.IProduct;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.annotations.NotNull;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.LinkedHashMap;
import java.util.Map;

public class PopularCountVisit {
    static FirebaseFirestore db = FirebaseFirestore.getInstance();

    public static void updateCountVisit(IProduct p){
        DocumentReference productRef = db.collection("products").document(""+p.getProductID());
        productRef.update("productCountVisit", FieldValue.increment(1));
    }

    public static void addToPopularCollection(IProduct p) {
        DocumentReference productRef = db.document("products/"+p.getProductID());

        Map<String, DocumentReference> popular = new LinkedHashMap<String, DocumentReference>();
        popular.put("ProductRef", productRef);

        db.collection("popular").document("" + p.getProductID()).set(popular).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.d("Popular Collection Add", "product " + p.getProductID() + " added.");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Log.w("Popular Collection Add", "product " + p.getProductID() + " NOT added.");
            }
        });
    }


    public static void removeFromPopularCollection(IProduct p) {
        db.collection("popular").document("" + p.getProductID()).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("Popular Collection ", "product " + p.getProductID() + " removed.");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("Popular Collection ", "product " + p.getProductID() + " NOT removed.");
                    }
                });
    }

}
