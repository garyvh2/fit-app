package com.gitgud.fitapp.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.gitgud.fitapp.data.model.ActivityRecord;

import java.util.Date;
import java.util.List;

@Dao
public interface ActivityRecordDao {
    @Insert
    long insert(ActivityRecord activityRecord);

    @Update
    void update(ActivityRecord activityRecord);

    @Delete
    void delete(ActivityRecord activityRecord);

    @Query("SELECT * FROM activity_record_table WHERE active = :active AND date BETWEEN :from AND :to limit 1")
    LiveData<ActivityRecord> findActivityRecordByActiveAndTime(boolean active, Date from, Date to);

    @Query("SELECT * FROM activity_record_table WHERE type = :type AND date BETWEEN :from AND :to limit 1")
    LiveData<ActivityRecord> findActivityRecordByTypeAndTime(String type, Date from, Date to);

    @Query("SELECT * FROM activity_record_table WHERE date BETWEEN :from AND :to")
    LiveData<List<ActivityRecord>> findActivityRecordsByTime(Date from, Date to);

    @Query("SELECT * FROM activity_record_table WHERE date BETWEEN :from AND :to")
    List<ActivityRecord> findActivityRecordsByTimeSync(Date from, Date to);

    @Query("SELECT * FROM activity_record_table WHERE active = :active")
    List<ActivityRecord> getAllActive(Boolean active);

    @Query("SELECT * FROM activity_record_table WHERE active = :active AND date BETWEEN :from AND :to limit 1")
    ActivityRecord findActivityRecordByActiveAndTimeSync(boolean active, Date from, Date to);

    @Query("SELECT * FROM activity_record_table WHERE type = :type AND date BETWEEN :from AND :to limit 1")
    ActivityRecord findActivityRecordByTypeAndTimeSync(String type, Date from, Date to);
}
