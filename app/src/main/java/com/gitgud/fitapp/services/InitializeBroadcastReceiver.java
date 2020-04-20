package com.gitgud.fitapp.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.gitgud.fitapp.data.respository.ActivityRepository;
import com.gitgud.fitapp.data.respository.StepsRepository;

public class InitializeBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        ActivityRepository activityRepository = new ActivityRepository(context);
        activityRepository.createDailyActivities();

        StepsRepository stepsRepository = new StepsRepository(context);
        stepsRepository.createDailySteps();

        Intent stepsServiceIntent = new Intent(context, StepsService.class);
        context.startService(stepsServiceIntent);
    }

}
