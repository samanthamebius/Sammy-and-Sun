package com.example.se306_project1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;

import com.example.se306_project1.models.Product;
import com.example.se306_project1.repository.IProductRepository;
import com.example.se306_project1.repository.ProductRepository;
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

public class SearchActivity extends AppCompatActivity{

    //private RecyclerView recview;
    //private searchAdapter adapter;
    private AutoCompleteTextView searchField;
    private CollectionReference cref;
    private ListView searchResults;
    SearchViewModel searchViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        setTitle("remember to set this section as logo");


        searchField = (AutoCompleteTextView) findViewById(R.id.search_field);
        cref = FirebaseFirestore.getInstance().collection("products");
        searchResults = (ListView) findViewById(R.id.search_results);



        searchViewModel = new ViewModelProvider(this).get(SearchViewModel.class);


        cref.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                populateSearch(value);
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

            ArrayAdapter<String> arrayAdapter = new ArrayAdapter(getBaseContext(),android.R.layout.simple_list_item_1,products);
            searchField.setAdapter(arrayAdapter);


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
        }

    }

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
            }
        });

    }



}
