package com.example.se306_project1.repository;

import androidx.lifecycle.LiveData;
import com.example.se306_project1.models.IProduct;
import java.util.List;

public interface IFavouritesRepository {
    LiveData<List<IProduct>> getFavourites();
    List<IProduct> getFavouritesCache(String key);
    void addToFavouriteCollection(IProduct product);
    void removeFromFavouriteCollection(IProduct product);
    void updateFavouriteBoolean(IProduct product, Boolean newStatus);
}
