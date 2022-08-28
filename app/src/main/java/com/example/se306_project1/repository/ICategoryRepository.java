package com.example.se306_project1.repository;

import androidx.lifecycle.LiveData;
import com.example.se306_project1.models.ICategory;
import java.util.List;

public interface ICategoryRepository {
    LiveData<List<ICategory>> getCategories();
    List<ICategory> getCategoriesCache(String key);
}
