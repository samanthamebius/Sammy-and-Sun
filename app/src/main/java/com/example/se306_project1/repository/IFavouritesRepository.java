package com.example.se306_project1.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.se306_project1.models.Category;
import com.example.se306_project1.models.Product;

import java.util.List;

public interface IFavouritesRepository {

    LiveData<List<Product>> getFavourites();

}
