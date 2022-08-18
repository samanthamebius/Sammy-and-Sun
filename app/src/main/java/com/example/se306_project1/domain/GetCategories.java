package com.example.se306_project1.domain;

import androidx.lifecycle.MutableLiveData;

import com.example.se306_project1.models.Category;
import com.example.se306_project1.models.ICategory;
import com.example.se306_project1.repository.CategoryRepository;
import com.example.se306_project1.repository.ICategoryRepository;

import java.util.List;

public class GetCategories {

    public MutableLiveData<List<Category>> getCategories() {

        ICategoryRepository repo = new CategoryRepository();
        MutableLiveData<List<Category>> categories = repo.getCategories();

        return categories;
    }

}
