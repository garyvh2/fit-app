package com.gitgud.fitapp.ui.exercises.exercise;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.gitgud.fitapp.data.model.Exercise;
import com.gitgud.fitapp.data.model.RoutineAndExercise;
import com.gitgud.fitapp.data.respository.RoutineRepository;

import java.util.List;

import io.reactivex.annotations.NonNull;

public class ExerciseViewModel extends AndroidViewModel {

    private RoutineRepository routineRepository;

    private LiveData<RoutineAndExercise> routine;
    private MutableLiveData<Exercise> currentExercise;
    private  int index;

    public ExerciseViewModel(@NonNull Application application) {
        super(application);
        routineRepository =  new RoutineRepository(application);
        currentExercise =new MutableLiveData<>();
        currentExercise.postValue(null);

    }

    public LiveData<RoutineAndExercise> getRoutine(long id) {
        return routineRepository.getRoutine((int)id);
    }

    public LiveData<RoutineAndExercise> getRoutine() {
        return routine;
    }


    public int getIndex() {
        return index;
    }

    public void increaseIndex() {
        this.index += 1;
    }

    public MutableLiveData<Exercise> getCurrentExercise() {
        return currentExercise;
    }

    public void setCurrentExercise(Exercise currentExercise) {
        this.currentExercise.postValue(currentExercise);
    }

    public  String getAmountExercise() {
        if(currentExercise.getValue() != null) {
            Exercise ex = currentExercise.getValue();
            if(ex.getTime() != 0) {
                return ex.getTime() + "s";
            } else if(ex.getRepetitions() != 0) {
                return "X" + ex.getRepetitions();
            }
        }
        return "";

    }
}
