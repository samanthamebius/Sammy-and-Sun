package com.example.se306_project1.domain;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.se306_project1.models.Product;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class UpdateCountVisit {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    long currentCount, newCount;

    public void updateCountVisit(Product p){
        currentCount = getCurrentCount(p);

        newCount = currentCount+1;

        updateCountVisitFireStore(p, newCount);
    }

    public long getCurrentCount(Product p){
        DocumentReference productRef = db.collection("products").document(""+p.getProductID());

        productRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot snap = task.getResult();
                    Log.d("firebase", String.valueOf(task.getResult()));
                    long productCountVisit = (long) snap.get("productCountVisit");
                    currentCount = productCountVisit;
                }
                else{
                    Log.d("firebase", "error getting data!", task.getException());
                }
            }
        });
        return currentCount;
    }

    public void updateCountVisitFireStore(Product p, Long newCount){
        DocumentReference productRef = db.collection("products").document(""+p.getProductID());

        productRef
                .update("productCountVisit", newCount)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("Count Visit ", "product " + p.getProductID() + " incremented.");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("Count Visit ", "product " + p.getProductID() + " NOT incremented.");
                    }
                });
    }
}
