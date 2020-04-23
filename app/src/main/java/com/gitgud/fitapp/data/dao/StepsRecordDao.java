package com.gitgud.fitapp.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.gitgud.fitapp.data.model.StepsRecord;

import java.util.Date;

@Dao
public interface StepsRecordDao {
    @Insert
    long insert(StepsRecord stepsRecord);

    @Update
    void update(StepsRecord stepsRecord);

    @Delete
    void delete(StepsRecord stepsRecord);

    @Query("SELECT * FROM steps_record_table WHERE date BETWEEN :from AND :to limit 1")
    LiveData<StepsRecord> findStepsRecordByDate(Date from, Date to);

    @Query("SELECT * FROM steps_record_table WHERE date BETWEEN :from AND :to limit 1")
    StepsRecord findStepsRecordByDateSync(Date from, Date to);
}
