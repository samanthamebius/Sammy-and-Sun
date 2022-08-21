package com.example.se306_project1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import com.example.se306_project1.adapters.SliderImagesAdapter;
import com.example.se306_project1.data.CategoriesDataProvider;
import com.example.se306_project1.data.ProductsDataProvider;
import com.example.se306_project1.domain.UpdateCountVisit;
import com.example.se306_project1.domain.UpdateFavourite;
import com.example.se306_project1.models.Brand;
import com.example.se306_project1.models.ColourType;
import com.example.se306_project1.models.Product;
import com.example.se306_project1.repository.IProductRepository;
import com.example.se306_project1.repository.ProductRepository;

import java.util.ArrayList;

import ru.tinkoff.scrollingpagerindicator.ScrollingPagerIndicator;

public class DetailsActivity extends AppCompatActivity {

    Product p;
    ArrayList<Integer> intImages;
    private static Boolean favouriteStatus;
    RecyclerView recyclerView;
    int detailsCounter = 0;
    int productCareCounter = 0;
    ViewHolder vh;
    Toolbar toolbar;
    long productID;

    class ViewHolder {
        TextView product_details, product_price, product_brand, product_long_name, product_description,
                product_care;
        ImageView details_icon, product_care_icon, favouriteIcon;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        productID = 0;
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            productID = extras.getLong("id");
        }

        vh = new ViewHolder();
        vh.product_price = (TextView) findViewById(R.id.product_price);
        vh.product_brand = (TextView) findViewById(R.id.product_brand);
        vh.product_long_name = (TextView) findViewById(R.id.product_long_name);
        vh.product_description = (TextView) findViewById(R.id.product_description);
        vh.product_details = (TextView) findViewById(R.id.product_details);
        vh.details_icon = (ImageView) findViewById(R.id.details_icon);
        vh.product_care = (TextView) findViewById(R.id.product_care);
        vh.product_care_icon = (ImageView) findViewById(R.id.product_care_icon);
        vh.favouriteIcon = (ImageView) findViewById(R.id.favourite_icon);
        recyclerView = findViewById(R.id.recyclerView);

        toolbar = findViewById(R.id.toolBarBack);
        setSupportActionBar(toolbar);

        IProductRepository prodRepo = ProductRepository.getInstance();
        prodRepo.getProductByID(productID).observe(this, new Observer<Product>() {
            @Override
            public void onChanged(Product p) {

                intImages = GetIntImageArray(p.getProductImages());
                vh.product_price.setText("$"+Double.toString(p.getProductPrice())+"0");
                vh.product_brand.setText(p.getBrandName().name().replaceAll("_"," "));
                vh.product_long_name.setText(p.getProductLongName());
                vh.product_description.setText(p.getProductDescription());
                vh.product_details.setText(p.getProductDetails());
                vh.product_care.setText(p.getProductCare());

                UpdateCountVisit.updateCountVisit(p);

                favouriteStatus = p.getIsFavourite();

                if(favouriteStatus){
                    vh.favouriteIcon.setImageResource(R.drawable.selected_heart);
                } else {
                    vh.favouriteIcon.setImageResource(R.drawable.unselected_heart);
                }

                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getBaseContext(),
                        LinearLayoutManager.HORIZONTAL, false);
                recyclerView.setLayoutManager(layoutManager);

                SliderImagesAdapter sliderImagesAdapter = new SliderImagesAdapter(getBaseContext(), intImages);
                recyclerView.setAdapter(sliderImagesAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext(), LinearLayoutManager.HORIZONTAL, false));

                ScrollingPagerIndicator recyclerIndicator = findViewById(R.id.indicator);
                recyclerIndicator.attachToRecyclerView(recyclerView);


            }
        });
    }

    public void UpdateFavourite(View v) {
        vh = new ViewHolder();
        vh.favouriteIcon = (ImageView) findViewById(R.id.favourite_icon);

        IProductRepository prodRepo = ProductRepository.getInstance();
        prodRepo.getProductByID(productID).observe(this, new Observer<Product>() {
            @Override
            public void onChanged(Product p) {
                favouriteStatus = p.getIsFavourite();
                UpdateFavourite.updateFavourite(p, favouriteStatus);

                if(favouriteStatus){
                    vh.favouriteIcon.setImageResource(R.drawable.unselected_heart);
                } else {
                    vh.favouriteIcon.setImageResource(R.drawable.selected_heart);
                }
            }
        });
    }

    private ArrayList<Integer> GetIntImageArray(ArrayList<String> images) {
        ArrayList<Integer> intImages = new ArrayList<Integer>();

        for(int i = 0; i<images.size(); i++) {
            int imageID = GetImageResource(this, images.get(i));
            intImages.add(imageID);
        }
        return intImages;
    }

    private static int GetImageResource(Context c, String imageName) {
        int ResourceID = c.getResources().getIdentifier(imageName, "drawable", c.getPackageName());
        if (ResourceID == 0) {
            throw new IllegalArgumentException("No resource string found with name " + imageName);
        }
        else {
            return ResourceID;
        }
    }

    public void ExpandDetails(View v){
        detailsCounter++;
        if(detailsCounter%2 == 0){
            vh.product_details.setVisibility(View.GONE);
            vh.details_icon.setImageResource(R.drawable.drop_down);
        } else {
            vh.product_details.setVisibility(View.VISIBLE);
            vh.details_icon.setImageResource(R.drawable.drop_down_opposite);
        }
    }

    public void ExpandProductCare(View v){
        productCareCounter++;
        if(productCareCounter%2 == 0){
            vh.product_care.setVisibility(View.GONE);
            vh.product_care_icon.setImageResource(R.drawable.drop_down);
        } else {
            vh.product_care.setVisibility(View.VISIBLE);
            vh.product_care_icon.setImageResource(R.drawable.drop_down_opposite);
        }
    }

    public void Back(View v){
        Intent searchIntent = new Intent(this,MainActivity.class);
        startActivity(searchIntent);
        overridePendingTransition( R.anim.slide_in_left, R.anim.slide_out_right);
    }
}