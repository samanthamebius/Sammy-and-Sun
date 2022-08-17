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
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

public class UpdateCountVisit {
    static FirebaseFirestore db = FirebaseFirestore.getInstance();

    public static void updateCountVisit(Product p){
        DocumentReference productRef = db.collection("products").document(""+p.getProductID());
        productRef.update("productCountVisit", FieldValue.increment(1));
    }

}
