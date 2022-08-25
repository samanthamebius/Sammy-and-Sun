package com.example.se306_project1.repository;

import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.se306_project1.models.Category;
import com.example.se306_project1.models.ICategory;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.List;

public class CategoryRepository implements ICategoryRepository{

    private List<ICategory> categoryGroups = new ArrayList<>();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference colRef = db.collection("categories");

    // singleton pattern
    private static CategoryRepository instance;
    public static CategoryRepository getInstance(){
        if(instance == null){
            instance = new CategoryRepository();
        }
        return instance;
    }

    public LiveData<List<ICategory>> getCategories() {
        categoryGroups.clear();
        MutableLiveData<List<ICategory>> data = new MutableLiveData<>();
        fetchCategories(data);
        return data;
    }

    public void fetchCategories(MutableLiveData<List<ICategory>> data){
        colRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    Log.d("firebase fetch categories", String.valueOf(task.getResult()));
                    List<DocumentSnapshot> snapshots = task.getResult().getDocuments();
                    for(DocumentSnapshot snap: snapshots){
                        long categoryID = (long) snap.get("categoryID");
                        String categoryImage = snap.get("categoryImage").toString();
                        String categoryName = snap.get("categoryName").toString();
                        categoryGroups.add(new Category(categoryID, categoryImage, categoryName));
                    }
                }
                else {
                    Log.d("Firebase", "error getting categories!", task.getException());
                }

                data.setValue(categoryGroups);
            }
        });
    }
}
