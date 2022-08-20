package com.example.se306_project1.domain;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.se306_project1.models.Category;
import com.example.se306_project1.models.ICategory;
import com.example.se306_project1.repository.CategoryRepository;
import com.example.se306_project1.repository.ICategoryRepository;

import java.util.List;

public class GetCategories {

    public LiveData<List<Category>> getCategories() {

        ICategoryRepository repo = new CategoryRepository();
        LiveData<List<Category>> categories = repo.getCategories();

        return categories;
    }

}
