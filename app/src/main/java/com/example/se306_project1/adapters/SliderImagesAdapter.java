package com.example.se306_project1.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.se306_project1.MainActivity;
import com.example.se306_project1.R;

import java.util.ArrayList;

public class SliderImagesAdapter extends RecyclerView.Adapter<SliderImagesAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Integer> intImages;
    ViewHolder vh;

    public SliderImagesAdapter(Context context, ArrayList<Integer> intImages) {
        this.context = context;
        this.intImages = intImages;
    }

    @NonNull
    @Override
    public SliderImagesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.slider_item, parent, false);
        return new SliderImagesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SliderImagesAdapter.ViewHolder holder, int position) {
        holder.imageView.setImageResource(intImages.get(position));

        holder.itemView.setOnClickListener(view -> {
            System.out.println(position);
        });

    }

    @Override
    public int getItemCount() {
        return intImages.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.sliderImageView);
        }
    }
}