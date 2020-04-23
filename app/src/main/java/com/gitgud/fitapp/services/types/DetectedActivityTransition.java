package com.gitgud.fitapp.services.types;

public class DetectedActivityTransition {
    private String activity;
    private String transition;

    public DetectedActivityTransition(String activity, String transition) {
        this.activity = activity;
        this.transition = transition;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getTransition() {
        return transition;
    }

    public void setTransition(String transition) {
        this.transition = transition;
    }
}
