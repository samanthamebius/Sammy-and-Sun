package com.example.se306_project1.activities;

import androidx.appcompat.app.AppCompatActivity;
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
import com.example.se306_project1.models.IProduct;
import com.example.se306_project1.viewmodel.DetailsViewModel;
import com.example.se306_project1.viewmodel.UpdateFavourite;
import java.util.ArrayList;
import java.util.Comparator;
import ru.tinkoff.scrollingpagerindicator.ScrollingPagerIndicator;

public class DetailsActivity extends AppCompatActivity {


    private ArrayList<IProduct> popularList;
    private ArrayList<Integer> intImages;
    private Boolean favouriteStatus;
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

        popularList = new ArrayList();
        sharedPreferences = getSharedPreferences("SharedPref", Context.MODE_PRIVATE);
        detailsViewModel= new ViewModelProvider(this).get(DetailsViewModel.class);

        setUpScreenElements();
        populateScreenElements();

        setPopular(product);
        detailsViewModel.updatePopularCount(product, sharedPreferences);



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
        setProduct(productID);
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



    public void setPopular(IProduct product){
        popularList.clear();
        popularList = (ArrayList<IProduct>) detailsViewModel.getPopular();
        sizeOfCurrentPopular = popularList.size();
        popularList.sort(Comparator.comparing(IProduct::getProductCountVisit));
    }

    @Override
    protected void onPause() {
        super.onPause();
        // only need to update when we leave activity
        detailsViewModel.PopularLogic(sizeOfCurrentPopular, popularList, product, maxPopularSize, sharedPreferences);
    }



    // Helper method to set array of image strings to array of integers
    private ArrayList<Integer> GetIntImageArray(ArrayList<String> images) {
        ArrayList<Integer> intImages = new ArrayList<Integer>();

        for(int i = 0; i<images.size(); i++) {
            int imageID = GetImageResource(this, images.get(i));
            intImages.add(imageID);
        }
        return intImages;
    }

    // Helper method set string to drawable int
    private static int GetImageResource(Context c, String imageName) {
        int ResourceID = c.getResources().getIdentifier(imageName, "drawable", c.getPackageName());
        if (ResourceID == 0) {
            throw new IllegalArgumentException("No resource string found with name " + imageName);
        }
        else {
            return ResourceID;
        }
    }

    // OnClick method to update product favourite status
    public void updateFavourite(View v) {
        vh = new ViewHolder();
        vh.favouriteIcon = (ImageView) findViewById(R.id.favourite_icon);

        favouriteStatus = product.getIsFavourite();
        UpdateFavourite.updateFavourite(product, favouriteStatus);
        detailsViewModel.updateFavouritesCache(sharedPreferences,product, favouriteStatus);

        if(favouriteStatus){
            vh.favouriteIcon.setImageResource(R.drawable.unselected_heart);
        } else {
            vh.favouriteIcon.setImageResource(R.drawable.selected_heart);
        }
    }

    // OnClick method to expand details drop down
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

    // OnClick method to expand product drop down
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

    public void expandTextView(View v, int counter, TextView textView, ImageView icon){
        counter++;
        if(counter%2 == 0){
            textView.setVisibility(View.GONE);
            icon.setImageResource(R.drawable.drop_down);
        }
        else {
            textView.setVisibility(View.VISIBLE);
            icon.setImageResource(R.drawable.drop_down_opposite);
        }
    }

    // OnClick method for back in appbar
    public void Back(View v){
        Intent searchIntent = new Intent(this, MainActivity.class);
        startActivity(searchIntent);
        overridePendingTransition( R.anim.slide_in_left, R.anim.slide_out_right);
    }
}