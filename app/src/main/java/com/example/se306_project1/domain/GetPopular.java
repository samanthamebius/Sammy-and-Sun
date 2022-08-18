package com.example.se306_project1.domain;
import androidx.lifecycle.LiveData;

import com.example.se306_project1.models.Product;
import com.example.se306_project1.repository.IPopularRepository;
import com.example.se306_project1.repository.PopularRepository;

import java.util.List;

public class GetPopular {

    public LiveData<List<Product>> getPopular() {

        IPopularRepository repo = new PopularRepository();
        LiveData<List<Product>> populars = repo.getPopular();

        return populars;
    }

}
