package com.example.se306_project1.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.se306_project1.R;
import com.example.se306_project1.activities.DetailsActivity;
import com.example.se306_project1.models.Clutch;
import com.example.se306_project1.models.IProduct;
import com.example.se306_project1.models.Tote;

import java.util.ArrayList;

/**
 * An adapter class to display the list items on the ListActivity
 */
public class ListRecyclerAdapter extends RecyclerView.Adapter<ListRecyclerAdapter.MyViewHolder> {

    private ArrayList<IProduct> productList;
    private Activity activity;
    private static Context context;
    private Class aClass;

    public ListRecyclerAdapter( ArrayList<IProduct> productList, Activity activity){
        this.activity = activity;
        this.productList = productList;
        this.aClass = productList.get(0).getClass();
    }

    public class ClutchViewHolder extends MyViewHolder {
        public ClutchViewHolder(@NonNull View itemView) {
            super(itemView);
            nameText = itemView.findViewById(R.id.list0_name_text_view);
            brandText = itemView.findViewById(R.id.list0_brand_text_view);
            priceText = itemView.findViewById(R.id.list0_price_text_view);
            iconImage = (ImageView) itemView.findViewById(R.id.list0_image_view);
            favIcon = (ImageView) itemView.findViewById(R.id.list0_favourite_icon);
        }
    }

    public class CrossBodyViewHolder extends MyViewHolder {
        public CrossBodyViewHolder(@NonNull View itemView) {
            super(itemView);
            nameText = itemView.findViewById(R.id.list2_name_text_view);
            brandText = itemView.findViewById(R.id.list2_brand_text_view);
            priceText = itemView.findViewById(R.id.list2_price_text_view);
            iconImage = (ImageView) itemView.findViewById(R.id.list2_image_view);
            favIcon = (ImageView) itemView.findViewById(R.id.list2_favourite_icon);

        }
    }

    public class ToteViewHolder extends MyViewHolder {
        public ToteViewHolder(@NonNull View itemView) {
            super(itemView);
            nameText = itemView.findViewById(R.id.list1_name_text_view);
            brandText = itemView.findViewById(R.id.list1_brand_text_view);
            priceText = itemView.findViewById(R.id.list1_price_text_view);
            iconImage = (ImageView) itemView.findViewById(R.id.list1_image_view);
            favIcon = (ImageView) itemView.findViewById(R.id.list1_favourite_icon);
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        protected TextView nameText;
        protected TextView brandText;
        protected TextView priceText;
        protected ImageView iconImage;
        protected ImageView favIcon;

        public MyViewHolder(final View view) {
            super(view);
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
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;
        MyViewHolder viewHolderToReturn;
        context = parent.getContext();
        if (aClass == Clutch.class) {
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_view_item_style0, parent, false);
            viewHolderToReturn = new ClutchViewHolder(itemView);
        }  else if (aClass == Tote.class) {
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_view_item_style1, parent, false);
            viewHolderToReturn = new ToteViewHolder(itemView);
        }  else {
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_view_item_style2, parent, false);
            viewHolderToReturn = new CrossBodyViewHolder(itemView);
        }

        return viewHolderToReturn;
    }

    @Override
    public void onBindViewHolder(@NonNull ListRecyclerAdapter.MyViewHolder holder, int position) {
        Animation animation = AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.fade_in_row);

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

        holder.itemView.startAnimation(animation);
    }

    /**
     * Converts a image string to an integer
     * @param imageString
     * @return
     */
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
