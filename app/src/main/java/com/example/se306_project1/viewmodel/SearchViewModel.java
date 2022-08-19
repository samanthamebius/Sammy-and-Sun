package com.example.se306_project1.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.se306_project1.models.Product;
import com.example.se306_project1.repository.IProductRepository;
import com.example.se306_project1.repository.ProductRepository;
import com.google.firebase.firestore.FirebaseFirestore;


import java.util.List;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class SearchViewModel extends ViewModel {

    private IProductRepository repository;
    private CompositeDisposable disposables = new CompositeDisposable();
    private long productID;
    public Single<Product> singleBag;
    public LiveData<List<Product>> datafromrepo;
    FirebaseFirestore mfirestore;

    public SearchViewModel(){
        repository = ProductRepository.getInstance();
    }


//    public Single<Product> getProductByID() {
//        System.out.println("here in view model class");
//        System.out.println(repository.getProductByID(productID).getValue());
//
//        //Single<Product> productSingle = productUseCase.getProduct(productID);
//
//        return productSingle;
//    }


//    public LiveData<List<Product>> getlivedataProduct(){
//        return datafromrepo;
//    }

    public void init(){
//        this.productID = productID;
//        datafromrepo =  repository.getProductByID(productID);
        mfirestore = FirebaseFirestore.getInstance();
        //singleBag = getProductByID();
    }


}
