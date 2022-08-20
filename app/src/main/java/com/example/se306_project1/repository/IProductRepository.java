package com.example.se306_project1.repository;

import androidx.lifecycle.LiveData;

import com.example.se306_project1.models.Product;

import java.util.List;

public interface IProductRepository {
    public LiveData<List<Product>> getProducts();
    public LiveData<Product> getProductByID(long productID);
    public LiveData<List<Product>> getProductsByCategoryID(long categoryID);

}
