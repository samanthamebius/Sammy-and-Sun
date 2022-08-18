package com.example.se306_project1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import android.os.Bundle;
import android.widget.TextView;

import com.example.se306_project1.models.Category;
import com.example.se306_project1.models.Product;
import com.example.se306_project1.repository.CategoryRepository;
import com.example.se306_project1.repository.FavouritesRepository;
import com.example.se306_project1.repository.ICategoryRepository;
import com.example.se306_project1.repository.IFavouritesRepository;
import com.example.se306_project1.repository.IProductRepository;
import com.example.se306_project1.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        IProductRepository productRepository= ProductRepository.getInstance();
        productRepository.getProducts().observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                // loop through list of products here
                // favourites repo is left as an example
            }
        });

        // will want to cache a local copy of products eventually so made favouritesProducts
        List<Product> favouritesCache = new ArrayList<>();
        IFavouritesRepository favouritesRepository = FavouritesRepository.getInstance();
        favouritesRepository.getFavourites().observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                favouritesCache.clear();
                for(Product singleItem: products){
                    favouritesCache.add(singleItem);
                }
                // use favouritesCache to set the view here
            }
        });

    }
}