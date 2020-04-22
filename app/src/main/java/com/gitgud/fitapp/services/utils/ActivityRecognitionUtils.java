package com.gitgud.fitapp.services.utils;

import com.google.android.gms.location.DetectedActivity;

public class ActivityRecognitionUtils {
    public static String getActivityName(int activity) {
        switch (activity) {
            case DetectedActivity.IN_VEHICLE:
                return "in vehicle";
            case DetectedActivity.ON_BICYCLE:
                return "on bicycle";
            case DetectedActivity.RUNNING:
                return "running";
            case DetectedActivity.STILL:
                return "still";
            case DetectedActivity.WALKING:
                return "walking";
            default:
                return "unknown";
        }
    }

    public static String getTransitionName(int transition){
        if(transition==0)
            return "ENTER";
        return "EXIT";
    }
}
