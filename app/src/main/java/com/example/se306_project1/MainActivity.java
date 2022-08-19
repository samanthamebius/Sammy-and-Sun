package com.example.se306_project1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.se306_project1.adapters.RecyclerAdapter;
import com.example.se306_project1.models.Category;

import com.example.se306_project1.repository.CategoryRepository;
import com.example.se306_project1.repository.ICategoryRepository;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Category> categoryList;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.categories_recyclerView);
        categoryList = new ArrayList();

        ICategoryRepository categoryRepository = CategoryRepository.getInstance();
        categoryRepository.getCategories().observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(List<Category> categories) {
                categoryList.addAll(categories);
                setCategoryAdapter();
            }
        });

//        // will want to cache a local copy of products eventually so made favouritesProducts
//        List<Product> favouritesCache = new ArrayList<>();
//        IFavouritesRepository favouritesRepository = FavouritesRepository.getInstance();
//        favouritesRepository.getFavourites().observe(this, new Observer<List<Product>>() {
//            @Override
//            public void onChanged(List<Product> products) {
//                favouritesCache.clear();
//                for(Product singleItem: products){
//                    favouritesCache.add(singleItem);
//                }
//                // use favouritesCache to set the view here
//            }
//        });


    }

    private void setCategoryAdapter() {
        RecyclerAdapter adapter = new RecyclerAdapter(categoryList, getApplicationContext());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager((getApplicationContext()));
        recyclerView.setLayoutManager((layoutManager));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }
}