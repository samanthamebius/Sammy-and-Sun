package com.example.se306_project1.viewmodel;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import com.example.se306_project1.models.IProduct;
import com.example.se306_project1.repository.IProductRepository;
import com.example.se306_project1.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;

public class MainViewModel extends AndroidViewModel {
    private List<IProduct> popularList;
    private List<IProduct> favouriteList;
    IProductRepository productRepository = new ProductRepository(getApplication().getApplicationContext());

    public MainViewModel(@NonNull Application application) {
        super(application);
    }


    public List<IProduct> getPopular(){
        popularList = new ArrayList<>();
        popularList.addAll(productRepository.getProductCache("Popular"));
        return popularList;
    }

    public List<IProduct> getFavourites(){
        favouriteList = new ArrayList<>();
        favouriteList.addAll(productRepository.getProductCache("Favourites"));
        return favouriteList;
    }
}
