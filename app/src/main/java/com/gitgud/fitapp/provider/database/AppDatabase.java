package com.gitgud.fitapp.provider.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.gitgud.fitapp.data.dao.ActivityRecordDao;
import com.gitgud.fitapp.data.dao.GoalsDao;
import com.gitgud.fitapp.data.dao.RoutineDao;
import com.gitgud.fitapp.data.dao.StepsRecordDao;
import com.gitgud.fitapp.data.dao.UserDao;
import com.gitgud.fitapp.data.dao.WaterRecordDao;
import com.gitgud.fitapp.data.model.ActivityRecord;
import com.gitgud.fitapp.data.model.Exercise;
import com.gitgud.fitapp.data.model.Goal;
import com.gitgud.fitapp.data.model.Routine;
import com.gitgud.fitapp.data.model.RoutineAndExercise;
import com.gitgud.fitapp.data.model.StepsRecord;
import com.gitgud.fitapp.data.model.User;
import com.gitgud.fitapp.data.model.WaterRecord;
import com.gitgud.fitapp.utils.Converters;

import java.util.Arrays;
import java.util.List;


@Database(entities = {User.class, ActivityRecord.class, StepsRecord.class, WaterRecord.class, Goal.class, Routine.class, Exercise.class}, version = 6)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase INSTANCE;

    public static synchronized AppDatabase getInstance(Context context) {
        if(INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(
                    context.getApplicationContext(),
                    AppDatabase.class,
                    "app_database"
            )
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return INSTANCE;
    };

    public abstract UserDao userDao();
    public abstract ActivityRecordDao activityRecordDao();
    public abstract StepsRecordDao stepsRecordDao();
    public  abstract GoalsDao goalsDao();
    public abstract WaterRecordDao waterRecordDao();
    public  abstract RoutineDao routineDao();

    private static AppDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
   //         AsyncTask.execute(() -> {
//                UserDao userDao = INSTANCE.userDao();
//                userDao.insert(new User("Gary Valverde Hampton"));
  //          });
        }
    };
}
