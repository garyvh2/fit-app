package com.gitgud.fitapp.data.dao;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.gitgud.fitapp.data.model.Exercise;
import com.gitgud.fitapp.data.model.Routine;
import com.gitgud.fitapp.data.model.RoutineAndExercise;

import java.util.List;

@Dao
public interface  RoutineDao {


    @Insert
     void insertAll(List<Exercise> exercises);

    @Insert
     void insert(Routine routine);

    @Query("Select * from routine where id = :id")
      LiveData<RoutineAndExercise> getRoutineWithExercise(int id);

    @Query("Select * from routine")
     LiveData<List<RoutineAndExercise>> getRoutines();
}
