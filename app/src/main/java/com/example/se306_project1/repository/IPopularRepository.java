package com.example.se306_project1.repository;

import androidx.lifecycle.LiveData;
import com.example.se306_project1.models.IProduct;
import java.util.List;

public interface IPopularRepository {
    public LiveData<List<IProduct>> getPopular();
    public List<IProduct> getPopularCache(String key);
    public void updateCountVisit(IProduct product);
    public void removeProductFromPopular(IProduct product);
    public void addProductToPopular(IProduct product);
}
