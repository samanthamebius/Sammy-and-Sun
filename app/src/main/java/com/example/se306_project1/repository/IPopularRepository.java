package com.example.se306_project1.repository;

import androidx.lifecycle.LiveData;
import com.example.se306_project1.models.IProduct;
import java.util.List;

public interface IPopularRepository {
    LiveData<List<IProduct>> getPopular();
    List<IProduct> getPopularCache(String key);
    void updateCountVisit(IProduct product);
    void removeProductFromPopular(IProduct product);
    void addProductToPopular(IProduct product);
}
