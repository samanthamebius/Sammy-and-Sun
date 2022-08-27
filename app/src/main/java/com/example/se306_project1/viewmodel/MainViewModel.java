package com.example.se306_project1.viewmodel;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import com.example.se306_project1.models.ICategory;
import com.example.se306_project1.models.IProduct;
import com.example.se306_project1.repository.CategoryRepository;
import com.example.se306_project1.repository.FavouritesRepository;
import com.example.se306_project1.repository.ICategoryRepository;
import com.example.se306_project1.repository.IFavouritesRepository;
import com.example.se306_project1.repository.IProductRepository;
import com.example.se306_project1.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;

public class MainViewModel extends AndroidViewModel {
    private List<IProduct> popularList;
    private List<IProduct> favouriteList;
    private List<ICategory> categoriesList;
    IProductRepository productRepository = new ProductRepository(getApplication().getApplicationContext());
    ICategoryRepository categoryRepository = new CategoryRepository(getApplication().getApplicationContext());
    IFavouritesRepository favouritesRepository = new FavouritesRepository(getApplication().getApplicationContext());

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
        favouriteList.addAll(favouritesRepository.getFavouritesCache("Favourites"));
        return favouriteList;
    }

    public List<ICategory> getCategories(){
        categoriesList = new ArrayList<>();
        categoriesList.addAll(categoryRepository.getCategoriesCache("Categories"));
        return categoriesList;
    }
}
