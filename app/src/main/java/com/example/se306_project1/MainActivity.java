package com.example.se306_project1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.se306_project1.models.Product;
import com.example.se306_project1.repository.ProductRepository;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ProductRepository productRepo = new ProductRepository();
        productRepo.fetchAllProducts();
        for(Product bag: productRepo.productsDataSet){
            System.out.println(bag.getProductLongName());
        }

    }
}