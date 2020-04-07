package com.gitgud.fitapp.ui.modules.steps;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.gitgud.fitapp.R;
import com.gitgud.fitapp.data.dao.ActivityRecordDao;
import com.gitgud.fitapp.data.model.ActivityRecord;
import com.gitgud.fitapp.data.respository.ActivityRepository;
import com.gitgud.fitapp.provider.database.AppDatabase;
import com.gitgud.fitapp.utils.DateUtils;
import com.gitgud.fitapp.utils.Notifications;
import com.google.android.gms.location.ActivityTransitionEvent;
import com.google.android.gms.location.ActivityTransitionResult;
import com.google.android.gms.location.DetectedActivity;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.Date;

public class StepsBroadcastReceiver extends BroadcastReceiver {
    private ActivityRepository activityRepository;
    private final String TAG = StepsBroadcastReceiver.class.getSimpleName();
    private final int NOTIFICATION_ID = 1;
    private NotificationCompat.Builder notificationBuilder;

    private String type;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (activityRepository == null) {
            activityRepository = new ActivityRepository(context);
        }

        if (intent != null) {
            if (ActivityTransitionResult.hasResult(intent)) {
                ActivityTransitionResult result = ActivityTransitionResult.extractResult(intent);
                ActivityTransitionEvent event = result.getTransitionEvents().get(result.getTransitionEvents().size() - 1);
                //7 for walking and 8 for running
                Log.i(TAG, "Activity Type " + event.getActivityType());

                // 0 for enter, 1 for exit
                Log.i(TAG, "Transition Type " + event.getTransitionType());


                switch (event.getActivityType()) {
                    case DetectedActivity.WALKING:
                        type = "walking";
                        break;
                    case DetectedActivity.STILL:
                        type = "still";
                        break;
                    case DetectedActivity.RUNNING:
                        type = "running";
                        break;
                    case DetectedActivity.ON_BICYCLE:
                        type = "on a bicycle";
                        break;
                }

                activityRepository.transitionActivity(type);

                StepsBroadcastObserver.getInstance().updateValue(type);

                NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
                String title = MessageFormat.format("You are {0}", type);
                if (notificationBuilder == null) {
                    notificationBuilder = new NotificationCompat.Builder(context, Notifications.CHANNEL_ID)
                            .setSmallIcon(R.drawable.ic_walking)
                            .setContentTitle("Activity Started")
                            .setContentText(title)
                            .setUsesChronometer(true)
                            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                            .setCategory(NotificationCompat.CATEGORY_MESSAGE);


                    notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build());
                }
                notificationBuilder.setContentText(title);
                notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build());


            }
        }
    }

}