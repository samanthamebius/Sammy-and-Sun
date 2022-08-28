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

/**
 * An adapter class to display the search results on the SearchActivity
 */
public class SearchRecyclerAdapter extends RecyclerView.Adapter<SearchRecyclerAdapter.MyViewHolder> {

    private ArrayList<IProduct> resultsList;
    private static Context context;
    private Activity activity;


    public SearchRecyclerAdapter(ArrayList<IProduct> resultsList, Activity activity){
        this.resultsList = resultsList;
        this.activity = activity;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView nameText;
        private TextView brandText;
        private TextView priceText;
        private ImageView iconImage;
        private ImageView favIcon;

        public MyViewHolder(final View view) {
            super(view);
            view.setOnClickListener(this);
            nameText = view.findViewById(R.id.list1_name_text_view);
            brandText = view.findViewById(R.id.list1_brand_text_view);
            priceText = view.findViewById(R.id.list1_price_text_view);
            iconImage = (ImageView) view.findViewById(R.id.list1_image_view);
            favIcon = (ImageView) view.findViewById(R.id.list1_favourite_icon);
        }

        public void onClick(View view) {
            IProduct clickedProduct = resultsList.get(getBindingAdapterPosition());
            Intent intent = new Intent(context, DetailsActivity.class);
            intent.putExtra("id", clickedProduct.getProductID());
            context.startActivity(intent);
            activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }
    }

    @NonNull
    @Override
    public SearchRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.list_view_item_style1, parent, false);
        MyViewHolder holder = new MyViewHolder(itemView);
        return holder;
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
        holder.favIcon.setVisibility(View.GONE);
    }

    /**
     * Converts a image string to an integer
     * @param imageString - a string of the file name of an image
     * @return resID - an integer resource ID
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
        return resultsList.size();
    }

}
