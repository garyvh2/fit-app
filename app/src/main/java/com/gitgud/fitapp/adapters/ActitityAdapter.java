package com.gitgud.fitapp.adapters;

import android.app.Activity;
import android.content.Context;
import android.media.Image;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.gitgud.fitapp.R;
import com.gitgud.fitapp.data.model.ActivityRecord;
import com.gitgud.fitapp.utils.DateUtils;
import com.google.android.gms.location.DetectedActivity;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class ActitityAdapter extends RecyclerView.Adapter<ActitityAdapter.DataViewHolder> {
    private Context context;
    private List<ActivityRecord> activityRecords;


    public ActitityAdapter(Context context, List<ActivityRecord> activityRecords) {
        this.context = context;
        this.activityRecords = activityRecords;
    }

    @NonNull
    @Override
    public DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_activity, parent,false);
        return new DataViewHolder(itemView);

    }

    @NonNull
    @Override
    public void onBindViewHolder(DataViewHolder holder, int position) {
        ActivityRecord activityRecord = activityRecords.get(position);

        long milliseconds = activityRecord.getTimeElapsed();
        String hms = DateUtils.toHMS(milliseconds);

        holder.itemContext = (Activity)context;
        holder.time = milliseconds;
        holder.activityTime.setText(hms);
        holder.activityName.setText(activityRecord.getType());

        switch (activityRecord.getType()) {
            case "walking":
                holder.activityImage.setImageResource(R.drawable.ic_walking);
                break;
            case "still":
                holder.activityImage.setImageResource(R.drawable.ic_still);
                break;
            case "running":
                holder.activityImage.setImageResource(R.drawable.ic_running);
                break;
            case "on a bicycle":
                holder.activityImage.setImageResource(R.drawable.ic_bicycle);
                break;
        }

        if (activityRecord.isActive()) {
            holder.setupTimer();
        }
    }

    @Override
    public int getItemCount() {
        return activityRecords.size();
    }

    /**
     * Data ViewHolder class.
     */
    public static class DataViewHolder extends RecyclerView.ViewHolder {

        public TextView activityTime;
        public TextView activityName;
        public AppCompatImageView activityImage;
        public long time;
        public Activity itemContext;

        public DataViewHolder(View itemView) {
            super(itemView);
            activityTime = itemView.findViewById(R.id.activityTime);
            activityName = itemView.findViewById(R.id.activityName);
            activityImage = itemView.findViewById(R.id.activityImage);
        }

        public void setupTimer() {
            new Timer().scheduleAtFixedRate(new TimerTask(){
                @Override
                public void run(){
                    itemContext.runOnUiThread(() -> {
                        time += 1000;
                        activityTime.setText(DateUtils.toHMS(time));;
                    });
                }
            },0,1000);
        }
    }

}
