package com.example.se306_project1.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;
import com.example.se306_project1.R;
import com.example.se306_project1.adapters.SliderImagesAdapter;
import com.example.se306_project1.viewmodel.PopularCountVisit;
import com.example.se306_project1.viewmodel.UpdateFavourite;
import com.example.se306_project1.models.IProduct;
import com.example.se306_project1.repository.IPopularRepository;
import com.example.se306_project1.repository.IProductRepository;
import com.example.se306_project1.repository.PopularRepository;
import com.example.se306_project1.repository.ProductRepository;
import com.example.se306_project1.viewmodel.DetailsViewModel;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import ru.tinkoff.scrollingpagerindicator.ScrollingPagerIndicator;

public class DetailsActivity extends AppCompatActivity {

    IProduct lowestCountProduct;

    private ArrayList<Integer> intImages;
    private static Boolean favouriteStatus;
    private int detailsCounter = 0;
    private int productCareCounter = 0;
    private int maxPopularSize = 10;
    private int sizeOfCurrentPopular;
    private IProduct product;
    long productID;

    RecyclerView recyclerView;
    ViewHolder vh;
    Toolbar toolbar;
    DetailsViewModel detailsViewModel;
    SharedPreferences sharedPreferences;

    class ViewHolder {
        TextView product_details, product_price, product_brand, product_long_name, product_description,
                product_care;
        ImageView details_icon, product_care_icon, favouriteIcon;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        productID = 0;
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            productID = extras.getLong("id");
        }

        sharedPreferences = getSharedPreferences("SharedPref", Context.MODE_PRIVATE);
        detailsViewModel= new ViewModelProvider(this).get(DetailsViewModel.class);

        setUpScreenElements();

        setProduct(productID);

        populateScreenElements();

        PopularCountVisit.updateCountVisit(product);
        UpdatePopular(product);

        displayFavouriteIcon();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getBaseContext(),
                LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        SliderImagesAdapter sliderImagesAdapter = new SliderImagesAdapter(getBaseContext(), intImages);
        recyclerView.setAdapter(sliderImagesAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext(), LinearLayoutManager.HORIZONTAL, false));

        ScrollingPagerIndicator recyclerIndicator = findViewById(R.id.indicator);
        recyclerIndicator.attachToRecyclerView(recyclerView);
    }

    private void setUpScreenElements() {
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
    }

    private void populateScreenElements() {
        intImages = GetIntImageArray(product.getProductImages());
        vh.product_price.setText("$"+Double.toString(product.getProductPrice())+"0");
        vh.product_brand.setText(product.getBrandName().name().replaceAll("_"," "));
        vh.product_long_name.setText(product.getProductLongName());
        vh.product_description.setText(product.getProductDescription());
        vh.product_details.setText(product.getProductDetails());
        vh.product_care.setText(product.getProductCare());
    }

    private void displayFavouriteIcon() {
        favouriteStatus = product.getIsFavourite();

        if(favouriteStatus){
            vh.favouriteIcon.setImageResource(R.drawable.selected_heart);
        } else {
            vh.favouriteIcon.setImageResource(R.drawable.unselected_heart);
        }
    }

    private void setProduct(long productID) {
        product = null;
        product = detailsViewModel.getProductByID(productID);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // only need to update when we leave activity
        PopularLogic(sizeOfCurrentPopular, product, lowestCountProduct);
    }

    public void UpdatePopular(IProduct p){
        IPopularRepository popRepo = PopularRepository.getInstance();
        popRepo.getPopular().observe(this, new Observer<List<IProduct>>() {
            @Override
            public void onChanged(List<IProduct> products) {

                sizeOfCurrentPopular = products.size();
                products.sort(Comparator.comparing(IProduct::getProductCountVisit));
                lowestCountProduct = products.get(0);
            }
        });
    }

    public void PopularLogic(int sizeOfCurrentPopular, IProduct currentProduct, IProduct productToSwap){

        if(sizeOfCurrentPopular < maxPopularSize){
            PopularCountVisit.addToPopularCollection(currentProduct);
        }else{
            if(productToSwap.getProductCountVisit() < currentProduct.getProductCountVisit()){
                PopularCountVisit.removeFromPopularCollection(productToSwap);
                PopularCountVisit.addToPopularCollection(currentProduct);
            }
        }
    }

    public void UpdateFavourite(View v) {
        vh = new ViewHolder();
        vh.favouriteIcon = (ImageView) findViewById(R.id.favourite_icon);

        IProductRepository prodRepo = ProductRepository.getInstance();
        prodRepo.getProductByID(productID).observe(this, new Observer<IProduct>() {
            @Override
            public void onChanged(IProduct p) {
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
        Intent searchIntent = new Intent(this, MainActivity.class);
        startActivity(searchIntent);
        overridePendingTransition( R.anim.slide_in_left, R.anim.slide_out_right);
    }
}