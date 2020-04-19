package com.gitgud.fitapp.data.respository;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.gitgud.fitapp.data.dao.RoutineDao;
import com.gitgud.fitapp.data.model.RoutineAndExercise;

import java.util.List;

public class RoutineRepository {

    private RoutineDao routineDao;

    public void createRoutine(RoutineAndExercise routineAndExercise) {
        AsyncTask.execute(() -> routineDao.insert(routineAndExercise));
    }

    public LiveData<RoutineAndExercise> getRoutine(int id) {
        return routineDao.getRoutineWithExercise(id);
    }

    public  LiveData<List<RoutineAndExercise>> getRoutines() {
        return  routineDao.getRoutines();
    }
}
