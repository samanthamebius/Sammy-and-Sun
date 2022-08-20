package com.example.se306_project1.repository;

import androidx.lifecycle.LiveData;

import com.example.se306_project1.models.Category;

import java.util.List;

public interface ICategoryRepository {

    public LiveData<List<Category>> getCategories();

}
