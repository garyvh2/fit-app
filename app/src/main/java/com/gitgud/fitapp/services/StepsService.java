package com.gitgud.fitapp.services;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.lifecycle.LifecycleService;
import androidx.lifecycle.LiveData;
import androidx.navigation.NavDeepLinkBuilder;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.gitgud.fitapp.R;
import com.gitgud.fitapp.activities.AuthorizedActivity;
import com.gitgud.fitapp.data.model.StepsRecord;
import com.gitgud.fitapp.data.respository.ActivityRepository;
import com.gitgud.fitapp.data.respository.StepsRepository;
import com.gitgud.fitapp.services.utils.ActivityRecognitionUtils;
import com.gitgud.fitapp.ui.dashboard.dashboard.view.DashboardFragmentDirections;
import com.gitgud.fitapp.ui.modules.steps.StepsActivity;
import com.gitgud.fitapp.utils.App;
import com.gitgud.fitapp.utils.DateUtils;
import com.google.android.gms.location.ActivityRecognition;
import com.google.android.gms.location.ActivityTransition;
import com.google.android.gms.location.ActivityTransitionEvent;
import com.google.android.gms.location.ActivityTransitionRequest;
import com.google.android.gms.location.ActivityTransitionResult;
import com.google.android.gms.location.DetectedActivity;
import com.google.android.gms.tasks.Task;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class StepsService extends LifecycleService implements SensorEventListener {
    protected static final String TAG = StepsService.class.getSimpleName();
    private final int NOTIFICATION_ID = 1;
    private boolean INITIAL = false;

    /**
     * Repositories
     */
    private StepsRepository stepsRepository;
    private ActivityRepository activityRepository;

    /**
     * Sensor
     */
    private NotificationCompat.Builder notificationBuilder;
    private Sensor stepDetectorSensor;
    private SensorManager sensorManager;

    private AlarmManager alarmManager;
    private PendingIntent alarmPendingIntent;

    @Override
    public void onCreate() {
        super.onCreate();

        if (activityRepository == null) {
            activityRepository = new ActivityRepository(this);
        }

        if (stepsRepository == null) {
            stepsRepository = new StepsRepository(this);
        }

        INITIAL = true;
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        stepDetectorSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);

        if(HasGotSensorCaps()){
            Toast.makeText(this, "Registering sensors!", Toast.LENGTH_SHORT).show();
            sensorManager.registerListener(this, stepDetectorSensor, SensorManager.SENSOR_DELAY_FASTEST);
        } else {
            Toast.makeText(this, "Required sensors not supported on this device!", Toast.LENGTH_SHORT).show();
        }

        List<ActivityTransition> transitions = new ArrayList<>();

        transitions.add(
                new ActivityTransition.Builder()
                        .setActivityType(DetectedActivity.WALKING)
                        .setActivityTransition(ActivityTransition.ACTIVITY_TRANSITION_ENTER)
                        .build());

        transitions.add(
                new ActivityTransition.Builder()
                        .setActivityType(DetectedActivity.WALKING)
                        .setActivityTransition(ActivityTransition.ACTIVITY_TRANSITION_EXIT)
                        .build());

        transitions.add(
                new ActivityTransition.Builder()
                        .setActivityType(DetectedActivity.RUNNING)
                        .setActivityTransition(ActivityTransition.ACTIVITY_TRANSITION_ENTER)
                        .build());

        transitions.add(
                new ActivityTransition.Builder()
                        .setActivityType(DetectedActivity.RUNNING)
                        .setActivityTransition(ActivityTransition.ACTIVITY_TRANSITION_EXIT)
                        .build());

        transitions.add(
                new ActivityTransition.Builder()
                        .setActivityType(DetectedActivity.STILL)
                        .setActivityTransition(ActivityTransition.ACTIVITY_TRANSITION_ENTER)
                        .build());

        transitions.add(
                new ActivityTransition.Builder()
                        .setActivityType(DetectedActivity.STILL)
                        .setActivityTransition(ActivityTransition.ACTIVITY_TRANSITION_EXIT)
                        .build());

        ActivityTransitionRequest request = new ActivityTransitionRequest(transitions);

        Intent intent = new Intent(this, StepsService.class);
        PendingIntent pendingIntent = PendingIntent.getForegroundService(this, 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Task<Void> task = ActivityRecognition.getClient(this)
                .requestActivityTransitionUpdates(request, pendingIntent);

        task.addOnSuccessListener((Void result) -> {
            Toast.makeText(this, "Transition Detector Registered!", Toast.LENGTH_SHORT).show();
        });

        task.addOnFailureListener((Exception e) -> {
            Toast.makeText(this, "Transition Detector Failed!", Toast.LENGTH_SHORT).show();
        });


        stepsRepository.createDailySteps();
        activityRepository.createDailyActivities();

        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent alarmIntent = new Intent(this, InitializeBroadcastReceiver.class);
        alarmPendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, 0);

        // Set the alarm to start at 0:01 a.m.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.DATE, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 1);

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                24 * 60 * 60 * 1000, alarmPendingIntent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(HasGotSensorCaps()){
            Toast.makeText(this, "Unregistering sensors!", Toast.LENGTH_SHORT).show();
            sensorManager.unregisterListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR));
        }

        if (alarmPendingIntent != null && alarmManager != null) {
            alarmManager.cancel(alarmPendingIntent);
        }
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        if (intent != null) {
            if (ActivityTransitionResult.hasResult(intent)) {
                ActivityTransitionResult result = ActivityTransitionResult.extractResult(intent);
                ActivityTransitionEvent event = result.getTransitionEvents().get(result.getTransitionEvents().size() - 1);

                /**
                 * Get the transition values
                 */
                String TYPE = ActivityRecognitionUtils.getActivityName(event.getActivityType());
                String TRANSITION = ActivityRecognitionUtils.getTransitionName(event.getTransitionType());

                Log.i(TAG, "Activity Type " + TYPE);
                Log.i(TAG, "Transition Type " + TRANSITION);

                /**
                 * Store the transition
                 */
                activityRepository.transitionActivity(TYPE);
            }
        }

        PendingIntent pendingIntent = new NavDeepLinkBuilder(this)
                .setComponentName(AuthorizedActivity.class)
                .setGraph(R.navigation.nav_graph)
                .setDestination(R.id.stepsFragment)
                .createPendingIntent();

        if(notificationBuilder == null) {
            notificationBuilder = new NotificationCompat.Builder(this, App.CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_walking)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                    .setContentIntent(pendingIntent)
                    .setOnlyAlertOnce(true);
        }

        if (INITIAL) {
            INITIAL = false;
            startForeground(NOTIFICATION_ID, notificationBuilder.build());
        }

        startListeners();

        return START_STICKY;
    }

    public void startListeners() {
        stepsRepository.findStepsRecordByDate(DateUtils.minDate(), DateUtils.maxDate()).observe(this, stepsRecord -> {
            if (stepsRecord != null) {
                String title = MessageFormat.format("{0} steps", stepsRecord.getSteps());
                notificationBuilder.setProgress(stepsRecord.getGoal(), stepsRecord.getSteps(), false);
                notificationBuilder.setContentTitle(title);

                NotificationManager notificationManager = getSystemService(NotificationManager.class);
                notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build());
            }
        });

        activityRepository.findActivityRecordByActiveAndTime(true, DateUtils.minDate(), DateUtils.maxDate()).observe(this, activityRecord -> { ;
            if (activityRecord != null) {
                String text = MessageFormat.format("You are {0}", activityRecord.getType());
                notificationBuilder.setContentText(text);

                NotificationManager notificationManager = getSystemService(NotificationManager.class);
                notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build());
            }
        });
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        super.onBind(intent);
        return null;
    }

    public void onSensorChanged(SensorEvent sensorEvent) {
        switch (sensorEvent.sensor.getType())
        {
            case Sensor.TYPE_STEP_DETECTOR:
                AsyncTask.execute(() -> {
                    stepsRepository.addStep(DateUtils.minDate(), DateUtils.maxDate());
                });
                break;
        }
    }

    public boolean HasGotSensorCaps()
    {
        PackageManager packageManager = this.getPackageManager();

        // Require at least Android KitKat

        int currentApiVersion = Build.VERSION.SDK_INT;

        // Check that the device supports the step counter and detector sensors

        return currentApiVersion >= 19
                && packageManager.hasSystemFeature(PackageManager.FEATURE_SENSOR_STEP_DETECTOR);
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        Log.d("StepsActivity", "accuracy changed: " + accuracy);
    }
}
