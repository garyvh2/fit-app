package com.gitgud.fitapp.ui.exercises.routine;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.gitgud.fitapp.data.model.RoutineAndExercise;
import com.gitgud.fitapp.data.respository.GoalsRepository;
import com.gitgud.fitapp.data.respository.RoutineRepository;
import com.gitgud.fitapp.data.respository.UserRepository;
import com.gitgud.fitapp.data.source.UserDataSource;

import java.util.List;

import io.reactivex.annotations.NonNull;

public class RoutineViewModel  extends AndroidViewModel {

    private RoutineRepository routineRepository;

    private LiveData<RoutineAndExercise> routine;

    public RoutineViewModel(@NonNull Application application) {
        super(application);
        routineRepository =  new RoutineRepository(application);

    }

    public LiveData<RoutineAndExercise> getRoutine(long id) {
        return routineRepository.getRoutine(id);
    }

    public LiveData<List<RoutineAndExercise>> getRoutines() {
        return routineRepository.getRoutines();
    }

}
