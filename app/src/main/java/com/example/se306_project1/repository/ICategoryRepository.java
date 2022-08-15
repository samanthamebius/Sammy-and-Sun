package com.example.se306_project1.repository;

import androidx.lifecycle.MutableLiveData;

import com.example.se306_project1.models.Category;

import java.util.List;

public interface ICategoryRepository {

    MutableLiveData<List<Category>> getCategories();

}
