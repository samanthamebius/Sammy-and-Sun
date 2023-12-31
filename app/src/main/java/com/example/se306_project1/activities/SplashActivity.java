package com.example.se306_project1.activities;

import android.content.Context;
import android.content.Intent;

import android.content.SharedPreferences;

import android.content.pm.ActivityInfo;

import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import com.example.se306_project1.R;
import com.example.se306_project1.models.ICategory;
import com.example.se306_project1.models.IProduct;
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

/**
 * Represents the screen the screen that displays while data is loaded in from firebase
 */
public class SplashActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    static Long clutchID =(long) 0;
    static Long toteID =(long) 1;
    static Long crossbodyID =(long) 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        ImageView myImageView= (ImageView)findViewById(R.id.splashImage);
        Animation myFadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        myImageView.startAnimation(myFadeInAnimation);

        sharedPreferences = getSharedPreferences("SharedPref", Context.MODE_PRIVATE);

        IProductRepository productRepository = ProductRepository.getInstance();
        productRepository.getProducts().observe(this, new Observer<List<IProduct>>() {
            @Override
            public void onChanged(List<IProduct> products) {
                setList("Products", products);
            }
        });

        productRepository.getProductsByCategoryID(clutchID).observe(this, new Observer<List<IProduct>>() {
            @Override
            public void onChanged(List<IProduct> products) {
                setList("0", products);
            }
        });

        productRepository.getProductsByCategoryID(toteID).observe(this, new Observer<List<IProduct>>() {
            @Override
            public void onChanged(List<IProduct> products) {
                setList("1", products);
            }
        });

        productRepository.getProductsByCategoryID(crossbodyID).observe(this, new Observer<List<IProduct>>() {
            @Override
            public void onChanged(List<IProduct> products) {
                setList("2", products);
            }
        });

        IPopularRepository popularRepository = PopularRepository.getInstance();
        popularRepository.getPopular().observe(this, new Observer<List<IProduct>>() {
            @Override
            public void onChanged(List<IProduct> products) {
                setList("Popular", products);
            }
        });

        IFavouritesRepository favouritesRepository = FavouritesRepository.getInstance();
        favouritesRepository.getFavourites().observe(this, new Observer<List<IProduct>>() {
            @Override
            public void onChanged(List<IProduct> products) {
                setList("Favourites", products);
            }
        });

        ICategoryRepository categoryRepository = CategoryRepository.getInstance();
        categoryRepository.getCategories().observe(this, new Observer<List<ICategory>>() {
            @Override
            public void onChanged(List<ICategory> categories) {
                setList("Categories", categories);
            }
        });

        // display splash screen while waiting for data
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 3500);
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
            prefsEditor.commit();
        }
    }
}
