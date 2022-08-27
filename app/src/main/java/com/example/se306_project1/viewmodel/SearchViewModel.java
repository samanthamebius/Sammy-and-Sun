package com.example.se306_project1.viewmodel;

import android.app.Application;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.se306_project1.models.IProduct;
import com.example.se306_project1.repository.IProductRepository;
import com.example.se306_project1.repository.ProductRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class SearchViewModel extends AndroidViewModel implements ISearchViewModel {

    private IProduct bag;
    private List<IProduct> allProducts;


    IProductRepository productRepository = new ProductRepository(getApplication().getApplicationContext());
    public SearchViewModel(@NonNull Application application) {
        super(application);
    }

    public IProduct getProductByID(long productID){
        bag = productRepository.getProductByIDCache("Products", productID);
        return bag;
    }

    public List<IProduct> getAllProducts(){
        allProducts = new ArrayList<>();
        allProducts.addAll( productRepository.getProductCache("Products"));
        return allProducts;
    }


    public List<IProduct> getIncompleteSearchList (String searchWord, ArrayList<IProduct> resultsList, List<IProduct> allProducts, TextView view){
        Collections.sort(allProducts, Comparator.comparing(IProduct::getProductShortName));
        resultsList = (ArrayList<IProduct>) allProducts.stream().filter(iProduct -> iProduct.getProductShortName().startsWith(searchWord)).collect(Collectors.toList());

        if (resultsList.isEmpty()){
            view.setVisibility(View.VISIBLE);
        }
        else{
            view.setVisibility(View.GONE);
        }
        return resultsList;
    }

}
