package com.example.se306_project1.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.se306_project1.R;
import com.example.se306_project1.adapters.SearchRecyclerAdapter;
import com.example.se306_project1.models.IProduct;
import com.example.se306_project1.viewmodel.ISearchViewModel;
import com.example.se306_project1.viewmodel.SearchViewModel;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Represents the screen used to search through items
 */
public class SearchActivity extends AppCompatActivity{

    private AutoCompleteTextView searchField;
    private CollectionReference cref;
    private RecyclerView recyclerView;
    private ArrayList<IProduct> resultsList;
    private List<IProduct> allProducts;
    private ImageView searchIcon;
    private TextView noResultsText;
    ViewHolder vh;
    Toolbar toolbar;
    ISearchViewModel searchViewModel;
    SharedPreferences sharedPreferences;
    private IProduct product;

    /**
     * Describes the view of items in SearchActivity
     */
    private class ViewHolder {

        public ViewHolder() {
            recyclerView = findViewById(R.id.search_recyclerView);
            searchIcon = (ImageView) findViewById(R.id.search_icon);
            searchField = (AutoCompleteTextView) findViewById(R.id.search_field);
            toolbar = findViewById(R.id.toolBar);
            noResultsText = findViewById(R.id.no_results_text);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        vh = new ViewHolder();

        resultsList = new ArrayList<IProduct>();
        allProducts = new ArrayList<>();

        cref = FirebaseFirestore.getInstance().collection("products");

        setSupportActionBar(toolbar);

        searchIcon.setVisibility(View.GONE);
        
        sharedPreferences = getSharedPreferences("SharedPref", Context.MODE_PRIVATE);
        searchViewModel= new ViewModelProvider(this).get(SearchViewModel.class);

        findViewById(R.id.search_field).requestFocus();

        cref.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                populateSearchSuggestions(value);
            }
        });

        setAllProducts();

    }

    @Override
    protected void onStart() {
        super.onStart();
        searchField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                searchField.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView textView, int i, KeyEvent event) {

                        if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (i == EditorInfo.IME_ACTION_DONE)) {
                            searchField.dismissDropDown();
                            incompleteSearch(searchField.getText().toString());
                            InputMethodManager inputManager = (InputMethodManager) textView.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                            inputManager.hideSoftInputFromWindow(textView.getApplicationWindowToken(), 0);
                        }
                        return false;
                    }

                });
            }
        });

    }

    /**
     * Generate the search suggestions as the user types
     * @param snapshot
     */
    private void populateSearchSuggestions(QuerySnapshot snapshot) {
        ArrayList<String> products = new ArrayList<>();
        HashMap<String, Long> productDetails = new HashMap<>();

        if(snapshot.isEmpty()){
            Log.e("search", "no products found!");
        }
        else {
            Log.d("search", "products found for search");
            List<DocumentSnapshot> snapshots = snapshot.getDocuments();
            for (DocumentSnapshot singleBag : snapshots){
                String name = (String) singleBag.get("productShortName");
                long productID = (long) singleBag.get("productID");
                productDetails.put(name, productID);
                products.add(name);
            }

            // when user clicks on item in the drop down list of suggested searches
            searchField.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    long productID = productDetails.get(searchField.getText().toString());
                    searchBag(productID);
                    InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    in.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);

                }
            });

            ArrayAdapter<String> arrayAdapter = new ArrayAdapter(getBaseContext(),android.R.layout.simple_list_item_1,products);
            searchField.setAdapter(arrayAdapter);

        }

    }

    /**
     * Show the list of items matching the search term when a bag is not selected from search suggestions
     * @param searchWord
     */
    private void incompleteSearch(String searchWord){
        resultsList.clear();
        resultsList = (ArrayList<IProduct>) searchViewModel.getIncompleteSearchList(searchWord, resultsList, allProducts, noResultsText);
        setAdapter(recyclerView, resultsList);
    }

    /**
     * Show the item relating to the selected search suggestion
     * @param productID
     */
    private void searchBag(long productID) {
        setProduct(productID);
        noResultsText.setVisibility(View.GONE);
        resultsList.clear();
        resultsList.add(product);
        setAdapter(recyclerView, resultsList);

    }

    private void setProduct(long productID) {
        product = null;
        product = searchViewModel.getProductByID(productID);
    }

    private void setAllProducts(){
        allProducts.clear();
        allProducts = searchViewModel.getAllProducts();
    }

    private void setAdapter(RecyclerView view, ArrayList<IProduct> list) {
        SearchRecyclerAdapter adapter = new SearchRecyclerAdapter(list, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    public void goBack(View v){
        Intent searchIntent = new Intent(this, MainActivity.class);

        startActivity(searchIntent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
