package com.gitgud.fitapp.data.respository;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.gitgud.fitapp.data.dao.RoutineDao;
import com.gitgud.fitapp.data.model.Exercise;
import com.gitgud.fitapp.data.model.Routine;
import com.gitgud.fitapp.data.model.RoutineAndExercise;
import com.gitgud.fitapp.provider.database.AppDatabase;

import java.util.Arrays;
import java.util.List;

public class RoutineRepository {
    private RoutineDao routineDao;

    public RoutineRepository(Context context) {
        AppDatabase appDatabase = AppDatabase.getInstance(context);
        routineDao = appDatabase.routineDao();
    }

    public void initBaseRoutine (List<RoutineAndExercise> routineAndExercises) {
        AsyncTask.execute(() -> {
            try {
                for (RoutineAndExercise routineAndExercise : routineAndExercises) {
                   long id =  routineDao.insert(routineAndExercise.routine);
                   for (Exercise exercise : routineAndExercise.exerciseList) {
                       exercise.setRoutineId(id);
                   }
                    routineDao.insertAllExercises(routineAndExercise.exerciseList);
                }
            }catch (Exception error) {
                Log.e("errr", error.getMessage());
            }

        });

    }



    public LiveData<RoutineAndExercise> getRoutine(long id) {
        return routineDao.getRoutineWithExercise(id);
    }

    public  LiveData<List<RoutineAndExercise>> getRoutines() {
        return  routineDao.getRoutines();
    }
    public  void  insertRoutineWithExercises (RoutineAndExercise routineAndExercise) {
       routineDao.insert(routineAndExercise.routine);

       routineDao.insertAllExercises(routineAndExercise.exerciseList);
    }

    public LiveData<Routine> getTodayRoutine(String day) {
        return routineDao.getTodayRoutine(day);
    }
}
