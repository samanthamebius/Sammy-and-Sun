package com.example.se306_project1.domain;
import androidx.lifecycle.LiveData;

import com.example.se306_project1.models.Product;
import com.example.se306_project1.repository.IPopularRepository;
import com.example.se306_project1.repository.PopularRepository;

import java.util.List;

public class GetPopular {

    public LiveData<List<Long>> getPopular() {

        IPopularRepository repo = new PopularRepository();
        LiveData<List<Long>> populars = repo.getPopular();

        return populars;
    }

}
