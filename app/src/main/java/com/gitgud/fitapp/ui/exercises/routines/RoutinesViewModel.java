package com.gitgud.fitapp.ui.exercises.routines;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.gitgud.fitapp.data.model.RoutineAndExercise;
import com.gitgud.fitapp.data.respository.RoutineRepository;

import java.util.List;

import io.reactivex.annotations.NonNull;

public class RoutinesViewModel extends AndroidViewModel {

    private RoutineRepository routineRepository;

    private LiveData<List<RoutineAndExercise>> routines;

    public RoutinesViewModel(@NonNull Application application) {
        super(application);
        routineRepository =  new RoutineRepository(application);

        routines = routineRepository.getRoutines();
    }

}
