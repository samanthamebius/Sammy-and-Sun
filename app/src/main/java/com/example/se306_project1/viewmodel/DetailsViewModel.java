package com.example.se306_project1.viewmodel;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.se306_project1.R;
import com.example.se306_project1.models.Category;
import com.example.se306_project1.models.IProduct;
import com.example.se306_project1.models.Product;
import com.example.se306_project1.repository.FavouritesRepository;
import com.example.se306_project1.repository.IFavouritesRepository;
import com.example.se306_project1.repository.IPopularRepository;
import com.example.se306_project1.repository.IProductRepository;
import com.example.se306_project1.repository.PopularRepository;
import com.example.se306_project1.repository.ProductRepository;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class DetailsViewModel extends AndroidViewModel {

    private IProduct product;
    private List<IProduct> popularList;
    private List<IProduct> favouritesList;
    private List<IProduct> productsList;
    private List<IProduct> categoryList;
    Boolean favouriteStatus;
    IProductRepository productRepository = new ProductRepository(getApplication().getApplicationContext());
    IPopularRepository popularRepository = new PopularRepository(getApplication().getApplicationContext());
    IFavouritesRepository favouritesRepository = new FavouritesRepository(getApplication().getApplicationContext());


    public DetailsViewModel(@NonNull Application application) {
        super(application);
    }

    public IProduct getProductByID(long productID){
        product = productRepository.getProductByIDCache("Products", productID);
        return product;
    }

    public List<IProduct> getProducts(){
        productsList = new ArrayList<>();
        productsList.addAll(productRepository.getProductCache("Products"));
        return productsList;
    }

    public List<IProduct> getPopular(){
        popularList = new ArrayList<>();
        popularList.addAll(popularRepository.getPopularCache("Popular"));
        return popularList;
    }

    public List<IProduct> getCategoryCache(long categoryID){
        categoryList = new ArrayList<>();
        categoryList.addAll(productRepository.getProductCache(Long.toString(categoryID)));
        return categoryList;
    }

    public List<IProduct> getFavourites(){
        favouritesList = new ArrayList<>();
        favouritesList.addAll(favouritesRepository.getFavouritesCache("Favourites"));
        return favouritesList;
    }

    public Boolean displayFavouritesStatus(IProduct product, ImageView icon){
        favouriteStatus = product.getIsFavourite();

        if(favouriteStatus){
            icon.setImageResource(R.drawable.selected_heart);
        } else {
            icon.setImageResource(R.drawable.unselected_heart);
        }
        return favouriteStatus;
    }

    public void changeFavouriteStatus(SharedPreferences sharedPreferences){
        favouritesRepository.updateFavouriteBoolean(product, !favouriteStatus);
        renewFavouriteStatus(sharedPreferences);
        product.setProductIsFavourite(!favouriteStatus);
        if (!favouriteStatus){
            favouritesRepository.addToFavouriteCollection(product);
            addProductToFavouriteCache(sharedPreferences);
        }
        else{
            favouritesRepository.removeFromFavouriteCollection(product);
            removeProductFromFavouriteCache(sharedPreferences);
        }
    }

    public void addProductToFavouriteCache(SharedPreferences sharedPreferences){
        favouritesList = getFavourites();
        favouritesList.add(product);
        renewSharedPreferences(sharedPreferences, favouritesList, "Favourites");
    }

    public void removeProductFromFavouriteCache(SharedPreferences sharedPreferences){
        favouritesList = getFavourites();
        IProduct itemToRemove = null;
        for(IProduct p: favouritesList){
            if (p.getProductID() == product.getProductID()){
                itemToRemove = p;
            }
        }
        favouritesList.remove(itemToRemove);
        renewSharedPreferences(sharedPreferences, favouritesList, "Favourites");
    }

    public void renewFavouriteStatus(SharedPreferences sharedPreferences){
        productsList = getProducts();
        for(IProduct p: productsList){
            if(p.getProductID() == product.getProductID()){
                p.setProductIsFavourite(!product.getIsFavourite());
            }
        }
        categoryList = getCategoryCache(product.getCategoryID());
        for(IProduct p: categoryList){
            if(p.getProductID() == product.getProductID()){
                p.setProductIsFavourite(!product.getIsFavourite());
            }
        }
        popularList = getPopular();
        for(IProduct p: popularList){
            if(p.getProductID() == product.getProductID()){
                p.setProductIsFavourite(!product.getIsFavourite());
            }
        }
        renewSharedPreferences(sharedPreferences, productsList, "Products");
        renewSharedPreferences(sharedPreferences, popularList, "Popular");
        renewSharedPreferences(sharedPreferences, categoryList, Long.toString(product.getCategoryID()));
    }

    public void updatePopularCount(IProduct product, SharedPreferences sharedPreferences){
        popularRepository.updateCountVisit(product);
        renewProductCache(sharedPreferences);
    }

    public void PopularLogic(int sizeOfCurrentPopular, List<IProduct> popularList, IProduct productToSwap, int maxPopularSize, SharedPreferences sharedPreferences) {
        Boolean contains = false;
        popularList.sort(Comparator.comparing(IProduct::getProductCountVisit));
        for(int i = 0; i < popularList.size(); i++){
            if (popularList.get(i).getProductID() == productToSwap.getProductID()){
                contains = true;
            }
        }
        if(!contains){
            IProduct leastViewProduct = popularList.get(0);
            if (sizeOfCurrentPopular < maxPopularSize){
                popularRepository.addProductToPopular(productToSwap);
                addProductToPopularCache(sharedPreferences, productToSwap);
            }
            else if(productToSwap.getProductCountVisit() > leastViewProduct.getProductCountVisit()){
                popularRepository.addProductToPopular(productToSwap);
                popularRepository.removeProductFromPopular(leastViewProduct);
                swapProductsInPopularCache(sharedPreferences, leastViewProduct, productToSwap);
            }
        }
    }

    public void renewProductCache(SharedPreferences sharedPreferences){
        product.increaseProductViewCount();
        productsList = getProducts();
        for(int i = 0; i < productsList.size(); i++){
            if (productsList.get(i).getProductID() == product.getProductID()){
                productsList.set(i, product);
            }
        }
        renewSharedPreferences(sharedPreferences, productsList, "Products");
    }

    public void addProductToPopularCache(SharedPreferences sharedPreferences, IProduct product){
        popularList = getPopular();
        popularList.add(product);
        renewSharedPreferences(sharedPreferences, popularList, "Popular");
    }

    public void swapProductsInPopularCache(SharedPreferences sharedPreferences, IProduct removeProduct, IProduct addProduct){
        popularList = getPopular();
        for(int i = 0; i < popularList.size(); i++){
            if(popularList.get(i).getProductID() == removeProduct.getProductID()){
                popularList.remove(i);
            }
        }
        popularList.add(addProduct);
        renewSharedPreferences(sharedPreferences, popularList, "Popular");
    }

    public void renewSharedPreferences(SharedPreferences sharedPreferences, List<IProduct> list, String key){
        Gson gson = new Gson();
        String json = gson.toJson(list);
        if(sharedPreferences !=null){
            SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
            prefsEditor.putString(key, json);
            prefsEditor.commit();
        }
    }

}