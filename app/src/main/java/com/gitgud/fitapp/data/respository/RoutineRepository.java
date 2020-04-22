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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RoutineRepository {
    private RoutineDao routineDao;

    public RoutineRepository(Context context) {
        AppDatabase appDatabase = AppDatabase.getInstance(context);
        routineDao = appDatabase.routineDao();
    }

    public void initBaseRoutine () {
        AsyncTask.execute(() -> {
            try {
                Routine rout = new Routine(1,"Arms routine");
                Exercise exercise1 = new Exercise(1, "jumping jacks"
                        ,"is a physical jumping exercise performed by jumping to a position with the legs spread wide and the hands going overhead, sometimes in a clap, and then returning to a position with the feet together and the arms at the sides."
                        ,"", "https://image.shutterstock.com/image-vector/woman-doing-jumping-jack-exercise-260nw-1457642288.jpg", "", 250f, 0, 16);
                Exercise exercise2 = new Exercise(2, "jumping jacks"
                        ,"is a physical jumping exercise performed by jumping to a position with the legs spread wide and the hands going overhead, sometimes in a clap, and then returning to a position with the feet together and the arms at the sides."
                        ,"", "https://image.shutterstock.com/image-vector/woman-doing-jumping-jack-exercise-260nw-1457642288.jpg", "", 250f, 50, 0);
                exercise1.setRoutineId(1);
                exercise2.setRoutineId(1);
                List<Exercise> exerciseList = Arrays.asList(exercise1, exercise2);
                RoutineAndExercise newRoutine = new RoutineAndExercise();
                newRoutine.routine = rout;
                newRoutine.exerciseList = exerciseList;
                routineDao.insert(rout);
                routineDao.insertAll(exerciseList);
            }catch (Exception error) {
                Log.e("errr", error.getMessage());
            }

        });

    }



    public LiveData<RoutineAndExercise> getRoutine(int id) {
        return routineDao.getRoutineWithExercise(id);
    }

    public  LiveData<List<RoutineAndExercise>> getRoutines() {
        return  routineDao.getRoutines();
    }
    public  void  insertRoutineWithExercises (RoutineAndExercise routineAndExercise) {
       routineDao.insert(routineAndExercise.routine);

       routineDao.insertAll(routineAndExercise.exerciseList);
    }

    public void createDefaultRoutines(){
        MediatorLiveData<RoutineAndExercise> stepsRecordMediator = new MediatorLiveData<>();
        stepsRecordMediator.addSource(routineDao.getRoutineWithExercise(1), routine -> {
            if (routine == null) {
                Routine rout = new Routine(1,"Arms routine");
                Exercise exercise1 = new Exercise(1, "jumping jacks"
                        ,"is a physical jumping exercise performed by jumping to a position with the legs spread wide and the hands going overhead, sometimes in a clap, and then returning to a position with the feet together and the arms at the sides."
                ,"", "https://image.shutterstock.com/image-vector/woman-doing-jumping-jack-exercise-260nw-1457642288.jpg", "", 250f, 0, 16);
                Exercise exercise2 = new Exercise(1, "jumping"
                        ,"is a physical jumping exercise performed by jumping to a position with the legs spread wide and the hands going overhead, sometimes in a clap"
                        ,"", "https://image.shutterstock.com/image-photo/young-woman-doing-exercise-jumping-260nw-502211857.jpg", "", 250f, 0, 16);
                List<Exercise> exerciseList = Arrays.asList(exercise1, exercise2);
                RoutineAndExercise newRoutine = new RoutineAndExercise();
                newRoutine.routine = rout;
                newRoutine.exerciseList = exerciseList;
                insertRoutineWithExercises(newRoutine);
            }
        });

    }
}
