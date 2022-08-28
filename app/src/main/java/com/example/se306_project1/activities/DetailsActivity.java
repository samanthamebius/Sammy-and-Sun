package com.example.se306_project1.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
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
import androidx.recyclerview.widget.SnapHelper;

import com.example.se306_project1.R;
import com.example.se306_project1.adapters.SliderImagesAdapter;
import com.example.se306_project1.models.IProduct;
import com.example.se306_project1.viewmodel.DetailsViewModel;
import com.example.se306_project1.viewmodel.IDetailsViewModel;

import java.util.ArrayList;
import java.util.Comparator;
import ru.tinkoff.scrollingpagerindicator.ScrollingPagerIndicator;

/**
 * Represents the screen that shows the details of a particular product
 */
public class DetailsActivity extends AppCompatActivity {

    private ArrayList<IProduct> popularList;
    private ArrayList<Integer> imagesList;
    private Boolean isFavourite;
    private int detailsClicksCounter = 0;
    private int productCareClicksCounter = 0;
    private int POPULAR_MAX_SIZE = 10;
    private int popularCurrentSize;
    private IProduct product;
    long productID = 0;

    RecyclerView recyclerView;
    ViewHolder vh;
    Toolbar toolbar;
    IDetailsViewModel detailsViewModel;
    SharedPreferences sharedPreferences;

    /**
     * Describes the view of items in DetailsActivity
     */
    private class ViewHolder {
        TextView product_details, product_price, product_brand, product_long_name, product_description,
                product_care;
        ImageView details_icon, product_care_icon, favouriteIcon;

        public ViewHolder() {
            product_price = (TextView) findViewById(R.id.product_price);
            product_brand = (TextView) findViewById(R.id.product_brand);
            product_long_name = (TextView) findViewById(R.id.product_long_name);
            product_description = (TextView) findViewById(R.id.product_description);
            product_details = (TextView) findViewById(R.id.product_details);
            details_icon = (ImageView) findViewById(R.id.details_icon);
            product_care = (TextView) findViewById(R.id.product_care);
            product_care_icon = (ImageView) findViewById(R.id.product_care_icon);
            favouriteIcon = (ImageView) findViewById(R.id.favourite_icon);
            recyclerView = findViewById(R.id.recyclerView);
            toolbar = findViewById(R.id.toolBar);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            productID = extras.getLong("id");
        }

        popularList = new ArrayList();
        sharedPreferences = getSharedPreferences("SharedPref", Context.MODE_PRIVATE);
        detailsViewModel= new ViewModelProvider(this).get(DetailsViewModel.class);

        setUpScreenElements();
    }

    /**
     * Determines how to display product attributes and updates other fields based on interaction
     */
    private void setUpScreenElements() {
        vh = new ViewHolder();
        setSupportActionBar(toolbar);
        populateScreenElements();


        setPopular(product);
        detailsViewModel.updatePopularCount(product, sharedPreferences);
        isFavourite = detailsViewModel.displayFavouritesStatus(product, vh.favouriteIcon);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getBaseContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        SliderImagesAdapter sliderImagesAdapter = new SliderImagesAdapter(getBaseContext(), imagesList);
        recyclerView.setAdapter(sliderImagesAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext(), LinearLayoutManager.HORIZONTAL, false));

        ScrollingPagerIndicator recyclerIndicator = findViewById(R.id.indicator);
        recyclerIndicator.attachToRecyclerView(recyclerView);

        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);
    }


    private void populateScreenElements() {
        setProduct(productID);
        imagesList = getIntImageArray(product.getProductImages());
        vh.product_price.setText("$"+Double.toString(product.getProductPrice())+"0");
        vh.product_brand.setText(product.getBrandName().name().replaceAll("_"," "));
        vh.product_long_name.setText(product.getProductLongName());
        vh.product_description.setText(product.getProductDescription());
        vh.product_details.setText(product.getProductDetails());
        vh.product_care.setText(product.getProductCare());
    }

    private void setProduct(long productID) {
        product = null;
        product = detailsViewModel.getProductByID(productID);
    }

    public void setPopular(IProduct product){
        popularList.clear();
        popularList = (ArrayList<IProduct>) detailsViewModel.getPopular();
        popularCurrentSize = popularList.size();
        popularList.sort(Comparator.comparing(IProduct::getProductCountVisit));
    }

    @Override
    protected void onPause() {
        super.onPause();
        detailsViewModel.PopularLogic(popularCurrentSize, popularList, product, POPULAR_MAX_SIZE, sharedPreferences);
    }

    private ArrayList<Integer> getIntImageArray(ArrayList<String> images) {
        ArrayList<Integer> intImages = new ArrayList<Integer>();

        for(int i = 0; i<images.size(); i++) {
            int imageID = getImageResource(this, images.get(i));
            intImages.add(imageID);
        }
        return intImages;
    }

    private static int getImageResource(Context c, String imageName) {
        int ResourceID = c.getResources().getIdentifier(imageName, "drawable", c.getPackageName());
        if (ResourceID == 0) {
            throw new IllegalArgumentException("No resource string found with name " + imageName);
        }
        else {
            return ResourceID;
        }
    }

    public void updateFavourite(View v) {
        vh = new ViewHolder();
        vh.favouriteIcon = (ImageView) findViewById(R.id.favourite_icon);

        isFavourite = product.getIsFavourite();
        detailsViewModel.changeFavouriteStatus(sharedPreferences);

        if(isFavourite){
            vh.favouriteIcon.setImageResource(R.drawable.unselected_heart);
        } else {
            vh.favouriteIcon.setImageResource(R.drawable.selected_heart);
        }
    }

    public void expandDetails(View v){
        detailsClicksCounter++;
        if(detailsClicksCounter %2 == 0){
            vh.product_details.setVisibility(View.GONE);
            vh.details_icon.setImageResource(R.drawable.drop_down);
        } else {
            vh.product_details.setVisibility(View.VISIBLE);
            vh.details_icon.setImageResource(R.drawable.drop_down_opposite);
        }
    }

    public void expandProductCare(View v){
        productCareClicksCounter++;
        if(productCareClicksCounter%2 == 0){
            vh.product_care.setVisibility(View.GONE);
            vh.product_care_icon.setImageResource(R.drawable.drop_down);
        } else {
            vh.product_care.setVisibility(View.VISIBLE);
            vh.product_care_icon.setImageResource(R.drawable.drop_down_opposite);
        }
    }

    public void goBack(View v){
        Intent searchIntent = new Intent(this, MainActivity.class);
        startActivity(searchIntent);
        overridePendingTransition( R.anim.slide_in_left, R.anim.slide_out_right);
    }

    public void showSearchActivity(View view) {
        Intent searchIntent = new Intent(this, SearchActivity.class);
        startActivity(searchIntent);
        overridePendingTransition( R.anim.slide_in_right, R.anim.slide_out_left);
    }
}