package com.example.se306_project1.repository;

import androidx.lifecycle.LiveData;
import com.example.se306_project1.models.ICategory;
import com.example.se306_project1.models.IProduct;

import java.util.List;

public interface ICategoryRepository {
    public LiveData<List<ICategory>> getCategories();
    public List<ICategory> getCategoriesCache(String key);
}
