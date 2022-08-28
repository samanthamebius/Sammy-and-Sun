package com.example.se306_project1.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.se306_project1.models.IProduct;
import java.util.List;

public interface IProductRepository {
    MutableLiveData<List<IProduct>> getProducts();
    LiveData<IProduct> getProductByID(long productID);
    LiveData<List<IProduct>> getProductsByCategoryID(long categoryID);
    List<IProduct> getProductCache(String key);
    List<IProduct> getCategoryCache(String key);
    IProduct getProductByIDCache(String key, long productID);
}
