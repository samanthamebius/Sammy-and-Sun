package com.example.se306_project1.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.se306_project1.models.Category;
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

public class CategoryRepository implements ICategoryRepository{

    private List<Category> categoryGroups = new ArrayList<>();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DatabaseReference dref = FirebaseDatabase.getInstance().getReference();

    // singleton pattern
    private CategoryRepository instance;
    public CategoryRepository getInstance(){
        if(instance == null){
            instance = new CategoryRepository();
        }
        return instance;
    }

    public MutableLiveData<List<Category>> getCategories() {
        categoryGroups.clear();
        fetchCategories();
        MutableLiveData<List<Category>> data = new MutableLiveData<>();
        data.setValue(categoryGroups);
        return data;
    }

    public void fetchCategories(){
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
                        categoryGroups.add(new Category(categoryID, categoryImage, categoryName));
                    }
                }
                else {
                    Log.d("Firebase", "error getting data!", task.getException());
                }
            }
        });
    }
}
