package com.example.se306_project1.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.se306_project1.R;
import com.example.se306_project1.models.Category;

import java.util.ArrayList;

public class CategoryRecyclerAdapter extends RecyclerView.Adapter<CategoryRecyclerAdapter.MyViewHolder> {

    public ArrayList<Category> categoryList;
    private static Context context;
    private CategoryRecyclerViewClickListener listener;

    // create instance of adapter for list of categories
    public CategoryRecyclerAdapter(ArrayList<Category> categoryList, Context context, CategoryRecyclerViewClickListener listener){
        this.categoryList = categoryList;
        this.context = context;
        this.listener = listener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView nameText;
        private ImageView iconImage;
        public MyViewHolder(final View view) {
            super(view);
            nameText = view.findViewById(R.id.category_text_view);
            iconImage = (ImageView) view.findViewById(R.id.category_image_view);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.onClick(view, getAdapterPosition());
        }
    }

    @NonNull
    @Override
    public CategoryRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_list_view_item,parent,false);
        return new MyViewHolder(itemView);
    }

    // change contents of text and image views
    @Override
    public void onBindViewHolder(@NonNull CategoryRecyclerAdapter.MyViewHolder holder, int position) {
        String rawName = categoryList.get(position).getCategoryName();
        String formattedName = rawName.substring(0, 1).toUpperCase() + rawName.substring(1);
        String iconString = categoryList.get(position).getCategoryImage();
        int imageID = getImageResource(iconString);
        holder.iconImage.setImageResource(imageID);
        holder.nameText.setText(formattedName);
    }

    public static int getImageResource(String imageString) {
        int resID = context.getResources().getIdentifier(imageString,"drawable",context.getPackageName());
        if (resID == 0) {
            throw new IllegalArgumentException("No resource string found name" + imageString);
        } else {
            return resID;
        }
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public interface CategoryRecyclerViewClickListener{
        void onClick(View v, int position);
    }

}
