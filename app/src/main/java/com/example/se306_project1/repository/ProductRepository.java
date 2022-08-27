package com.example.se306_project1.repository;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.se306_project1.models.Brand;
import com.example.se306_project1.models.Clutch;
import com.example.se306_project1.models.ColourType;
import com.example.se306_project1.models.CrossBody;
import com.example.se306_project1.models.IProduct;
//import com.example.se306_project1.models.Product;
import com.example.se306_project1.models.Product;
import com.example.se306_project1.models.Tote;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.common.reflect.TypeToken;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ProductRepository implements IProductRepository{


    private static Context context;
    public List<IProduct> productsDataSet = new ArrayList<>();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference productColRef = db.collection("products");
    private long productID;
    private long categoryID;


    public ProductRepository(Context context){
        this.context = context;
    }

    // singleton pattern

    private static ProductRepository instance;
    public static IProductRepository getInstance(){
        if(instance == null){
            instance = new ProductRepository(context);
        }
        return instance;
    }

    // method to make database queries
    // using LiveData so that in activity class there are no public methods to update stored data
    // mutableLiveData exposes SetValue and PostValue that can modify LiveData, so expose mutableLIveData in viewModels
    public MutableLiveData<List<IProduct>> getProducts() {
        productsDataSet.clear();
        MutableLiveData<List<IProduct>> data = new MutableLiveData<>();
        fetchAllProducts(data);
        return data;
    }

    public LiveData<IProduct> getProductByID(long productID) {
        productsDataSet.clear();
        this.productID = productID;
        MutableLiveData<IProduct> data = new MutableLiveData<>();
        fetchProductByID(data);
        return data;
    }

    public LiveData<List<IProduct>> getProductsByCategoryID(long categoryID) {
        productsDataSet.clear();
        this.categoryID = categoryID;
        MutableLiveData<List<IProduct>> data = new MutableLiveData<>();
        fetchProductsByCategory(data);
        return data;
    }

    @Override
    public List<IProduct> getProductCache(String key) {
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

    @Override
    public List<IProduct> getCategoryCache(String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("SharedPref", Context.MODE_PRIVATE);
        List<IProduct> arrayItems = new ArrayList<>();
        String serializedObject = sharedPreferences.getString(key, null);
        if (serializedObject != null) {
            Gson gson = new Gson();
            Type type = new TypeToken<List<Product>>(){}.getType();

            if(key.equals("0")){
                type = new TypeToken<List<Clutch>>(){}.getType();
            }
            else if(key.equals("1")){
                type = new TypeToken<List<Tote>>(){}.getType();
            }
            else{
                type = new TypeToken<List<CrossBody>>(){}.getType();
            }

            arrayItems = gson.fromJson(serializedObject, type);
        }
        return arrayItems;
    }




    public void fetchProductByID(MutableLiveData<IProduct> data){
        String idString = Long.toString(productID);
        DocumentReference documentReference = productColRef.document(idString);
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot snap = task.getResult();

                    Log.d("firebase fetchProductByID", String.valueOf(task.getResult().get("productLongName")));

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
                    productsDataSet.add(determineCategory(productID, categoryID, productPrice, productLongName, productShortName, brandName,
                            productDescription, productDetails, productCare,
                            productColourType, productCountVisit, isFavourite, productImages));
                }
                else{
                    Log.d("firebase fetch product", "error getting product by ID data!", task.getException());
                }

                data.setValue(productsDataSet.get(0));
            }
        });


    }

    public void fetchAllProducts(MutableLiveData<List<IProduct>> data){

        productColRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    Log.d("firebase fetch all products", String.valueOf(task.getResult()));
                    // getting snapshot of all the documents in the collection
                    List<DocumentSnapshot> snapshots = task.getResult().getDocuments();
                    // looping through the documents
                    productsDataSet.clear();
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
                data.setValue(productsDataSet);
            }
        });
    }

    public void fetchProductsByCategory(MutableLiveData<List<IProduct>> data){
        String stringID = "category"+ Long.toString(categoryID);
        CollectionReference collectionRef = db.collection(stringID);
        collectionRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    Log.d("firebase fetch product by category", String.valueOf(task.getResult()));
                    List<DocumentSnapshot> snapshots = task.getResult().getDocuments();
                    productsDataSet.clear();
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
                data.setValue(productsDataSet);
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
