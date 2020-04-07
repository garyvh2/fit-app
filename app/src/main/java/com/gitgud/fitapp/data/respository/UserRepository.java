package com.gitgud.fitapp.data.respository;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.gitgud.fitapp.data.dao.UserDao;
import com.gitgud.fitapp.data.model.User;
import com.gitgud.fitapp.provider.database.AppDatabase;

public class UserRepository {
    private UserDao userDao;

    public UserRepository(Context context) {
        AppDatabase appDatabase = AppDatabase.getInstance(context);
        userDao = appDatabase.userDao();
    }

    public void insert(User user) {
        AsyncTask.execute(() -> userDao.insert(user));
    }

    public void update(User user) {
        AsyncTask.execute(() -> userDao.update(user));
    }

    public void delete(User user) {
        AsyncTask.execute(() -> userDao.delete(user));
    }

    public LiveData<User> getCurrentUser() { return userDao.getCurrentUser(); }
}
