package com.example.se306_project1.viewmodel;

import android.content.SharedPreferences;
import android.widget.ImageView;

import com.example.se306_project1.models.IProduct;

import java.util.List;

public interface IDetailsViewModel {
    public IProduct getProductByID(long productID);
    public void updatePopularCount(IProduct product, SharedPreferences sharedPreferences);
    public Boolean displayFavouritesStatus(IProduct product, ImageView icon);
    public List<IProduct> getPopular();
    public void PopularLogic(int sizeOfCurrentPopular, List<IProduct> popularList, IProduct productToSwap, int maxPopularSize, SharedPreferences sharedPreferences);
    public void changeFavouriteStatus(SharedPreferences sharedPreferences);
}
