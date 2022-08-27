package com.example.se306_project1.domain;

import androidx.lifecycle.LiveData;

import com.example.se306_project1.models.IProduct;
import com.example.se306_project1.repository.ProductRepository;

public class GetProductDetails {

    public LiveData<IProduct> getProductDetails(long productID) {

        ProductRepository productRepository = new ProductRepository();
        LiveData<IProduct> product = productRepository.getProductByID(productID);

        return product;
    }
}
