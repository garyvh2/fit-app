package com.gitgud.fitapp.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.gitgud.fitapp.data.model.Goal;
import com.gitgud.fitapp.data.model.User;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface GoalsDao {
    @Insert
    long insert(Goal goal);

    @Update
    void update(Goal goal);

    @Delete
    void delete(Goal goal);

    @Insert
    void  insertAll(List<Goal> goals);

    @Query("SELECT * FROM goals where status = 'ACTIVE' ORDER BY id DESC  limit 1")
    LiveData<Goal> getCurrentGoal();

    @Query("SELECT * FROM goals")
    LiveData<List<Goal>> getAllGoals();
}
