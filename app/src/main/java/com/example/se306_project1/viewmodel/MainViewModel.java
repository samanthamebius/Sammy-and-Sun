package com.example.se306_project1.viewmodel;

import androidx.lifecycle.ViewModel;

import com.example.se306_project1.models.IProduct;
import com.example.se306_project1.repository.IProductRepository;
import com.example.se306_project1.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;

public class MainViewModel extends ViewModel {
    private List<IProduct> popularList;
    private List<IProduct> favouriteList;
    IProductRepository productRepository = ProductRepository.getInstance();

    public List<IProduct> getPopular(){
        popularList = new ArrayList<>();
        popularList = productRepository.getProductCache("Popular");
        return popularList;
    }

    public List<IProduct> getFavourites(){
        favouriteList = new ArrayList<>();
        favouriteList = productRepository.getProductCache("Favourites");
        return favouriteList;
    }
}
