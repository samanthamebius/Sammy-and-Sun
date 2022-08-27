package com.example.se306_project1.viewmodel;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import com.example.se306_project1.models.IProduct;
import com.example.se306_project1.models.Product;
import com.example.se306_project1.repository.FavouritesRepository;
import com.example.se306_project1.repository.IFavouritesRepository;
import com.example.se306_project1.repository.IPopularRepository;
import com.example.se306_project1.repository.IProductRepository;
import com.example.se306_project1.repository.PopularRepository;
import com.example.se306_project1.repository.ProductRepository;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class DetailsViewModel extends AndroidViewModel {

    private IProduct product;
    private List<IProduct> popularList;
    private List<IProduct> favouritesList;
    private List<IProduct> productsList;
    IProductRepository productRepository = new ProductRepository(getApplication().getApplicationContext());
    IPopularRepository popularRepository = new PopularRepository(getApplication().getApplicationContext());
    IFavouritesRepository favouritesRepository = new FavouritesRepository(getApplication().getApplicationContext());


    public DetailsViewModel(@NonNull Application application) {
        super(application);
    }

    public IProduct getProductByID(long productID){
        product = productRepository.getProductByIDCache("Products", productID);
        return product;
    }

    public List<IProduct> getProducts(){
        productsList = new ArrayList<>();
        productsList.addAll(productRepository.getProductCache("Products"));
        return productsList;
    }

    public List<IProduct> getPopular(){
        popularList = new ArrayList<>();
        popularList.addAll(popularRepository.getPopularCache("Popular"));
        return popularList;
    }

    public List<IProduct> getFavourites(){
        favouritesList = new ArrayList<>();
        favouritesList.addAll(favouritesRepository.getFavouritesCache("Favourites"));
        return favouritesList;
    }

    public void updatePopularCount(IProduct product, SharedPreferences sharedPreferences){
        popularRepository.updateCountVisit(product);
        renewProductCache(sharedPreferences);
    }

    public void updateFavouritesCache (SharedPreferences sharedPreferences, IProduct product, Boolean favouriteStatus){
        Gson gson = new Gson();
        String json = gson.toJson(product);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if(favouriteStatus){
            editor.putString("Favourites", json);
        } else {
            editor.remove(json);
        }
    }

    public void PopularLogic(int sizeOfCurrentPopular, List<IProduct> popularList, IProduct productToSwap, int maxPopularSize, SharedPreferences sharedPreferences) {
        // first check is product to swap is in current popular list
        popularList.sort(Comparator.comparing(IProduct::getProductCountVisit));

        Log.e("view model size of current popular", Integer.toString(sizeOfCurrentPopular));

        if(!popularList.contains(productToSwap)){
            IProduct leastViewProduct = popularList.get(0);
            Log.e("view model count of current", Long.toString(productToSwap.getProductCountVisit()));
            Log.e("view model count of least", Long.toString(leastViewProduct.getProductCountVisit()));
            if (sizeOfCurrentPopular < maxPopularSize){
                // add product to Swap to firebase
                popularRepository.addProductToPopular(productToSwap);
                addProductToPopularCache(sharedPreferences, productToSwap);
                // add product to swap to cache
            }
            else if(productToSwap.getProductCountVisit() > leastViewProduct.getProductCountVisit()){
                Log.e("view model logic, least view product: ", leastViewProduct.getProductShortName());
                Log.e("view model logic, current swap: ", productToSwap.getProductShortName());
                popularRepository.addProductToPopular(productToSwap);
                popularRepository.removeProductFromPopular(leastViewProduct);
                swapProductsInPopularCache(sharedPreferences, leastViewProduct, productToSwap);
            }
        }


    }



    // get current list of popular
    // get current list of favourites


    public void renewProductCache(SharedPreferences sharedPreferences){

        product.increaseProductViewCount();
        productsList = getProducts();
        for(int i = 0; i < productsList.size(); i++){
            if (productsList.get(i).getProductID() == product.getProductID()){
                productsList.set(i, product);
            }
        }

        renewSharedPreferences(sharedPreferences, productsList, "Products");

    }

    public void addProductToPopularCache(SharedPreferences sharedPreferences, IProduct product){
        popularList = getPopular();
        popularList.add(product);
        renewSharedPreferences(sharedPreferences, popularList, "Popular");
    }

    public void swapProductsInPopularCache(SharedPreferences sharedPreferences, IProduct removeProduct, IProduct addProduct){
        popularList = getPopular();
        for(int i = 0; i < popularList.size(); i++){
            if(popularList.get(i).getProductID() == removeProduct.getProductID()){
                popularList.remove(i);
            }
        }
        popularList.add(addProduct);
        renewSharedPreferences(sharedPreferences, popularList, "Popular");
    }

    public void renewSharedPreferences(SharedPreferences sharedPreferences, List<IProduct> list, String key){
        Gson gson = new Gson();
        String json = gson.toJson(list);
        if(sharedPreferences !=null){
            SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
            prefsEditor.putString(key, json);
            prefsEditor.commit();
        }
    }





}