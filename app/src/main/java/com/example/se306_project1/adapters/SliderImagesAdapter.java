package com.example.se306_project1.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.se306_project1.R;
import com.bumptech.glide.Glide;
import com.example.se306_project1.models.IProduct;
import com.example.se306_project1.models.Product;

import java.util.ArrayList;
import java.util.List;

public class SliderImagesAdapter extends RecyclerView.Adapter<SliderImagesAdapter.ViewHolder> {

    private Context context;
    private Product product;

    public SliderImagesAdapter(Context context, Product product) {
        this.context = context;
        this.product = product;
    }

    @NonNull
    @Override
    public SliderImagesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.slider_item, parent, false);
        return new SliderImagesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SliderImagesAdapter.ViewHolder holder, int position) {
        Glide.with(holder.itemView)
                .asBitmap()
                .load(product.getProductImages().get(position))
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return product.getProductImages().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
        }
    }
}