package com.gitgud.fitapp.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.gitgud.fitapp.R;
import com.gitgud.fitapp.data.model.Goal;
import com.gitgud.fitapp.data.model.enums.GoalType;

import org.w3c.dom.Text;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class GoalHistoryAdapter extends  RecyclerView.Adapter<GoalHistoryAdapter.DataViewHolder>{
    private Context context;
    private List<Goal> goalList;

    public GoalHistoryAdapter(Context context, List<Goal> goalList) {
        this.context = context;
        this.goalList = goalList;
    }

    @NonNull
    @Override
    public GoalHistoryAdapter.DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.goal_record_card, parent,false);
        return new GoalHistoryAdapter.DataViewHolder(itemView);

    }
    @Override
    public int getItemCount() {
        return goalList.size();
    }


    @NonNull
    @Override
    public void onBindViewHolder(GoalHistoryAdapter.DataViewHolder holder, int position) {
        Goal goal = goalList.get(position);

        holder.goalName.setText(goal.getName());
        holder.goal.setText(goal.getProgress() + " / " + goal.getGoal());
        holder.goalDate.setText("Goal ended: " + getDateString(goal.getDate()));
        holder.goalType.setText(getUnit(goal.getGoalType()));

    }

    private String getDateString(String date){
        DateTimeFormatter oldFormatter = DateTimeFormatter.ofPattern("MM/dd/yy");
        LocalDate lDate = LocalDate.parse(date, oldFormatter);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM, dd");
        return lDate.format(formatter);
    }
    private String getUnit (String goalType) {
        if(goalType.equals(GoalType.RUNNING.getUrl()) ) return "Km";
        if(goalType.equals(GoalType.TIME.getUrl())) return  "min";
        return "Kg";
    }
    public static class DataViewHolder extends RecyclerView.ViewHolder {

        public TextView goalName;
        public TextView goal;
        public TextView goalType;
        public TextView goalDate;



        public DataViewHolder(View itemView) {
            super(itemView);
            goalName = itemView.findViewById(R.id.goal_name);
            goal = itemView.findViewById(R.id.goal);
            goalType = itemView.findViewById(R.id.goal_type);
            goalDate =  itemView.findViewById(R.id.goal_date);

        }

    }
}
