package com.example.se306_project1.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.se306_project1.R;
import com.example.se306_project1.adapters.CategoryRecyclerAdapter;
import com.example.se306_project1.adapters.PanelRecyclerAdapter;
import com.example.se306_project1.data.CategoriesDataProvider;
import com.example.se306_project1.data.ProductsDataProvider;
import com.example.se306_project1.models.ICategory;
import com.example.se306_project1.models.IProduct;
import com.example.se306_project1.viewmodel.IMainViewModel;
import com.example.se306_project1.viewmodel.MainViewModel;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Represents the main screen the user sees when they open the app
 */
public class MainActivity extends AppCompatActivity {

    private ArrayList<IProduct> popularList;
    private ArrayList<IProduct> favouritesList;
    private ArrayList<ICategory> categoriesList;

    private RecyclerView popularRecyclerView;
    private RecyclerView favouritesRecyclerView;
    private RecyclerView categoryRecyclerView;

    private LinearLayout popularView;
    private LinearLayout favouritesView;

    Toolbar toolbar;
    IMainViewModel mainViewModel;
    SharedPreferences sharedPreferences;
    ViewHolder vh;

    /**
     * Describes the view of items in MainActivity
     */
    private class ViewHolder {

        public ViewHolder() {
            popularRecyclerView = findViewById(R.id.popular_recyclerView);
            favouritesRecyclerView = findViewById(R.id.favourites_recyclerView);
            categoryRecyclerView = findViewById(R.id.categories_recyclerView);
            popularView = findViewById(R.id.popular_view);
            favouritesView = findViewById(R.id.favourites_view);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        vh = new ViewHolder();

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        popularList = new ArrayList();
        favouritesList = new ArrayList();
        categoriesList = new ArrayList();


        toolbar = findViewById(R.id.toolBarMain);
        setSupportActionBar(toolbar);

        sharedPreferences = getSharedPreferences("SharedPref", Context.MODE_PRIVATE);
        mainViewModel= new ViewModelProvider(this).get(MainViewModel.class);

    }

    @Override
    public void onResume(){
        super.onResume();

        setPopularList();

        if (popularList.isEmpty()) {
            popularView.setVisibility(View.GONE);
        } else {
            popularView.setVisibility(View.VISIBLE);
        }

        setFavouritesList();

        if (favouritesList.isEmpty()) {
            favouritesView.setVisibility(View.GONE);
        } else {
            favouritesView.setVisibility(View.VISIBLE);
        }

        setCategoriesList();

        setAdapter(popularRecyclerView, popularList);
        setAdapter(favouritesRecyclerView, favouritesList);
        setCategoryAdapter(categoryRecyclerView,categoriesList);

    }

    private void setPopularList() {
        popularList.clear();
        popularList = (ArrayList<IProduct>) mainViewModel.getPopular();
    }

    private void setFavouritesList(){
        favouritesList.clear();
        favouritesList = (ArrayList<IProduct>) mainViewModel.getFavourites();
        Collections.reverse(favouritesList);
    }

    private void setCategoriesList(){
        categoriesList.clear();
        categoriesList = (ArrayList<ICategory>) mainViewModel.getCategories();
    }

    private void setAdapter(RecyclerView view, ArrayList<IProduct> list) {
        PanelRecyclerAdapter adapter = new PanelRecyclerAdapter(list, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager((getApplicationContext()),LinearLayoutManager.HORIZONTAL,false);
        view.setLayoutManager((layoutManager));
        view.setItemAnimator(new DefaultItemAnimator());
        view.setAdapter(adapter);
    }

    private void setCategoryAdapter(RecyclerView view, ArrayList<ICategory> list) {
        CategoryRecyclerAdapter adapter = new CategoryRecyclerAdapter(list, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        view.setLayoutManager((layoutManager));
        view.setItemAnimator(new DefaultItemAnimator());
        view.setAdapter(adapter);
    }

    public void showSearchActivity(View view) {
        Intent searchIntent = new Intent(this, SearchActivity.class);
        startActivity(searchIntent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

}