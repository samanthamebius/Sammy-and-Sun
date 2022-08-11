package com.example.se306_project1.repository;

import androidx.lifecycle.LiveData;

import com.example.se306_project1.models.Product;

import java.util.List;

public interface IPopularRepository {
    public LiveData<List<Product>> getPopular();
}
