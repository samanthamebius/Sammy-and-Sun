package com.example.se306_project1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.se306_project1.models.Product;
import com.example.se306_project1.repository.IProductRepository;
import com.example.se306_project1.repository.ProductRepository;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        setTitle("remember to set this section as logo");


        searchField = (AutoCompleteTextView) findViewById(R.id.search_field);
        cref = FirebaseFirestore.getInstance().collection("products");
        searchResults = (ListView) findViewById(R.id.search_results);

//        recview=(RecyclerView)findViewById(R.id.recview);
//        recview.setLayoutManager(new LinearLayoutManager(this));
//
//        FirebaseRecyclerOptions<Product> options =
//                new FirebaseRecyclerOptions.Builder<Product>()
//                        .setQuery(FirebaseDatabase.getInstance().getReference().child("products").orderByChild("productShortName"), Product.class)
//                        .build();
//
//        adapter=new searchAdapter(options);
//        recview.setAdapter(adapter);



        cref.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                populateSearch(value);
            }
        });

    }

    // this method should be moved out of activity
    private void populateSearch(QuerySnapshot snapshot) {
        ArrayList<String> products = new ArrayList<>();
        HashMap<String, Long> productDetails = new HashMap<>();

        if(snapshot.isEmpty()){
            Log.d("products", "no products found!");
        }
        else {
            Log.d("firebase", "products found for search");
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

        ProductRepository productRepo = IProductRepository.getInstance();
        LiveData<Product> selectedBag =  productRepo.getProductByID(productID);
        bagsResults.add(selectedBag.getValue().getBrandName().name() + "\n"
                + selectedBag.getValue().getProductShortName() + "\n"
                + String.valueOf(selectedBag.getValue().getProductPrice()));

        ArrayAdapter adapter = new ArrayAdapter(getBaseContext(), android.R.layout.simple_list_item_1, bagsResults);
        searchResults.setAdapter(adapter);

    }


//    @Override
//    protected void onStart() {
//        super.onStart();
//        adapter.startListening();
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        adapter.stopListening();
//    }
//
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu)
//    {
//
//
//        SearchView searchView=(SearchView)item.getActionView();
//
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String s) {
//                processsearch(s);
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String s) {
//                processsearch(s);
//                return false;
//            }
//        });
//
//        return super.onCreateOptionsMenu(menu);
//    }
//
//    private void processsearch(String s)
//    {
//        FirebaseRecyclerOptions<Product> options =
//                new FirebaseRecyclerOptions.Builder<Product>()
//                        .setQuery(FirebaseDatabase.getInstance().getReference().child("products").orderByChild("productShortName").startAt(s).endAt(s+"\uf8ff"), Product.class)
//                        .build();
//
//        adapter=new searchAdapter(options);
//        adapter.startListening();
//        recview.setAdapter(adapter);
//    }

}
