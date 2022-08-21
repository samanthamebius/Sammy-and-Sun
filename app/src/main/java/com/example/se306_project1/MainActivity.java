package com.example.se306_project1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.se306_project1.adapters.CategoryRecyclerAdapter;
import com.example.se306_project1.adapters.PanelRecyclerAdapter;
import com.example.se306_project1.data.CategoriesDataProvider;
import com.example.se306_project1.data.ProductsDataProvider;
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

    private ArrayList<Boolean> popularFavouriteStatusList;
    private ArrayList<Boolean> favouriteStatusList;

    private RecyclerView popularRecyclerView;
    private RecyclerView favouritesRecyclerView;
    private RecyclerView categoryRecyclerView;

    Toolbar toolbar;
    private PanelRecyclerAdapter.PanelRecyclerViewClickListener popularListener;
    private PanelRecyclerAdapter.PanelRecyclerViewClickListener favouritesListener;
    private CategoryRecyclerAdapter.CategoryRecyclerViewClickListener categoryListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        popularList = new ArrayList();
        favouritesList = new ArrayList();
        categoryList = new ArrayList();
        popularFavouriteStatusList = new ArrayList();
        favouriteStatusList = new ArrayList();


        popularRecyclerView = findViewById(R.id.popular_recyclerView);
        favouritesRecyclerView = findViewById(R.id.favourites_recyclerView);
        categoryRecyclerView = findViewById(R.id.categories_recyclerView);


        toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        IPopularRepository popularRepository = PopularRepository.getInstance();
        popularRepository.getPopular().observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {

                popularList.clear();

                popularFavouriteStatusList.clear();

                popularList.addAll(products);

                for (Product item : popularList) {
                    if(item.getIsFavourite()){
                        popularFavouriteStatusList.add(true);
                    } else {
                        popularFavouriteStatusList.add(false);
                    }
                }

                setPopularAdapter(popularRecyclerView,popularList, panelListener, popularFavouriteStatusList);

            }
        });

        IFavouritesRepository favouritesRepository = FavouritesRepository.getInstance();
        favouritesRepository.getFavourites().observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {

                favouritesList.clear();

                favouriteStatusList.clear();

                favouritesList.addAll(products);

                for (Product item : favouritesList) {
                    favouriteStatusList.add(true);
                }

                setFavouritesAdapter(favouritesRecyclerView,favouritesList, panelListener, favouriteStatusList);

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
       

    }

    private void setPopularAdapter(RecyclerView view, ArrayList<Product> list, PanelRecyclerAdapter.PanelRecyclerViewClickListener listener, ArrayList<Boolean> popularFavouriteStatusList) {
        setPopularOnClickListener();
        PanelRecyclerAdapter adapter = new PanelRecyclerAdapter(list, getApplicationContext(), listener, popularFavouriteStatusList);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager((getApplicationContext()),LinearLayoutManager.HORIZONTAL,false);
        view.setLayoutManager((layoutManager));
        view.setItemAnimator(new DefaultItemAnimator());
        view.setAdapter(adapter);
    }

    private void setPopularOnClickListener() {
        popularListener = new PanelRecyclerAdapter.PanelRecyclerViewClickListener(){
            @Override
            public void onClick(View v, int position) {
                Intent intent = new Intent(getApplicationContext(), DetailsActivity.class);
                intent.putExtra("id",popularList.get(position).getProductID());
                startActivity(intent);
            }
        };

    }

    private void setFavouritesAdapter(RecyclerView view, ArrayList<Product> list, PanelRecyclerAdapter.PanelRecyclerViewClickListener listener, ArrayList<Boolean> favStatusList) {
  
        setFavouritesOnClickListener();
        PanelRecyclerAdapter adapter = new PanelRecyclerAdapter(list, getApplicationContext(), listener, favStatusList);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager((getApplicationContext()),LinearLayoutManager.HORIZONTAL,false);
        view.setLayoutManager((layoutManager));
        view.setItemAnimator(new DefaultItemAnimator());
        view.setAdapter(adapter);
    }

    private void setFavouritesOnClickListener() {
        favouritesListener = new PanelRecyclerAdapter.PanelRecyclerViewClickListener(){
            @Override
            public void onClick(View v, int position) {
                Intent intent = new Intent(getApplicationContext(), DetailsActivity.class);
                intent.putExtra("id",favouritesList.get(position).getProductID());
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        };

    }

    private void setCategoryAdapter(RecyclerView view, ArrayList<Category> list) {
        setCategoryOnClickListener();
        CategoryRecyclerAdapter adapter = new CategoryRecyclerAdapter(list, getApplicationContext(), categoryListener);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        view.setLayoutManager((layoutManager));
        view.setItemAnimator(new DefaultItemAnimator());
        view.setAdapter(adapter);
    }

    private void setCategoryOnClickListener() {
        categoryListener = new CategoryRecyclerAdapter.CategoryRecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                Intent intent = new Intent(getApplicationContext(), ListActivity.class);
                String rawName = categoryList.get(position).getCategoryName();
                String formattedName = rawName.substring(0, 1).toUpperCase() + rawName.substring(1);
                long categoryID = categoryList.get(position).getCategoryID();
                intent.putExtra("header", formattedName);
                intent.putExtra("id", categoryID);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        };
    }

    public void showSearchActivity(View view) {
        Intent searchIntent = new Intent(this,SearchActivity.class);
        startActivity(searchIntent);
        overridePendingTransition(0, 0);
    }
}