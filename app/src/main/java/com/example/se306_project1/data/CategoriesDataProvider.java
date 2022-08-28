package com.example.se306_project1.data;

import android.util.Log;
import androidx.annotation.NonNull;

import com.example.se306_project1.models.Category;
import com.example.se306_project1.models.ICategory;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.annotations.NotNull;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * This class is used to provide category data to firestore
 */
public class CategoriesDataProvider {

    // Add documents to firestore
    public static void addCategoriesToFirestore() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        List<ICategory> categoriesList = getCategories();
        for (ICategory aCategory : categoriesList) {
            db.collection("categories").document("" + aCategory.getCategoryID()).set(aCategory).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Log.d("Categories Collection Add", "category " + aCategory.getCategoryID() + " added.");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull @NotNull Exception e) {
                    Log.w("Categories Collection Add", "category " + aCategory.getCategoryID() + " NOT added.");
                }
            });
        }
    }

    public static List<ICategory> getCategories() {
        List<ICategory> categoriesList = new ArrayList<>();

        Map<Integer, String> names = generateCategoryName();

        for (Integer key : names.keySet()) {

            int categoryID = key;

            String categoryName = names.get(key);
            String categoryImage = "c"+key;

            ICategory c = new Category(categoryID, categoryImage, categoryName);
            categoriesList.add(c);
        }

        return categoriesList;
    }

    public static Map<Integer, String> generateCategoryName() {
        Map<Integer, String> names =
                new LinkedHashMap<Integer, String>();
        names.put(0, "Clutches");
        names.put(1, "Tote Bags");
        names.put(2, "Cross-body Bags");
        return names;
    }

}
