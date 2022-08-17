package com.example.se306_project1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.se306_project1.adapters.SliderImagesAdapter;
import com.example.se306_project1.models.Brand;
import com.example.se306_project1.models.ColourType;
import com.example.se306_project1.models.IProduct;
import com.example.se306_project1.models.Product;
import com.example.se306_project1.repository.ProductRepository;
import com.example.se306_project1.data.CategoriesDataProvider;
import com.example.se306_project1.data.ProductsDataProvider;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ViewPager2 viewPager;

    SliderImagesAdapter sliderImagesAdapter;

    int detailsCounter = 0;
    int productCareCounter = 0;
    ViewHolder vh;

    class ViewHolder {
        TextView product_details, product_price, product_brand, product_long_name, product_description,
                product_care;
        ImageView details_icon, product_care_icon;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        vh = new ViewHolder();
        vh.product_price = (TextView) findViewById(R.id.product_price);
        vh.product_brand = (TextView) findViewById(R.id.product_brand);
        vh.product_long_name = (TextView) findViewById(R.id.product_long_name);
        vh.product_description = (TextView) findViewById(R.id.product_description);
        vh.product_details = (TextView) findViewById(R.id.product_details);
        vh.details_icon = (ImageView) findViewById(R.id.details_icon);
        vh.product_care = (TextView) findViewById(R.id.product_care);
        vh.product_care_icon = (ImageView) findViewById(R.id.product_care_icon);
        viewPager = findViewById(R.id.viewPager);


        ArrayList<String> images = new ArrayList<String>();
        images.add("b0_01");
        images.add("@drawable/b0_02");
        images.add("@drawable/b0_03");


        Product p = new Product(0, 0, 100.00, "long", "short", Brand.Balenciaga, "description", "details", "product care",ColourType.Pink, 0, false, images);

        vh.product_price.setText("$"+Double.toString(p.getProductPrice())+"0");
        vh.product_brand.setText(p.getBrandName().name());
        vh.product_long_name.setText(p.getProductLongName());
        vh.product_description.setText(p.getProductDescription());
        vh.product_details.setText(p.getProductDetails());
        vh.product_care.setText(p.getProductCare());

        sliderImagesAdapter = new SliderImagesAdapter(this, p);
        viewPager.setAdapter(sliderImagesAdapter);
    }

    public void expandDetails(View v){
        detailsCounter++;
        if(detailsCounter%2 == 0){
            vh.product_details.setVisibility(View.GONE);
            vh.details_icon.setImageResource(R.drawable.drop_down);
        } else {
            vh.product_details.setVisibility(View.VISIBLE);
            vh.details_icon.setImageResource(R.drawable.drop_down_opposite);
        }
    }

    public void expandProductCare(View v){
        productCareCounter++;
        if(productCareCounter%2 == 0){
            vh.product_care.setVisibility(View.GONE);
            vh.product_care_icon.setImageResource(R.drawable.drop_down);
        } else {
            vh.product_care.setVisibility(View.VISIBLE);
            vh.product_care_icon.setImageResource(R.drawable.drop_down_opposite);
        }
    }
}