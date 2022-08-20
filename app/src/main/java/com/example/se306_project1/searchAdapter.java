package com.example.se306_project1;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.se306_project1.models.Product;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;


public class searchAdapter extends FirebaseRecyclerAdapter<Product,searchAdapter.searchViewholder>
{
    public searchAdapter(@NonNull FirebaseRecyclerOptions<Product> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull searchViewholder holder, int position, @NonNull Product product)
    {
        holder.brand.setText(product.getBrandName().toString());
        holder.name.setText(product.getProductShortName().toString());
        holder.price.setText(Double.toString(product.getProductPrice()));
        // getting first image as display image
        Glide.with(holder.img.getContext()).load(product.getProductImages().get(0)).into(holder.img);
    }


    // view holder stuff below
    @NonNull
    @Override
    public searchViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerow_search,parent,false);
        return new searchViewholder(view);
    }


    // view holder class

    static class searchViewholder extends RecyclerView.ViewHolder
    {
        ImageView img;
        TextView brand,name,price;
        public searchViewholder(@NonNull View itemView)
        {
            super(itemView);
            img=(ImageView)itemView.findViewById(R.id.product_imageview);
            brand=(TextView)itemView.findViewById(R.id.brand_textview);
            name=(TextView)itemView.findViewById(R.id.productName_textview);
            price=(TextView)itemView.findViewById(R.id.price_textview);
        }
    }
}
