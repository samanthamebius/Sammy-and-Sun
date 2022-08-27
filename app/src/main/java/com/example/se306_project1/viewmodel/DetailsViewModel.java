package com.example.se306_project1.viewmodel;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import com.example.se306_project1.models.IProduct;
import com.example.se306_project1.models.Product;
import com.example.se306_project1.repository.IProductRepository;
import com.example.se306_project1.repository.ProductRepository;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class DetailsViewModel extends AndroidViewModel {

    private IProduct product;
    private List<IProduct> popularList;
    IProductRepository productRepository = new ProductRepository(getApplication().getApplicationContext());

    public DetailsViewModel(@NonNull Application application) {
        super(application);
    }

    public IProduct getProductByID(long productID){
        product = productRepository.getProductByIDCache("Products", productID);
        return product;
    }

    public List<IProduct> getPopular(){
        popularList = new ArrayList<>();
        popularList.addAll(productRepository.getProductCache("Popular"));
        return popularList;
    }

    public void updateFavouritesCache (SharedPreferences sharedPreferences, IProduct product, Boolean favouriteStatus){
        Gson gson = new Gson();
        String json = gson.toJson(product);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Log.e("Favourites Cache", json);

        if(favouriteStatus){
            editor.putString("Favourites", json);
        } else {
            editor.remove(json);
        }
    }

    public void removeFromPopularCache (SharedPreferences sharedPreferences, IProduct productToRemove){
        Gson gson = new Gson();
        String json = gson.toJson(productToRemove);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(json);
    }

    public void addToPopularCache (SharedPreferences sharedPreferences, IProduct productToRemove){
        Gson gson = new Gson();
        String json = gson.toJson(productToRemove);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Popular", json);
    }

}