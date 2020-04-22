package com.gitgud.fitapp.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.gitgud.fitapp.data.model.WaterRecord;

import java.util.Date;

@Dao
public interface WaterRecordDao {
    @Insert
    long insert(WaterRecord waterRecord);

    @Update
    void update(WaterRecord waterRecord);

    @Insert
    void delete(WaterRecord waterRecord);

    @Query("SELECT * FROM water_record_table WHERE date BETWEEN :from AND :to LIMIT 1")
    LiveData<WaterRecord> findActivityRecordByTime(Date from, Date to);

    @Query("SELECT * FROM water_record_table WHERE date BETWEEN :from AND :to LIMIT 1")
    WaterRecord findActivityRecordByTimeSync(Date from, Date to);
}
