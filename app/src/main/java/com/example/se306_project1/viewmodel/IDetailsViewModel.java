package com.example.se306_project1.viewmodel;

import android.content.SharedPreferences;
import android.widget.ImageView;
import com.example.se306_project1.models.IProduct;
import java.util.List;

public interface IDetailsViewModel {
    IProduct getProductByID(long productID);
    void updatePopularCount(IProduct product, SharedPreferences sharedPreferences);
    Boolean displayFavouritesStatus(IProduct product, ImageView icon);
    List<IProduct> getPopular();
    void PopularLogic(int sizeOfCurrentPopular, List<IProduct> popularList, IProduct productToSwap, int maxPopularSize, SharedPreferences sharedPreferences);
    void changeFavouriteStatus(SharedPreferences sharedPreferences);
}
