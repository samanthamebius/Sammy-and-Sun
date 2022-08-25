package com.example.se306_project1.domain;

import androidx.lifecycle.LiveData;
import com.example.se306_project1.models.IProduct;
import com.example.se306_project1.repository.FavouritesRepository;
import com.example.se306_project1.repository.IFavouritesRepository;
import java.util.List;

public class GetFavourites {

    public LiveData<List<IProduct>> getFavourites() {

        IFavouritesRepository repo = new FavouritesRepository();
        LiveData<List<IProduct>> favourites = repo.getFavourites();

        return favourites;
    }
}
