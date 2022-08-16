package com.example.se306_project1.domain;

import androidx.lifecycle.LiveData;

import com.example.se306_project1.models.Product;
import com.example.se306_project1.repository.FavouritesRepository;
import com.example.se306_project1.repository.IFavouritesRepository;

import java.util.List;

public class GetFavourites {

    public LiveData<List<Long>> getFavourites() {

        IFavouritesRepository repo = new FavouritesRepository();
        LiveData<List<Long>> favourites = repo.getFavourites();

        return favourites;
    }
}
