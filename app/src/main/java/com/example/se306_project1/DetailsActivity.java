package com.example.se306_project1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


import com.example.se306_project1.models.Product;
import com.example.se306_project1.repository.ProductRepository;
import com.example.se306_project1.data.CategoriesDataProvider;
import com.example.se306_project1.data.ProductsDataProvider;

public class DetailsActivity extends AppCompatActivity {

    ViewHolder vh;

    class ViewHolder {
        TextView product_details;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        vh = new ViewHolder();
        vh.product_details = (TextView) findViewById(R.id.product_details);


    }

    public void expandDetails(View v){
        vh.product_details.setVisibility(View.VISIBLE);

    }


}