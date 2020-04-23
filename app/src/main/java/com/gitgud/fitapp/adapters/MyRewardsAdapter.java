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
import com.gitgud.fitapp.entities.reward.GetRewardsByUserQuery;
import com.gitgud.fitapp.entities.reward.GetRewardsQuery;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.function.BiFunction;

public class MyRewardsAdapter extends RecyclerView.Adapter<MyRewardsAdapter.DataViewHolder> {
    private Context context;
    private List<GetRewardsByUserQuery.UserReward> rewards;


    public MyRewardsAdapter(Context context, List<GetRewardsByUserQuery.UserReward> rewards) {
        this.context = context;
        this.rewards = rewards;
    }

    public void setRewards(List<GetRewardsByUserQuery.UserReward> rewards) {
        this.rewards = rewards;
    }

    @NonNull
    @Override
    public MyRewardsAdapter.DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_reward, parent,false);
        return new MyRewardsAdapter.DataViewHolder(itemView);
    }

    @NonNull
    @Override
    public void onBindViewHolder(MyRewardsAdapter.DataViewHolder holder, int position) {
        GetRewardsByUserQuery.Reward reward = rewards.get(position).reward();
        holder.description.setText(reward.description());
        holder.companyName.setText(reward.company().name());
        holder.companyDescription.setText(reward.company().description());
        holder.discount.setText(String.valueOf(Math.floor(reward.discount())) + "%");
        holder.minimunScore.setText(String.valueOf(Math.floor(reward.minimunScore())));
        holder.setImage(reward.company().logo());
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

        public DataViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.logo);
            description = itemView.findViewById(R.id.description);
            companyName = itemView.findViewById(R.id.companyName);
            companyDescription = itemView.findViewById(R.id.companyDescription);
            discount = itemView.findViewById(R.id.discount);
            minimunScore = itemView.findViewById(R.id.minimunScore);
        }

        public void setImage(String url) {
            Picasso.get().load(url).placeholder(R.drawable.placeholder).resize(75, 75).centerCrop().into(image);
        }
    }

}
