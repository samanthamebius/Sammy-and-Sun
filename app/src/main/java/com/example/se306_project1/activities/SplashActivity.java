package com.example.se306_project1.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

import com.example.se306_project1.R;
import com.example.se306_project1.models.Category;
import com.example.se306_project1.models.IProduct;
import com.example.se306_project1.models.Product;
import com.example.se306_project1.repository.CategoryRepository;
import com.example.se306_project1.repository.FavouritesRepository;
import com.example.se306_project1.repository.ICategoryRepository;
import com.example.se306_project1.repository.IFavouritesRepository;
import com.example.se306_project1.repository.IPopularRepository;
import com.example.se306_project1.repository.IProductRepository;
import com.example.se306_project1.repository.PopularRepository;
import com.example.se306_project1.repository.ProductRepository;
import com.google.gson.Gson;

import java.util.List;

public class SplashActivity extends AppCompatActivity {


    SharedPreferences sharedPreferences;
    static Long Clutch =(long) 0;
    static Long CrossBody=(long) 0;
    static Long Tote =(long) 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        ImageView myImageView= (ImageView)findViewById(R.id.splashImage);
        Animation myFadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        myImageView.startAnimation(myFadeInAnimation);

        sharedPreferences = getSharedPreferences("SharedPref", Context.MODE_PRIVATE);

        IProductRepository productRepository = ProductRepository.getInstance();
        productRepository.getProducts().observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {

                setList("Products", products);
            }
        });

        productRepository.getProductsByCategoryID(Clutch).observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                setList("Clutch", products);
            }
        });

        productRepository.getProductsByCategoryID(CrossBody).observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                setList("Crossbody", products);
            }
        });

        productRepository.getProductsByCategoryID(Tote).observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                setList("Tote", products);
            }
        });

        IPopularRepository popularRepository = PopularRepository.getInstance();
        popularRepository.getPopular().observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                setList("Popular", products);
            }
        });

        IFavouritesRepository favouritesRepository = FavouritesRepository.getInstance();
        favouritesRepository.getFavourites().observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                setList("Favourites", products);
            }
        });


        ICategoryRepository categoryRepository = CategoryRepository.getInstance();
        categoryRepository.getCategories().observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(List<Category> categories) {
                setList("Categories", categories);
            }
        });

        // splash screen wait for all onChanged
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 3000);
    }

    public <T> void setList(String key, List<T> list) {

        Gson gson = new Gson();
        String json = gson.toJson(list);
        set(key, json);
    }

    public void set(String key, String value) {
        if (sharedPreferences != null) {
            SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
            prefsEditor.putString(key, value);
            prefsEditor.apply();
            // note use prefsEditor.commit() for sync, but will likely cause UI lag
        }
    }
}
