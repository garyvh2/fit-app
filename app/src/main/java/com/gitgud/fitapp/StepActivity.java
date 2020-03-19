package com.gitgud.fitapp;

import androidx.databinding.DataBindingUtil;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;

import com.gitgud.fitapp.databinding.ActivityStepBinding;
import com.gitgud.fitapp.utils.Toasts;

import java.text.MessageFormat;

public class StepActivity extends Activity implements SensorEventListener {
    private static final String TAG = StepActivity.class.getSimpleName();
    private Context context;
    /**
     * Service Variables
     */
    private SensorManager sensorManager;
    private Sensor stepCounterSensor;
    private Sensor stepDetectorSensor;
    private Sensor acelerationSensor;

    private int stepsTaken = 0;
    private int reportedSteps = 0;
    private int stepDetector = 0;
    private String aceleration = "";


    private ActivityStepBinding binding;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);

        context = this;

        binding = DataBindingUtil.setContentView(this, R.layout.activity_step);
        binding.setActivity(this);

        /**
         * Register Services
         */
        sensorManager = (SensorManager) context.getSystemService(SENSOR_SERVICE);
        acelerationSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        stepCounterSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        stepDetectorSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);

        registerSensors();

    }

    private void registerSensors()
    {
        if(!HasGotSensorCaps()){
            Toasts.show(findViewById(android.R.id.content).getRootView(), "Required sensors not supported on this device!");
            return;
        }

        Toasts.show(findViewById(android.R.id.content).getRootView(), "Registering sensors!");

        sensorManager.registerListener(this, acelerationSensor, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, stepCounterSensor, SensorManager.SENSOR_DELAY_FASTEST);
        sensorManager.registerListener(this, stepDetectorSensor, SensorManager.SENSOR_DELAY_FASTEST);
    }

    private void unRegisterSensors()
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
        unRegisterSensors();
    }

    public void onDestroy()
    {
        super.onDestroy();
        unRegisterSensors();
    }

    public void onSensorChanged(SensorEvent sensorEvent) {
        switch (sensorEvent.sensor.getType())
        {
            case Sensor.TYPE_STEP_COUNTER:
                if (reportedSteps < 1){
                    reportedSteps = (int)sensorEvent.values[0];
                }
                stepsTaken = (int)sensorEvent.values[0] - reportedSteps;
                break;

            case Sensor.TYPE_STEP_DETECTOR:
                stepDetector++;
                break;
            case  Sensor.TYPE_ACCELEROMETER:
                aceleration = MessageFormat.format("X: {0} Y: {1} Z: {2}", sensorEvent.values[0], sensorEvent.values[1], sensorEvent.values[2]);
                break;
        }
        binding.invalidateAll();
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        Log.d(TAG, "accuracy changed: " + accuracy);
    }

    public String getSteps() {
        return Float.toString(this.stepsTaken);
    }

    public String getAceleration() {
        return aceleration;
    }
}
