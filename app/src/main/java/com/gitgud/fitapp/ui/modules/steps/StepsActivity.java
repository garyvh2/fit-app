package com.gitgud.fitapp.ui.modules.steps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.gitgud.fitapp.R;
import com.gitgud.fitapp.databinding.ActivityStepsBinding;
import com.gitgud.fitapp.utils.Permissions;
import com.google.android.gms.location.ActivityRecognition;
import com.google.android.gms.location.ActivityTransition;
import com.google.android.gms.location.ActivityTransitionRequest;
import com.google.android.gms.location.DetectedActivity;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class StepsActivity extends AppCompatActivity implements SensorEventListener, Observer {
    private final int ACTIVITY_RECOGNITION_GRANTED = 1;

    @NonNull
    private StepsViewModel viewModel;
    @NonNull
    ActivityStepsBinding binding;


    private SensorManager sensorManager;
    private Sensor stepCounterSensor;
    private Sensor stepDetectorSensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_steps);
        viewModel = StepsModule.createViewModel();
        binding.setViewModel(viewModel);

        checkPermissions();
    }

    private void checkPermissions() {
        try {
            if (Permissions.arePermissionsGranted(this, Arrays.asList(Manifest.permission.ACTIVITY_RECOGNITION))) {
                ActivityCompat.requestPermissions(
                        this,
                        new String[]{Manifest.permission.ACTIVITY_RECOGNITION},
                        ACTIVITY_RECOGNITION_GRANTED
                );
            } else {
                initSensors();
            }
        } catch (SecurityException exception) {
            Log.e("[Error]", exception.getMessage());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        try {
            switch (requestCode) {
                case ACTIVITY_RECOGNITION_GRANTED: {
                    // If request is cancelled, the result arrays are empty.
                    if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        initSensors();
                    }
                    return;
                }
            }
        } catch (SecurityException exception) {
            Log.e("[Error]", exception.getMessage());
        }

    }

    private void initSensors() {
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        stepCounterSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        stepDetectorSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);

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

        StepsBroadcastObserver.getInstance().addObserver(this);

        ActivityTransitionRequest request = new ActivityTransitionRequest(transitions);

        Intent intent = new Intent(this, StepsBroadcastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Task<Void> task = ActivityRecognition.getClient(this)
                .requestActivityTransitionUpdates(request, pendingIntent);

        task.addOnSuccessListener((Void result) -> {
            Toast.makeText(this, "Transition Detector Registered!", Toast.LENGTH_SHORT).show();
        });

        task.addOnFailureListener((Exception e) -> {
            Toast.makeText(this, "Transition Detector Failed!", Toast.LENGTH_SHORT).show();
        });

    }

    private void registerSensors() {
        if(!HasGotSensorCaps()){
            Toast.makeText(this, "Required sensors not supported on this device!", Toast.LENGTH_SHORT).show();
            return;
        }

        Toast.makeText(this, "Registering sensors!", Toast.LENGTH_SHORT).show();

        sensorManager.registerListener(this, stepCounterSensor, SensorManager.SENSOR_DELAY_FASTEST);
        sensorManager.registerListener(this, stepDetectorSensor, SensorManager.SENSOR_DELAY_FASTEST);
    }

    private void unregisterSensors()
    {
        if(!HasGotSensorCaps()){
            return;
        }

        sensorManager.unregisterListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER));
        sensorManager.unregisterListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR));
        sensorManager.unregisterListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR));
    }

    public boolean HasGotSensorCaps()
    {
        PackageManager packageManager = this.getPackageManager();

        // Require at least Android KitKat

        int currentApiVersion = Build.VERSION.SDK_INT;

        // Check that the device supports the step counter and detector sensors

        return currentApiVersion >= 19
                && packageManager.hasSystemFeature(PackageManager.FEATURE_SENSOR_STEP_COUNTER)
                && packageManager.hasSystemFeature(PackageManager.FEATURE_SENSOR_STEP_DETECTOR)
                && packageManager.hasSystemFeature(PackageManager.FEATURE_SENSOR_ACCELEROMETER);
    }

    protected void onResume() {
        super.onResume();
        registerSensors();
    }

    protected void onPause() {
        super.onPause();
        unregisterSensors();
    }

    public void onDestroy()
    {
        super.onDestroy();
        unregisterSensors();
    }

    public void onSensorChanged(SensorEvent sensorEvent) {
        switch (sensorEvent.sensor.getType())
        {
            case Sensor.TYPE_STEP_DETECTOR:
                this.viewModel.setSteps(this.viewModel.getSteps() + 1);
                break;
        }
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        Log.d("StepsActivity", "accuracy changed: " + accuracy);
    }

    @Override
    public void update(Observable observable, Object data) {
        this.viewModel.setActivity(String.valueOf(data));
    }
}
