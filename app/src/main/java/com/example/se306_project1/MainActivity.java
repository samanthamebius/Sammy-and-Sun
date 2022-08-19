package com.example.se306_project1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.se306_project1.adapters.CategoryRecyclerAdapter;
import com.example.se306_project1.adapters.PanelRecyclerAdapter;
import com.example.se306_project1.models.Category;

import com.example.se306_project1.models.Product;
import com.example.se306_project1.repository.CategoryRepository;
import com.example.se306_project1.repository.FavouritesRepository;
import com.example.se306_project1.repository.ICategoryRepository;
import com.example.se306_project1.repository.IFavouritesRepository;
import com.example.se306_project1.repository.IPopularRepository;
import com.example.se306_project1.repository.PopularRepository;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Product> popularList;
    private ArrayList<Product> favouritesList;
    private ArrayList<Category> categoryList;

    private RecyclerView popularRecyclerView;
    private RecyclerView favouritesRecyclerView;
    private RecyclerView categoryRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        popularList = new ArrayList();
        favouritesList = new ArrayList();
        categoryList = new ArrayList();

        popularRecyclerView = findViewById(R.id.popular_recyclerView);
        favouritesRecyclerView = findViewById(R.id.favourites_recyclerView);
        categoryRecyclerView = findViewById(R.id.categories_recyclerView);

        IPopularRepository popularRepository = PopularRepository.getInstance();
        popularRepository.getPopular().observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                popularList.addAll(products);
                setPanelAdapter(popularRecyclerView,popularList);
            }
        });

        IFavouritesRepository favouritesRepository = FavouritesRepository.getInstance();
        favouritesRepository.getFavourites().observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                favouritesList.addAll(products);
                setPanelAdapter(favouritesRecyclerView,favouritesList);
            }
        });

        ICategoryRepository categoryRepository = CategoryRepository.getInstance();
        categoryRepository.getCategories().observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(List<Category> categories) {
                categoryList.addAll(categories);
                setCategoryAdapter(categoryRecyclerView,categoryList);
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

    private void setPanelAdapter(RecyclerView view, ArrayList<Product> list) {
        PanelRecyclerAdapter adapter = new PanelRecyclerAdapter(list, getApplicationContext());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager((getApplicationContext()),LinearLayoutManager.HORIZONTAL,false);
        view.setLayoutManager((layoutManager));
        view.setItemAnimator(new DefaultItemAnimator());
        view.setAdapter(adapter);
    }

    private void setCategoryAdapter(RecyclerView view, ArrayList<Category> list) {
        CategoryRecyclerAdapter adapter = new CategoryRecyclerAdapter(list, getApplicationContext());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        view.setLayoutManager((layoutManager));
        view.setItemAnimator(new DefaultItemAnimator());
        view.setAdapter(adapter);
    }

    public void showSearchActivity(View view) {
        Intent searchIntent = new Intent(this,SearchActivity.class);
        startActivity(searchIntent);
    }
}