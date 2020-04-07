package com.gitgud.fitapp.ui.modules.steps;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.ActivityTransitionEvent;
import com.google.android.gms.location.ActivityTransitionResult;
import com.google.android.gms.location.DetectedActivity;

import java.text.MessageFormat;

public class StepsBroadcastReceiver extends BroadcastReceiver {
    private final String TAG = StepsBroadcastReceiver.class.getSimpleName();
    private StepsBroadcastObserver stepsActivityConnect;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null) {
            if (ActivityTransitionResult.hasResult(intent)) {
                ActivityTransitionResult result = ActivityTransitionResult.extractResult(intent);
                ActivityTransitionEvent event = result.getTransitionEvents().get(result.getTransitionEvents().size() - 1);
                //7 for walking and 8 for running
                Log.i(TAG, "Activity Type " + event.getActivityType());

                // 0 for enter, 1 for exit
                Log.i(TAG, "Transition Type " + event.getTransitionType());


                String type = "";
                switch (event.getActivityType()) {
                    case DetectedActivity.WALKING:
                        type = "WALKING";
                        break;
                    case DetectedActivity.STILL:
                        type = "STILL";
                        break;
                    case DetectedActivity.RUNNING:
                        type = "STILL";
                        break;
                    case DetectedActivity.ON_BICYCLE:
                        type = "STILL";
                        break;
                }

                StepsBroadcastObserver.getInstance().updateValue(type);

            }
        }
    }

}