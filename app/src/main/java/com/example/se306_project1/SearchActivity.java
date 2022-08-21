package com.example.se306_project1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
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
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.se306_project1.adapters.SearchRecyclerAdapter;
import com.example.se306_project1.models.Product;
import com.example.se306_project1.repository.IProductRepository;
import com.example.se306_project1.repository.ProductRepository;
import com.example.se306_project1.viewmodel.SearchViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class SearchActivity extends AppCompatActivity{

    private AutoCompleteTextView searchField;
    private CollectionReference cref;
    private RecyclerView recyclerView;
    private ArrayList<Product> resultsList;
    private SearchRecyclerAdapter.SearchRecyclerViewClickListener listener;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        resultsList = new ArrayList<>();
        recyclerView = findViewById(R.id.search_recyclerView);

        searchField = (AutoCompleteTextView) findViewById(R.id.search_field);
        cref = FirebaseFirestore.getInstance().collection("products");

        toolbar = findViewById(R.id.toolBarBack);
        setSupportActionBar(toolbar);

        findViewById(R.id.search_field).requestFocus();

        cref.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                populateSearch(value); // populate suggestions
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        searchField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                searchField.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView textView, int i, KeyEvent event) {
                        // if enter key pressed
                        if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (i == EditorInfo.IME_ACTION_DONE)) {
                            Log.e("TAG","Enter pressed");

                            //close search suggestions dropdown
                            searchField.dismissDropDown();

                            String searchText = searchField.getText().toString();
                            incompleteSearch(searchText);
                            InputMethodManager inputManager = (InputMethodManager) textView.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                            inputManager.hideSoftInputFromWindow(textView.getApplicationWindowToken(), 0);


                        }
                        return false;
                    }

                });

            }
        });

    }

    // for search suggestions
    // this method should be moved out of activity into search view model
    private void populateSearch(QuerySnapshot snapshot) {
        ArrayList<String> products = new ArrayList<>();
        HashMap<String, Long> productDetails = new HashMap<>();

        if(snapshot.isEmpty()){
            Log.d("search", "no products found!");
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
                    // get the id of the product! so preferably reuse code of get product by ID
                    String shortName = searchField.getText().toString();
                    long productID = productDetails.get(shortName);
                    searchBag(productID);
                    InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    in.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
                }
            });

            // array adapter for the search suggestions
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter(getBaseContext(),android.R.layout.simple_list_item_1,products);
            searchField.setAdapter(arrayAdapter);

        }

    }

    private void incompleteSearch(String searchWord){
        resultsList.clear();
        TextView noResultsText = findViewById(R.id.no_results_text);

        Query query = cref.orderBy("productShortName").startAt(searchWord).endAt(searchWord + "\uf8ff");
        query.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error != null){
                    Log.d("Tag incomplete search", "not null");
                }
                else {
                    if (value.isEmpty()) {
                        noResultsText.setVisibility(View.VISIBLE);
                    } else {
                        noResultsText.setVisibility(View.GONE);
                        for (DocumentChange doc : value.getDocumentChanges()) {

                            Product bag = doc.getDocument().toObject(Product.class);
                            resultsList.add(bag);
                        }

                        setAdapter();
                    }

                }

            }
        });
    }

    // for item selected from suggestions
    private void searchBag(long productID) {

        IProductRepository testRepo = ProductRepository.getInstance();
        testRepo.getProductByID(productID).observe(this, new Observer<Product>() {
            @Override
            public void onChanged(Product product) {
                resultsList.clear();
                resultsList.add(product);
                setAdapter();
            }
        });

    }

    private void setAdapter() {
        setOnClickListener();
        SearchRecyclerAdapter adapter = new SearchRecyclerAdapter(resultsList, getApplicationContext(), listener);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    private void setOnClickListener() {
        listener = new SearchRecyclerAdapter.SearchRecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                Intent intent = new Intent(getApplicationContext(), DetailsActivity.class);
                intent.putExtra("id", resultsList.get(position).getProductID());
                startActivity(intent);
            }
        };
    }

    public void Back(View v){
        Intent searchIntent = new Intent(this,MainActivity.class);
        startActivity(searchIntent);
        overridePendingTransition(0, 0);
    }


}
