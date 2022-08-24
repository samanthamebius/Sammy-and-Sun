package com.example.se306_project1.domain;

import androidx.lifecycle.LiveData;
import com.example.se306_project1.models.IProduct;
import com.example.se306_project1.repository.IPopularRepository;
import com.example.se306_project1.repository.PopularRepository;
import java.util.List;

public class GetPopular {

    public LiveData<List<IProduct>> getPopular() {

        IPopularRepository repo = new PopularRepository();
        LiveData<List<IProduct>> populars = repo.getPopular();

        return populars;
    }

}
