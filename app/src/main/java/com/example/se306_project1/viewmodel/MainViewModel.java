package com.example.se306_project1.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import com.example.se306_project1.models.ICategory;
import com.example.se306_project1.models.IProduct;
import com.example.se306_project1.repository.CategoryRepository;
import com.example.se306_project1.repository.FavouritesRepository;
import com.example.se306_project1.repository.ICategoryRepository;
import com.example.se306_project1.repository.IFavouritesRepository;
import com.example.se306_project1.repository.IPopularRepository;
import com.example.se306_project1.repository.PopularRepository;
import java.util.ArrayList;
import java.util.List;

/**
 * View Model layer for the MainActivity
 */
public class MainViewModel extends AndroidViewModel implements IMainViewModel {
    private List<IProduct> popularList;
    private List<IProduct> favouriteList;
    private List<ICategory> categoriesList;
    IPopularRepository popularRepository = new PopularRepository(getApplication().getApplicationContext());
    ICategoryRepository categoryRepository = new CategoryRepository(getApplication().getApplicationContext());
    IFavouritesRepository favouritesRepository = new FavouritesRepository(getApplication().getApplicationContext());

    public MainViewModel(@NonNull Application application) {
        super(application);
    }

    public List<IProduct> getPopular(){
        popularList = new ArrayList<>();
        popularList.addAll(popularRepository.getPopularCache("Popular"));
        return popularList;
    }

    public List<IProduct> getFavourites(){
        favouriteList = new ArrayList<>();
        favouriteList.addAll(favouritesRepository.getFavouritesCache("Favourites"));
        return favouriteList;
    }

    public List<ICategory> getCategories(){
        categoriesList = new ArrayList<>();
        categoriesList.addAll(categoryRepository.getCategoriesCache("Categories"));
        return categoriesList;
    }
}
