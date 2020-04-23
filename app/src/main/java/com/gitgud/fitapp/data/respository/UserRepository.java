package com.gitgud.fitapp.data.respository;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.gitgud.fitapp.data.dao.UserDao;
import com.gitgud.fitapp.data.model.HistoryStat;
import com.gitgud.fitapp.data.model.User;
import com.gitgud.fitapp.provider.database.AppDatabase;

import java.util.Arrays;
import java.util.List;

public class UserRepository {
    private UserDao userDao;

    public UserRepository(Context context) {
        AppDatabase appDatabase = AppDatabase.getInstance(context);
        userDao = appDatabase.userDao();
    }

    public void insert(User user, List<HistoryStat> historyStatList) {
        AsyncTask.execute(() -> {
            long id = userDao.insert(user);
            for (HistoryStat stat : historyStatList) {
                stat.setUserId(id);
            }
            userDao.insertAll(historyStatList);
        });
    }

    public void update(User user) {
        AsyncTask.execute(() -> userDao.update(user));
    }

    public void delete(User user) {
        AsyncTask.execute(() -> userDao.delete(user));
    }

    public void insertNewStat(HistoryStat historyStat) {AsyncTask.execute(()-> userDao.insertAll(Arrays.asList(historyStat)));}
    public  void insertStats(List<HistoryStat> historyStatList) {AsyncTask.execute(() -> userDao.insertAll(historyStatList));}

    public LiveData<User> getCurrentUser() { return userDao.getCurrentUser(); }
    public LiveData<HistoryStat> getCurrentStat(){ return  userDao.getCurrentStat();}
}
