package com.gitgud.fitapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gitgud.fitapp.R;
import com.gitgud.fitapp.entities.product.GetProductsByUserQuery;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.function.BiFunction;

public class NutritionalProductsAdapter extends RecyclerView.Adapter<NutritionalProductsAdapter.DataViewHolder> {
    private Context context;
    private List<GetProductsByUserQuery.UserFood> nutritionalProducts;


    public NutritionalProductsAdapter(Context context, List<GetProductsByUserQuery.UserFood> nutritionalFacts) {
        this.context = context;
        this.nutritionalProducts = nutritionalFacts;
    }

    public void setNutritionalProducts(List<GetProductsByUserQuery.UserFood> nutritionalProducts) {
        this.nutritionalProducts = nutritionalProducts;
    }

    @NonNull
    @Override
    public NutritionalProductsAdapter.DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_nutritional_product, parent,false);
        return new NutritionalProductsAdapter.DataViewHolder(itemView);
    }

    @NonNull
    @Override
    public void onBindViewHolder(NutritionalProductsAdapter.DataViewHolder holder, int position) {
        GetProductsByUserQuery.UserFood userFood = nutritionalProducts.get(position);
        holder.name.setText(userFood.product().name());
        holder.sku.setText(userFood.product().sku());
        holder.calories.setText(String.valueOf(Math.floor(userFood.product().calories())) + " kcal");
        holder.portion.setText(String.valueOf(Math.floor(userFood.product().portion())) + " g");
        holder.setImage(userFood.product().image());
    }

    @Override
    public int getItemCount() {
        return nutritionalProducts.size();
    }

    /**
     * Data ViewHolder class.
     */
    public static class DataViewHolder extends RecyclerView.ViewHolder {

        public ImageView image;
        public TextView name;
        public TextView sku;
        public TextView calories;
        public TextView portion;

        public DataViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            name = itemView.findViewById(R.id.name);
            sku = itemView.findViewById(R.id.sku);
            calories = itemView.findViewById(R.id.calories);
            portion = itemView.findViewById(R.id.portion);
        }

        public void setImage(String url) {
            Picasso.get().load(url).placeholder(R.drawable.placeholder).resize(75, 75).centerCrop().into(image);
        }
    }

}
