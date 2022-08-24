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
import com.example.se306_project1.models.IProduct;
import java.util.ArrayList;

public class PanelRecyclerAdapter extends RecyclerView.Adapter<PanelRecyclerAdapter.MyViewHolder> {

    private ArrayList<IProduct> productList;
    private static Context context;
    private ArrayList<Boolean> favouriteStatusList;

    private PanelRecyclerViewClickListener listener;

    // create instance of adapter for list of categories
    public PanelRecyclerAdapter(ArrayList<IProduct> productList, Context context, PanelRecyclerViewClickListener listener, ArrayList<Boolean> favouriteStatusList){
        this.productList = productList;
        this.context = context;
        this.listener = listener;
        this.favouriteStatusList = favouriteStatusList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView nameText;
        private TextView brandText;
        private TextView priceText;
        private ImageView iconImage;
        private ImageView favIcon;

        public MyViewHolder(final View view) {
            super(view);
            nameText = view.findViewById(R.id.main_name_text_view);
            brandText = view.findViewById(R.id.main_brand_text_view);
            priceText = view.findViewById(R.id.main_price_text_view);
            iconImage = (ImageView) view.findViewById(R.id.main_image_view);
            favIcon = (ImageView) view.findViewById(R.id.favourite_icon);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.onClick(view,getBindingAdapterPosition());
        }


    }

    @NonNull
    @Override
    public PanelRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_list_view_item,parent,false);
        return new MyViewHolder(itemView);
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

        if (!favouriteStatusList.get(position)){
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


    public interface PanelRecyclerViewClickListener {
        void onClick(View v, int position);
    }


}
