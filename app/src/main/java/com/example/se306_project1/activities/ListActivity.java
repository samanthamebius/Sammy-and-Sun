package com.example.se306_project1.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.se306_project1.R;
import com.example.se306_project1.adapters.ListRecyclerAdapter;
import com.example.se306_project1.models.IProduct;
import com.example.se306_project1.viewmodel.IListViewModel;
import com.example.se306_project1.viewmodel.ListViewModel;
import java.util.ArrayList;


public class ListActivity extends AppCompatActivity {

    Toolbar toolbar;
    private ArrayList<IProduct> productsList;
    private RecyclerView recyclerView;
    private long categoryID = 0;
    IListViewModel listViewModel;
    SharedPreferences sharedPreferences;
    String categoryName = "Clutches";
    TextView headerText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        headerText = findViewById(R.id.list_header);
        productsList = new ArrayList();
        recyclerView = findViewById(R.id.list_recyclerView);

        sharedPreferences = getSharedPreferences("SharedPref", Context.MODE_PRIVATE);
        listViewModel= new ViewModelProvider(this).get(ListViewModel.class);

    }

    @Override
    protected void onResume() {
        super.onResume();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            categoryID = extras.getLong("id");
            categoryName = extras.getString("name");
        }

        headerText.setText(categoryName);
        setProductList(categoryID);
        setAdapter();
    }

    private void setProductList(long categoryID) {
        productsList.clear();
        productsList = (ArrayList<IProduct>) listViewModel.getProductsList(categoryID);
    }

    private void setAdapter() {
        ListRecyclerAdapter adapter = new ListRecyclerAdapter(productsList, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    public void Back(View v){
        Intent mainIntent = new Intent(this, MainActivity.class);
        startActivity(mainIntent);
        overridePendingTransition( R.anim.slide_in_left, R.anim.slide_out_right);
    }

    public void showSearchActivity(View view) {
        Intent searchIntent = new Intent(this, SearchActivity.class);
        startActivity(searchIntent);
        overridePendingTransition( R.anim.slide_in_right, R.anim.slide_out_left);
    }
}
