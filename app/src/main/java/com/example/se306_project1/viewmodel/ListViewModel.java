package com.example.se306_project1.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.se306_project1.models.IProduct;
import com.example.se306_project1.repository.IProductRepository;
import com.example.se306_project1.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;

public class ListViewModel extends AndroidViewModel {
    List<IProduct> productsList;
    IProductRepository productRepository = new ProductRepository(getApplication().getApplicationContext());

    public ListViewModel(@NonNull Application application) {
        super(application);
    }

    public List<IProduct> getProductsList(String categoryName){
        productsList = new ArrayList<>();
        productsList.clear();
        productsList.addAll(productRepository.getProductCache("Clutches"));
        return productsList;
    }

}
