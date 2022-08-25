package com.example.se306_project1.repository;

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
import com.example.se306_project1.models.Tote;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.List;

public class FavouritesRepository implements IFavouritesRepository{

    public List<IProduct> favouritesDataSet = new ArrayList<>();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference collectionRef = db.collection("favourites");

    // singleton pattern
    private static FavouritesRepository instance;
    public static FavouritesRepository getInstance(){
        if(instance == null){
            instance = new FavouritesRepository();
        }
        return instance;
    }

    public LiveData<List<IProduct>> getFavourites() {
        favouritesDataSet.clear();
        MutableLiveData<List<IProduct>> data = new MutableLiveData<>();
        fetchAllFavourites(data);
        return data;
    }

    public void fetchAllFavourites(MutableLiveData<List<IProduct>> data){

        collectionRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    Log.d("firebase favourites", String.valueOf(task.getResult()));
                    List<DocumentSnapshot> snapshots = task.getResult().getDocuments();

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

                                    favouritesDataSet.add(determineCategory(productID, categoryID, productPrice, productLongName, productShortName, brandName,
                                            productDescription, productDetails, productCare,
                                            productColourType, productCountVisit, isFavourite, productImages));

                                    data.setValue(favouritesDataSet);
                                }
                            }
                        });
                    }
                }
                else{
                    Log.d("firebase favourites", "Error getting favourites data!", task.getException());
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
