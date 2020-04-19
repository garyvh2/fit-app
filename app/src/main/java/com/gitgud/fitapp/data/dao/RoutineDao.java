package com.gitgud.fitapp.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.gitgud.fitapp.data.model.RoutineAndExercise;

import java.util.List;

@Dao
public interface RoutineDao {

    @Insert
    void insert(RoutineAndExercise routineAndExercise);

    @Query("Select * from routine where id = :id")
    LiveData<RoutineAndExercise> getRoutineWithExercise(int id);

    @Query("Select * from routine")
    LiveData<List<RoutineAndExercise>> getRoutines();
}
