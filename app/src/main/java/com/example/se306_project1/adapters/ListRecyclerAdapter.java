package com.example.se306_project1.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import java.util.ArrayList;

public class ListRecyclerAdapter extends RecyclerView.Adapter<ListRecyclerAdapter.MyViewHolder> {

    private ArrayList<IProduct> productList;
    private Activity activity;
    private static Context context;
    private long styleID;

    // create instance of adapter for list of categories
    public ListRecyclerAdapter(long styleID, ArrayList<IProduct> productList, Activity activity){
        this.styleID = styleID;
        this.activity = activity;
        this.productList = productList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView nameText;
        private TextView brandText;
        private TextView priceText;
        private ImageView iconImage;
        private ImageView favIcon;

        public MyViewHolder(final View view) {
            super(view);

            if ((int)styleID == 0) {
                nameText = view.findViewById(R.id.list0_name_text_view);
                brandText = view.findViewById(R.id.list0_brand_text_view);
                priceText = view.findViewById(R.id.list0_price_text_view);
                iconImage = (ImageView) view.findViewById(R.id.list0_image_view);
                favIcon = (ImageView) view.findViewById(R.id.list0_favourite_icon);
            } else if ((int)styleID == 1) {
                nameText = view.findViewById(R.id.list1_name_text_view);
                brandText = view.findViewById(R.id.list1_brand_text_view);
                priceText = view.findViewById(R.id.list1_price_text_view);
                iconImage = (ImageView) view.findViewById(R.id.list1_image_view);
                favIcon = (ImageView) view.findViewById(R.id.list1_favourite_icon);
            } else {
                nameText = view.findViewById(R.id.list2_name_text_view);
                brandText = view.findViewById(R.id.list2_brand_text_view);
                priceText = view.findViewById(R.id.list2_price_text_view);
                iconImage = (ImageView) view.findViewById(R.id.list2_image_view);
                favIcon = (ImageView) view.findViewById(R.id.list2_favourite_icon);
            }
            view.setOnClickListener(this);
        }

        @Override
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
    public ListRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;
        context = parent.getContext();
        if ((int)styleID == 0) {
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_view_item_style0, parent, false);
        }  else if ((int)styleID == 1) {
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_view_item_style1, parent, false);
        }  else {
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_view_item_style2, parent, false);
        }
        return new MyViewHolder(itemView);
    }

    // change contents of text and image views
    @Override
    public void onBindViewHolder(@NonNull ListRecyclerAdapter.MyViewHolder holder, int position) {
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
