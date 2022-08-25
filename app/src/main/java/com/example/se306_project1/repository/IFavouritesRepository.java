package com.example.se306_project1.repository;

import androidx.lifecycle.LiveData;
import com.example.se306_project1.models.IProduct;
import java.util.List;

public interface IFavouritesRepository {
    public LiveData<List<IProduct>> getFavourites();
}
