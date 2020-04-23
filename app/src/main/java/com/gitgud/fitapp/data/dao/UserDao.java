package com.gitgud.fitapp.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.gitgud.fitapp.data.model.HistoryStat;
import com.gitgud.fitapp.data.model.User;
import com.gitgud.fitapp.data.model.UserAndHistoryStat;

import java.util.List;


@Dao
public interface UserDao {
    @Insert
    long insert(User user);

    @Update
    void update(User user);

    @Insert
    void insertAll(List<HistoryStat> historyStatList);

    @Delete
    void delete(User user);

    @Query("SELECT * FROM user_table limit 1")
    User getCurrentUserSync();

    @Query("SELECT * FROM user_table limit 1")
    LiveData<User> getCurrentUser();

    @Query("SELECT * from user_table where id = :id")
    LiveData<UserAndHistoryStat> getUserHistoryStat(long id);

    @Query("SELECT * from history_stat ORDER BY id DESC LIMIT 1")
    LiveData<HistoryStat> getCurrentStat();
}
