package com.example.se306_project1.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.se306_project1.R;
import com.example.se306_project1.activities.DetailsActivity;
import com.example.se306_project1.models.IProduct;
import com.example.se306_project1.models.Product;

import java.util.ArrayList;

public class PanelRecyclerAdapter extends RecyclerView.Adapter<PanelRecyclerAdapter.MyViewHolder> {

    private ArrayList<IProduct> productList;
    private static Context context;
    private Activity activity;



    // create instance of adapter for list of categories
    public PanelRecyclerAdapter(ArrayList<IProduct> productList, Activity activity){
        this.productList = productList;
        this.activity = activity;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView nameText;
        private TextView brandText;
        private TextView priceText;
        private ImageView iconImage;
        private ImageView favIcon;

        public MyViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            nameText = view.findViewById(R.id.main_name_text_view);
            brandText = view.findViewById(R.id.main_brand_text_view);
            priceText = view.findViewById(R.id.main_price_text_view);
            iconImage = (ImageView) view.findViewById(R.id.main_image_view);
            favIcon = (ImageView) view.findViewById(R.id.favourite_icon);

        }


        public void onClick(View view) {
            IProduct clickedProduct = productList.get(getBindingAdapterPosition());
            Intent intent = new Intent(context, DetailsActivity.class);
            intent.putExtra("id",clickedProduct.getProductID());
            context.startActivity(intent);
            activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }


    }

    @NonNull
    @Override
    public PanelRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.main_list_view_item, parent, false);
        MyViewHolder holder = new MyViewHolder(itemView);
        return holder;
    }

    // change contents of text and image views
    @Override
    public void onBindViewHolder(@NonNull PanelRecyclerAdapter.MyViewHolder holder, int position) {
        String name = productList.get(position).getProductShortName();

        String brand = productList.get(position).getBrandName().toString().replaceAll("_"," ");
        String price = ("$" + String.valueOf((int)productList.get(position).getProductPrice()) + ".00");

        String iconString = productList.get(position).getProductImages().get(0);

        int imageID = getImageResource(iconString);
        holder.iconImage.setImageResource(imageID);
        holder.nameText.setText(name);
        holder.brandText.setText(brand);
        holder.priceText.setText(price);

        if(!productList.get(position).getIsFavourite()){
            Log.e("panel adapter position", Integer.toString(position));
            Log.e("panel adapter product", productList.get(position).getProductShortName());
            Log.e("panel adapter product is favourite?", productList.get(position).getIsFavourite().toString());
            holder.favIcon.setVisibility(View.GONE);
        }
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
        return productList.size();
    }



}
