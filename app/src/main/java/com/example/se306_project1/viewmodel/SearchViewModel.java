package com.example.se306_project1.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.se306_project1.models.IProduct;
import com.example.se306_project1.repository.IProductRepository;
import com.example.se306_project1.repository.ProductRepository;

public class SearchViewModel extends AndroidViewModel {

    private IProduct bag;


    IProductRepository productRepository = new ProductRepository(getApplication().getApplicationContext());
    public SearchViewModel(@NonNull Application application) {
        super(application);
    }

    public IProduct getProductByID(long productID){
        bag = productRepository.getProductByIDCache("Products", productID);
        return bag;
    }

}
