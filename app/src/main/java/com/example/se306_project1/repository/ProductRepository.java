package com.example.se306_project1.repository;


import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.se306_project1.models.Brand;
import com.example.se306_project1.models.Clutch;
import com.example.se306_project1.models.ColourType;
import com.example.se306_project1.models.CrossBody;
import com.example.se306_project1.models.Product;
import com.example.se306_project1.models.Tote;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ProductRepository implements IProductRepository{

    public List<Product> productsDataSet = new ArrayList<>();
    public Product productSingle;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    // singleton pattern
    private static ProductRepository instance;
    public static ProductRepository getInstance(){
        if(instance == null){
            instance = new ProductRepository();
        }
        return instance;
    }

    // method to make database queries
    // using LiveData so that in activity class there are no public methods to update stored data
    // mutableLiveData exposes SetValue and PostValue that can modify LiveData, so expose mutableLIveData in viewModels
    public LiveData<List<Product>> getProducts() {
        productsDataSet.clear();
        fetchAllProducts();
        MutableLiveData<List<Product>> data = new MutableLiveData<>();
        data.setValue(productsDataSet);
        return data;
    }

    public LiveData<Product> getProductByID(long productID) {
        fetchProductByID(productID);
        MutableLiveData<Product> data = new MutableLiveData<>();
        data.setValue(productSingle);
        return data;
    }

    public LiveData<List<Product>> getProductByCategoryID(long categoryID) {
        productsDataSet.clear();
        fetchProductsByCategory(categoryID);
        MutableLiveData<List<Product>> data = new MutableLiveData<>();
        data.setValue(productsDataSet);
        return data;
    }

    public void fetchProductByID(long productID){
        String idString = Long.toString(productID);

        DocumentReference documentReference = db.collection("product").document(idString);
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot snap = task.getResult();
                    Log.d("firebase", String.valueOf(task.getResult()));
                    long productID = (long) snap.get("productID");
                    long categoryID = (long) snap.get("categoryID");
                    double productPrice = (double) snap.get("productPrice");
                    String productLongName = snap.get("productLongName").toString();
                    String productShortName = snap.get("productShortName").toString();
                    Brand brandName = Brand.valueOf(snap.get("brandName").toString());
                    String productDescription = snap.get("productDescription").toString();
                    String productDetails = snap.get("productDetails").toString();
                    String productCare = snap.get("productCare").toString();
                    ColourType productColourType = ColourType.valueOf(snap.get("productColourType").toString());
                    long productCountVisit = (long) snap.get("productCountVisit");
                    boolean isFavourite = (boolean) snap.get("isFavourite");
                    ArrayList<String> productImages = (ArrayList<String>) snap.get("productImages");
                    productSingle = determineCategory(productID, categoryID, productPrice, productLongName, productShortName, brandName,
                            productDescription, productDetails, productCare,
                            productColourType, productCountVisit, isFavourite, productImages);
                }
                else{
                    Log.d("firebase", "error getting product by ID data!", task.getException());
                }
            }
        });


    }

    public void fetchAllProducts(){
        CollectionReference collectionRef = db.collection("products");
        collectionRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    Log.d("firebase", String.valueOf(task.getResult()));
                    // getting snapshot of all the documents in the collection
                    List<DocumentSnapshot> snapshots = task.getResult().getDocuments();
                    // looping through the documents
                    for(DocumentSnapshot singleBag : snapshots){
                        long productID = (long) singleBag.get("productID");
                        long categoryID = (long) singleBag.get("categoryID");
                        double productPrice = (double) singleBag.get("productPrice");
                        String productLongName = singleBag.get("productLongName").toString();
                        String productShortName = singleBag.get("productShortName").toString();
                        Brand brandName = Brand.valueOf(singleBag.get("brandName").toString());
                        String productDescription = singleBag.get("productDescription").toString();
                        String productDetails = singleBag.get("productDetails").toString();
                        String productCare = singleBag.get("productCare").toString();
                        ColourType productColourType = ColourType.valueOf(singleBag.get("productColourType").toString());
                        long productCountVisit = (long) singleBag.get("productCountVisit");
                        boolean isFavourite = (boolean) singleBag.get("isFavourite");
                        ArrayList<String> productImages = (ArrayList<String>) singleBag.get("productImages");

                        productsDataSet.add(determineCategory(productID, categoryID, productPrice, productLongName, productShortName, brandName,
                                productDescription, productDetails, productCare,
                                productColourType, productCountVisit, isFavourite, productImages));
                    }
                }
                else{
                    Log.d("firebase", "Error getting all products data!", task.getException());
                }
            }
        });
    }

    public void fetchProductsByCategory(long categoryID){
        String stringID = "category"+ Long.toString(categoryID);
        CollectionReference collectionRef = db.collection(stringID);
        collectionRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    Log.d("firebase", String.valueOf(task.getResult()));
                    List<DocumentSnapshot> snapshots = task.getResult().getDocuments();
                    for(DocumentSnapshot singleBag: snapshots){
                        long productID = (long) singleBag.get("productID");
                        long categoryID = (long) singleBag.get("categoryID");
                        double productPrice = (double) singleBag.get("productPrice");
                        String productLongName = singleBag.get("productLongName").toString();
                        String productShortName = singleBag.get("productShortName").toString();
                        Brand brandName = Brand.valueOf(singleBag.get("brandName").toString());
                        String productDescription = singleBag.get("productDescription").toString();
                        String productDetails = singleBag.get("productDetails").toString();
                        String productCare = singleBag.get("productCare").toString();
                        ColourType productColourType = ColourType.valueOf(singleBag.get("productColourType").toString());
                        long productCountVisit = (long) singleBag.get("productCountVisit");
                        boolean isFavourite = (boolean) singleBag.get("isFavourite");
                        ArrayList<String> productImages = (ArrayList<String>) singleBag.get("productImages");

                        productsDataSet.add(determineCategory(productID, categoryID, productPrice, productLongName, productShortName, brandName,
                                productDescription, productDetails, productCare,
                                productColourType, productCountVisit, isFavourite, productImages));
                    }
                }
                else {
                    Log.d("firebase", "error getting data by category!", task.getException());
                }
            }
        });
    }


    public Product determineCategory (long productID, long categoryID, double productPrice,
                                      String productLongName, String productShortName, Brand brandName,
                                      String productDescription, String productDetails, String productCare,
                                      ColourType productColourType, long productCountVisit, boolean isFavourite, ArrayList<String> productImages){
        Product bag;
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
