package com.example.se306_project1.viewmodel;

import com.example.se306_project1.models.IProduct;

import java.util.List;

public interface IListViewModel {
    public List<IProduct> getProductsList(long categoryID);
}