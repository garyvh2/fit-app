package com.gitgud.fitapp.ui.modules.water;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.gitgud.fitapp.data.model.WaterRecord;
import com.gitgud.fitapp.data.respository.WaterRepository;
import com.gitgud.fitapp.utils.DateUtils;

public class WaterConsumeViewModel extends AndroidViewModel {
    private WaterRepository waterRepository;
    private LiveData<WaterRecord> waterRecordLiveData;

    public WaterConsumeViewModel(@NonNull Application application) {
        super(application);
        waterRepository = new WaterRepository(application);
        waterRecordLiveData = this.waterRepository.findActivityRecordByTime(DateUtils.minDate(), DateUtils.maxDate());
    }

    public LiveData<WaterRecord> findActivityRecordByTime() {
        return waterRecordLiveData;
    }

    public void updateTodayWater(WaterRecord waterRecord, int quantity) {
        this.waterRepository.updateTodayWater(waterRecord, quantity);
    }

    public void updateSteps(int goal) {
        waterRepository.updateTodayGoal(goal);
    }
}
