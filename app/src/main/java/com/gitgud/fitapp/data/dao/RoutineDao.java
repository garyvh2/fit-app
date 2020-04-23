package com.gitgud.fitapp.data.dao;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.gitgud.fitapp.data.model.Exercise;
import com.gitgud.fitapp.data.model.Routine;
import com.gitgud.fitapp.data.model.RoutineAndExercise;

import java.util.List;

@Dao
public interface  RoutineDao {


    @Insert
     void insertAllExercises(List<Exercise> exercises);

    @Insert
     long insert(Routine routine);

    @Transaction
    @Query("Select * from routine where id = :id")
      LiveData<RoutineAndExercise> getRoutineWithExercise(long id);
    @Transaction
    @Query("Select * from routine")
     LiveData<List<RoutineAndExercise>> getRoutines();
}
