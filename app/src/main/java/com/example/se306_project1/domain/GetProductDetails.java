package com.example.se306_project1.domain;

import androidx.lifecycle.LiveData;

import com.example.se306_project1.models.IProduct;
import com.example.se306_project1.models.Product;
import com.example.se306_project1.repository.IProductRepository;
import com.example.se306_project1.repository.ProductRepository;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class GetProductDetails {

    public LiveData<Product> getProductDetails(long productID) {

        ProductRepository productRepository = new ProductRepository();
        LiveData<Product> product = productRepository.getProductByID(productID);

        return product;
    }
}
