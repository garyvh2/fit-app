package com.gitgud.fitapp.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gitgud.fitapp.R;

import java.util.List;
import java.util.function.BiFunction;

public class NutritionalFactsAdapter extends RecyclerView.Adapter<NutritionalFactsAdapter.DataViewHolder> {
    private Context context;
    private List<String> nutritionalFacts;
    private BiFunction<View, String, Void> deleteCallback;


    public NutritionalFactsAdapter(Context context, List<String> nutritionalFacts, BiFunction<View, String, Void> deleteCallback) {
        this.context = context;
        this.nutritionalFacts = nutritionalFacts;
        this.deleteCallback = deleteCallback;

    }

    public void setNutritionalFacts(List<String> nutritionalFacts) {
        this.nutritionalFacts = nutritionalFacts;
    }

    @NonNull
    @Override
    public NutritionalFactsAdapter.DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_nutritional_fact, parent,false);
        return new NutritionalFactsAdapter.DataViewHolder(itemView);
    }

    @NonNull
    @Override
    public void onBindViewHolder(NutritionalFactsAdapter.DataViewHolder holder, int position) {
        String nutritionalRecord = nutritionalFacts.get(position);
        holder.detailTextView.setText(nutritionalRecord);
        holder.clearButton.setOnClickListener(view -> {
            this.deleteCallback.apply(view, nutritionalRecord);
        });
    }

    @Override
    public int getItemCount() {
        return nutritionalFacts.size();
    }

    /**
     * Data ViewHolder class.
     */
    public static class DataViewHolder extends RecyclerView.ViewHolder {

        public TextView detailTextView;
        public ImageView clearButton;

        public DataViewHolder(View itemView) {
            super(itemView);
            detailTextView = itemView.findViewById(R.id.detail);
            clearButton = itemView.findViewById(R.id.clear);
        }
    }

}
