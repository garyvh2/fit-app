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
import com.gitgud.fitapp.data.model.ActivityRecord;
import com.gitgud.fitapp.data.model.Exercise;
import com.gitgud.fitapp.utils.DateUtils;


import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.DataViewHolder>  {
    private Context context;
    private List<Exercise> exerciseList;

    public ExerciseAdapter(Context context, List<Exercise> exerciseList) {
        this.context = context;
        this.exerciseList = exerciseList;
    }
    @NonNull
    @Override
    public ExerciseAdapter.DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.exercise_card_component, parent,false);
        return new ExerciseAdapter.DataViewHolder(itemView);

    }

    @NonNull
    @Override
    public void onBindViewHolder(ExerciseAdapter.DataViewHolder holder, int position) {
        Exercise exercise = exerciseList.get(position);

        holder.exerciseImage.setImageResource(R.drawable.jumping_jacks);
        holder.exerciseName.setText(exercise.getName());
        if(exercise.getTime() != 0) {
            holder.repetition.setText(exercise.getTime() + "s");
        } else if(exercise.getRepetitions() != 0) {
            holder.repetition.setText("X" + exercise.getRepetitions());
        }

    }



    @Override
    public int getItemCount() {
        return exerciseList.size();
    }



    public static class DataViewHolder extends RecyclerView.ViewHolder {

        public ImageView exerciseImage;
        public TextView exerciseName;
        public TextView repetition;
        public AppCompatImageView activityImage;
        public long time;
        public Activity itemContext;

        public DataViewHolder(View itemView) {
            super(itemView);
            exerciseImage = itemView.findViewById(R.id.exercise_image);
            exerciseName = itemView.findViewById(R.id.exercise_name);
            repetition = itemView.findViewById(R.id.repetition);
        }

    }
}
