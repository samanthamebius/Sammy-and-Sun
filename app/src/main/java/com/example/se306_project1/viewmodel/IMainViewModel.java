package com.example.se306_project1.viewmodel;

import com.example.se306_project1.models.ICategory;
import com.example.se306_project1.models.IProduct;
import java.util.List;

public interface IMainViewModel {
    List<IProduct> getPopular();
    List<IProduct> getFavourites();
    List<ICategory> getCategories();
}
