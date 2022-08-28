package com.example.se306_project1.repository;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.se306_project1.models.Brand;
import com.example.se306_project1.models.Clutch;
import com.example.se306_project1.models.ColourType;
import com.example.se306_project1.models.CrossBody;
import com.example.se306_project1.models.IProduct;
import com.example.se306_project1.models.Product;
import com.example.se306_project1.models.Tote;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.common.reflect.TypeToken;
import com.google.firebase.database.annotations.NotNull;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class PopularRepository implements IPopularRepository{

    private static Context context;
    public List<IProduct> popularDataSet = new ArrayList<>();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference collectionRef = db.collection("popular");

    public PopularRepository(Context context){
        this.context = context;
    }
    // singleton pattern
    private static PopularRepository instance;
    public static PopularRepository getInstance(){
        if(instance == null){
            instance = new PopularRepository(context);
        }
        return instance;
    }

    public LiveData<List<IProduct>> getPopular() {
        popularDataSet.clear();
        MutableLiveData<List<IProduct>> data = new MutableLiveData<>();
        fetchAllPopular(data);
        return data;
    }

    @Override
    public List<IProduct> getPopularCache(String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("SharedPref", Context.MODE_PRIVATE);
        List<IProduct> arrayItems = new ArrayList<>();
        String serializedObject = sharedPreferences.getString(key, null);
        if (serializedObject != null) {
            Gson gson = new Gson();
            Type type = new TypeToken<List<Product>>(){}.getType();
            arrayItems = gson.fromJson(serializedObject, type);
        }
        return arrayItems;
    }

    public void addProductToPopular(IProduct p){
        DocumentReference productRef = FirebaseFirestore.getInstance().document("products/"+p.getProductID());
        Map<String, DocumentReference> popular = new LinkedHashMap<String, DocumentReference>();
        popular.put("ProductRef", productRef);

        db.collection("popular").document("" + p.getProductID()).set(popular).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.e("Popular Collection Add", "product " + p.getProductID() + " added.");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Log.e("Popular Collection Add", "product " + p.getProductID() + " NOT added.");
            }
        });
    }

    public void removeProductFromPopular(IProduct p){
        FirebaseFirestore.getInstance().collection("popular").document("" + p.getProductID()).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
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

    @Override
    public void updateCountVisit(IProduct p){
        DocumentReference productRef = FirebaseFirestore.getInstance().collection("products").document(""+p.getProductID());
        productRef.update("productCountVisit", FieldValue.increment(1));
    }


    public void fetchAllPopular(MutableLiveData<List<IProduct>> data){

        collectionRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    Log.d("firebase popular", String.valueOf(task.getResult()));
                    List<DocumentSnapshot> snapshots = task.getResult().getDocuments();

                    popularDataSet.clear();
                    for(DocumentSnapshot favouriteBagReference : snapshots){
                        // getting foreign key reference
                        DocumentReference docRef = (DocumentReference) favouriteBagReference.get("ProductRef");
                        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                            @Override
                            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                                if(value.exists()){
                                    long productID = (long) value.get("productID");
                                    long categoryID = (long) value.get("categoryID");
                                    double productPrice = (double) value.get("productPrice");
                                    String productLongName = value.get("productLongName").toString();
                                    String productShortName = value.get("productShortName").toString();
                                    Brand brandName = Brand.valueOf(value.get("brandName").toString());
                                    String productDescription = value.get("productDescription").toString();
                                    String productDetails = value.get("productDetails").toString();
                                    String productCare = value.get("productCare").toString();
                                    ColourType productColourType = ColourType.valueOf(value.get("productColourType").toString());
                                    long productCountVisit = (long) value.get("productCountVisit");
                                    boolean isFavourite = (boolean) value.get("isFavourite");
                                    ArrayList<String> productImages = (ArrayList<String>) value.get("productImages");

                                    popularDataSet.add(determineCategory(productID, categoryID, productPrice, productLongName, productShortName, brandName,
                                            productDescription, productDetails, productCare,
                                            productColourType, productCountVisit, isFavourite, productImages));

                                    data.setValue(popularDataSet);
                                }
                            }
                        });
                    }
                }
                else{
                    Log.d("firebase popular", "Error getting popular data!", task.getException());
                }
            }
        });

    }


    public IProduct determineCategory (long productID, long categoryID, double productPrice,
                                      String productLongName, String productShortName, Brand brandName,
                                      String productDescription, String productDetails, String productCare,
                                      ColourType productColourType, long productCountVisit, boolean isFavourite, ArrayList<String> productImages){
        IProduct bag;
        if(categoryID == 0){
            // type is clutch
            bag = new Clutch(productID, categoryID, productPrice, productLongName, productShortName, brandName,
                    productDescription, productDetails, productCare,
                    productColourType, productCountVisit, isFavourite, productImages);

        }
        else if (categoryID == 1){
            // type is crossBody
            bag = new CrossBody(productID, categoryID, productPrice, productLongName, productShortName, brandName,
                    productDescription, productDetails, productCare,
                    productColourType, productCountVisit, isFavourite, productImages);
        }
        else{
            // type is tote
            bag = new Tote(productID, categoryID, productPrice, productLongName, productShortName, brandName,
                    productDescription, productDetails, productCare,
                    productColourType, productCountVisit, isFavourite, productImages);

        }

        return bag;
    }
}
