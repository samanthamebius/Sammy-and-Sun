package com.example.se306_project1.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.se306_project1.models.IProduct;
import java.util.List;

public interface IProductRepository {
    public MutableLiveData<List<IProduct>> getProducts();
    public LiveData<IProduct> getProductByID(long productID);
    public LiveData<List<IProduct>> getProductsByCategoryID(long categoryID);
    public List<IProduct> getProductCache(String key);
    public List<IProduct> getCategoryCache(String key);
    public IProduct getProductByIDCache(String key, long productID);

}
