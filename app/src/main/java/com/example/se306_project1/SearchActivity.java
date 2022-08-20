package com.example.se306_project1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

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
    private ListView searchResults;
    SearchViewModel searchViewModel;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
//        setTitle("remember to set this section as logo and add back button");

        searchField = (AutoCompleteTextView) findViewById(R.id.search_field);
        cref = FirebaseFirestore.getInstance().collection("products");
        searchResults = (ListView) findViewById(R.id.search_results);

        toolbar = findViewById(R.id.toolBarBack);
        setSupportActionBar(toolbar);

        findViewById(R.id.search_field).requestFocus();

        searchViewModel = new ViewModelProvider(this).get(SearchViewModel.class);

        cref.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                populateSearch(value);

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

                            // populate results adapter
                            String searchText = searchField.getText().toString();
                            incompleteSearch(searchText);

                        }
                        return false;
                    }
                });

            }
        });

    }


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
                }
            });


            // array adapter for the search suggestions
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter(getBaseContext(),android.R.layout.simple_list_item_1,products);
            searchField.setAdapter(arrayAdapter);

        }

    }

    // use this method if we want to have filled screen to begin with
    private void displayAllProducts(){
        ArrayList<String> bagsResults = new ArrayList<>();

        IProductRepository productRepository = ProductRepository.getInstance();
        productRepository.getProducts().observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {

                for(Product product: products){
                    bagsResults.add(product.getBrandName().name() + "\n"
                            + product.getProductLongName() + "\n"
                            + String.valueOf(product.getProductPrice()));
                }
                ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, bagsResults);
                searchResults.setAdapter(adapter);
            }
        });

    }

    // this works for empty as well
    private void incompleteSearch(String searchWord){
        ArrayList<String> bagsResults = new ArrayList<>();

        Query query = cref.orderBy("productShortName").startAt(searchWord).endAt(searchWord + "\uf8ff");
        query.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error != null){
                    Log.d("Tag incomplete search", "not null");
                }
                else {
                    for(DocumentChange doc: value.getDocumentChanges()){

                        Product bag = doc.getDocument().toObject(Product.class);
                        bagsResults.add(bag.getBrandName().name() + "\n"
                        + bag.getProductLongName() + "\n"
                        + String.valueOf(bag.getProductPrice()));
                    }

                    ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, bagsResults);
                    searchResults.setAdapter(adapter);
                    searchResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            Intent intent = new Intent(SearchActivity.this, MainActivity.class);
                            System.out.println("Using values in the adapter to pass: " + adapterView.getAdapter().getItem(i));
                            intent.putExtra("productID", adapterView.getAdapter().getItem(i).toString());

                            startActivity(intent);
                        }
                    });
                }

            }
        });
    }



    // from the search suggestions
    private void searchBag(long productID) {
        ArrayList<String> bagsResults = new ArrayList<>();

        IProductRepository testRepo = ProductRepository.getInstance();
        testRepo.getProductByID(productID).observe(this, new Observer<Product>() {
            @Override
            public void onChanged(Product product) {
                bagsResults.add(product.getBrandName().name() + "\n"
                + product.getProductLongName() + "\n"
                + String.valueOf(product.getProductPrice()));

                ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, bagsResults);
                searchResults.setAdapter(adapter);
                searchResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent intent = new Intent(SearchActivity.this, MainActivity.class);
                        System.out.println("Using values in the adapter to pass: " + adapterView.getAdapter().getItem(i));
                        intent.putExtra("productID", adapterView.getAdapter().getItem(i).toString());

                        startActivity(intent);
                    }
                });
            }
        });

    }

    public void Back(View v){
        Intent searchIntent = new Intent(this,MainActivity.class);
        startActivity(searchIntent);
        overridePendingTransition(0, 0);
    }


}