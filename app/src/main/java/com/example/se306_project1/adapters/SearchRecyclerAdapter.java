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
import com.example.se306_project1.models.Product;

import java.util.ArrayList;

public class SearchRecyclerAdapter extends RecyclerView.Adapter<SearchRecyclerAdapter.MyViewHolder> {

    private ArrayList<Product> resultsList;
    private static Context context;
    private SearchRecyclerViewClickListener listener;

    public SearchRecyclerAdapter(ArrayList<Product> resultsList, Context context, SearchRecyclerViewClickListener listener){
        this.resultsList = resultsList;
        this.context = context;
        this.listener = listener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView nameText;
        private TextView brandText;
        private TextView priceText;
        private ImageView iconImage;

        public MyViewHolder(final View view) {
            super(view);
            nameText = view.findViewById(R.id.list1_name_text_view);
            brandText = view.findViewById(R.id.list1_brand_text_view);
            priceText = view.findViewById(R.id.list1_price_text_view);
            iconImage = (ImageView) view.findViewById(R.id.list1_image_view);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.onClick(view, getAbsoluteAdapterPosition());
        }
    }

    @NonNull
    @Override
    public SearchRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_view_item_style1, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String name = resultsList.get(position).getProductShortName();

        String brand = resultsList.get(position).getBrandName().toString().replaceAll("_"," ");
        String price = ("$" + String.valueOf((int)resultsList.get(position).getProductPrice()) + ".00");

        String iconString = resultsList.get(position).getProductImages().get(0);

        int imageID = getImageResource(iconString);
        holder.iconImage.setImageResource(imageID);
        holder.nameText.setText(name);
        holder.brandText.setText(brand);
        holder.priceText.setText(price);
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
        return resultsList.size();
    }

    public interface SearchRecyclerViewClickListener {
        void onClick(View v, int position);
    }
}
