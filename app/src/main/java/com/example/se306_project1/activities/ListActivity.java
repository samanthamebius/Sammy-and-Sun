package com.example.se306_project1.activities;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.se306_project1.R;
import com.example.se306_project1.adapters.ListRecyclerAdapter;
import com.example.se306_project1.models.IProduct;
import com.example.se306_project1.repository.IProductRepository;
import com.example.se306_project1.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    Toolbar toolbar;
    private ArrayList<IProduct> productsList;
    private RecyclerView recyclerView;
    private long categoryID;
    private ListRecyclerAdapter.ListRecyclerViewClickListener listener;
    private ArrayList<Boolean> favouriteStatusList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        toolbar = findViewById(R.id.toolBarBack);
        setSupportActionBar(toolbar);

        TextView headerText = findViewById(R.id.list_header);

        productsList = new ArrayList();
        favouriteStatusList = new ArrayList();

        recyclerView = findViewById(R.id.list_recyclerView);

        String header = "Header";
        categoryID = 0;
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            header = extras.getString("header");
            categoryID = extras.getLong("id");
        }
        headerText.setText(header);

        IProductRepository productRepository = ProductRepository.getInstance();
        productRepository.getProductsByCategoryID(categoryID).observe(this, new Observer<List<IProduct>>() {
            @Override
            public void onChanged(List<IProduct> products) {
                productsList.clear();
                favouriteStatusList.clear();
                productsList.addAll(products);

                for (IProduct item : productsList) {
                    if(item.getIsFavourite()){
                        favouriteStatusList.add(true);
                    } else {
                        favouriteStatusList.add(false);
                    }
                }

                setAdapter();
            }
        });


    }

    private void setAdapter() {
        setOnClickListener();
        ListRecyclerAdapter adapter = new ListRecyclerAdapter(productsList, getApplicationContext(), categoryID, listener, favouriteStatusList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    private void setOnClickListener() {
        listener = new ListRecyclerAdapter.ListRecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                Intent intent = new Intent(getApplicationContext(), DetailsActivity.class);
                intent.putExtra("id", productsList.get(position).getProductID());
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        };
    }

    public void Back(View v){
        Intent mainIntent = new Intent(this, MainActivity.class);
        startActivity(mainIntent);
        overridePendingTransition( R.anim.slide_in_left, R.anim.slide_out_right);
    }



}
