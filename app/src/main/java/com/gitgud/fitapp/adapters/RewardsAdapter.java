package com.gitgud.fitapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gitgud.fitapp.R;
import com.gitgud.fitapp.entities.reward.GetRewardsQuery;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.function.BiFunction;

public class RewardsAdapter extends RecyclerView.Adapter<RewardsAdapter.DataViewHolder> {
    private Context context;
    private BiFunction<RewardsAdapter.DataViewHolder, GetRewardsQuery.GetReward, Void> callback;
    private List<GetRewardsQuery.GetReward> rewards;


    public RewardsAdapter(Context context, List<GetRewardsQuery.GetReward> rewards, BiFunction<RewardsAdapter.DataViewHolder, GetRewardsQuery.GetReward, Void> callback) {
        this.context = context;
        this.rewards = rewards;
        this.callback = callback;
    }

    public void setRewards(List<GetRewardsQuery.GetReward> rewards) {
        this.rewards = rewards;
    }

    @NonNull
    @Override
    public RewardsAdapter.DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reward, parent,false);
        return new RewardsAdapter.DataViewHolder(itemView);
    }

    @NonNull
    @Override
    public void onBindViewHolder(RewardsAdapter.DataViewHolder holder, int position) {
        GetRewardsQuery.GetReward reward = rewards.get(position);
        holder.description.setText(reward.description());
        holder.companyName.setText(reward.company().name());
        holder.companyDescription.setText(reward.company().description());
        holder.discount.setText(String.valueOf(Math.floor(reward.discount())) + "%");
        holder.minimunScore.setText(String.valueOf(Math.floor(reward.minimunScore())));
        holder.setImage(reward.company().logo());
        holder.getCupon.setOnClickListener(view -> {
            this.callback.apply(holder, reward);
        });
    }

    @Override
    public int getItemCount() {
        return rewards.size();
    }

    /**
     * Data ViewHolder class.
     */
    public static class DataViewHolder extends RecyclerView.ViewHolder {

        public ImageView image;
        public TextView description;
        public TextView companyName;
        public TextView companyDescription;
        public TextView discount;
        public TextView minimunScore;
        public Button getCupon;

        public DataViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.logo);
            description = itemView.findViewById(R.id.description);
            companyName = itemView.findViewById(R.id.companyName);
            companyDescription = itemView.findViewById(R.id.companyDescription);
            discount = itemView.findViewById(R.id.discount);
            minimunScore = itemView.findViewById(R.id.minimunScore);
            getCupon = itemView.findViewById(R.id.getCupon);
        }

        public void setImage(String url) {
            Picasso.get().load(url).placeholder(R.drawable.placeholder).resize(75, 75).centerCrop().into(image);
        }

        public void setCuponEnable(boolean enabled) {
            getCupon.setEnabled(enabled);
        }
    }

}