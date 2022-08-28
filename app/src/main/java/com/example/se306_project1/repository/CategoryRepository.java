package com.example.se306_project1.repository;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.se306_project1.models.Category;
import com.example.se306_project1.models.ICategory;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.common.reflect.TypeToken;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CategoryRepository implements ICategoryRepository{

    private static Context context;
    private List<ICategory> categoryGroups = new ArrayList<>();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference colRef = db.collection("categories");

    public CategoryRepository(Context context){
        this.context = context;
    }

    // singleton pattern
    private static CategoryRepository instance;
    public static CategoryRepository getInstance(){
        if(instance == null){
            instance = new CategoryRepository(context);
        }
        return instance;
    }

    /**
     * Get categories data from firebase collection.
     * @return  LiveData list of ICategory
     */
    public LiveData<List<ICategory>> getCategories() {
        categoryGroups.clear();
        MutableLiveData<List<ICategory>> data = new MutableLiveData<>();
        fetchCategories(data);
        return data;
    }

    @Override
    public List<ICategory> getCategoriesCache(String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("SharedPref", Context.MODE_PRIVATE);
        List<ICategory> arrayItems = new ArrayList<>();
        String serializedObject = sharedPreferences.getString(key, null);
        if (serializedObject != null) {
            Gson gson = new Gson();
            Type type = new TypeToken<List<Category>>(){}.getType();
            arrayItems = gson.fromJson(serializedObject, type);
        }
        return arrayItems;
    }

    public void fetchCategories(MutableLiveData<List<ICategory>> data){
        colRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    Log.d("firebase fetch categories", String.valueOf(task.getResult()));
                    List<DocumentSnapshot> snapshots = task.getResult().getDocuments();
                    categoryGroups.clear();
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
