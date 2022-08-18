package com.example.se306_project1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.se306_project1.adapters.SliderImagesAdapter;
import com.example.se306_project1.data.CategoriesDataProvider;
import com.example.se306_project1.data.ProductsDataProvider;
import com.example.se306_project1.domain.UpdateCountVisit;
import com.example.se306_project1.models.Brand;
import com.example.se306_project1.models.ColourType;
import com.example.se306_project1.models.Product;

import java.util.ArrayList;

import ru.tinkoff.scrollingpagerindicator.ScrollingPagerIndicator;

public class DetailsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    int detailsCounter = 0;
    int productCareCounter = 0;
    ViewHolder vh;

    class ViewHolder {
        TextView product_details, product_price, product_brand, product_long_name, product_description,
                product_care;
        ImageView details_icon, product_care_icon, favouriteIcon;
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
//        vh.favouriteIcon = (ImageView) findViewById(R.id.favourite_icon);
        recyclerView = findViewById(R.id.recyclerView);

        ArrayList<String> images = new ArrayList<String>();
        images.add("b0_01");
        images.add("b0_02");
        images.add("b0_03");

        Product p = new Product(0, 0, 100.00, "long", "short", Brand.Balenciaga, "description", "details 1\ndetails 2\ndetails 3", "Product care 1\nProduct care 2\nProduct care 3\nProduct care 4",ColourType.Pink, 0, false, images);


        ArrayList<Integer> intImages = GetIntImageArray(images);

        vh.product_price.setText("$"+Double.toString(p.getProductPrice())+"0");
        vh.product_brand.setText(p.getBrandName().name());
        vh.product_long_name.setText(p.getProductLongName());
        vh.product_description.setText(p.getProductDescription());
        vh.product_details.setText(p.getProductDetails());
        vh.product_care.setText(p.getProductCare());

        UpdateCountVisit.updateCountVisit(p);


        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        SliderImagesAdapter sliderImagesAdapter = new SliderImagesAdapter(this, intImages);
        recyclerView.setAdapter(sliderImagesAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        ScrollingPagerIndicator recyclerIndicator = findViewById(R.id.indicator);
        recyclerIndicator.attachToRecyclerView(recyclerView);

//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
//        recyclerView.setLayoutManager(linearLayoutManager);
//        recyclerView.addItemDecoration(new CircleSliderIndicatorDecoration());

//        boolean favouriteStatus = UpdateFavourite.getFavouriteStatus(p);
//        System.out.println(favouriteStatus);
//        if (favouriteStatus) {
//            vh.favouriteIcon.setImageResource(R.drawable.selected_heart);
//        } else {
//            vh.favouriteIcon.setImageResource(R.drawable.unselected_heart);
//        }
    }


    public ArrayList<Integer> GetIntImageArray(ArrayList<String> images) {
        ArrayList<Integer> intImages = new ArrayList<Integer>();

        for(int i = 0; i<images.size(); i++) {
            int imageID = GetImageResource(this, images.get(i));
            intImages.add(imageID);
        }
        return intImages;
    }

    public static int GetImageResource(Context c, String imageName) {
        int ResourceID = c.getResources().getIdentifier(imageName, "drawable", c.getPackageName());
        if (ResourceID == 0) {
            throw new IllegalArgumentException("No resource string found with name " + imageName);
        }
        else {
            return ResourceID;
        }
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