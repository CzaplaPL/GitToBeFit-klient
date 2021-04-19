package pl.gittobefit.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import pl.gittobefit.database.conventer.TrainingConverter;
import pl.gittobefit.database.dao.ExerciseDao;
import pl.gittobefit.database.dao.FormDao;
import pl.gittobefit.database.dao.TrainingDao;
import pl.gittobefit.database.dao.UserDao;
import pl.gittobefit.database.entity.UserEntity;
import pl.gittobefit.database.entity.training.Exercise;
import pl.gittobefit.database.entity.training.SavedTraining;
import pl.gittobefit.database.entity.training.WorkoutForm;

/**
 * klasa bazy danych
 */
@Database(entities = {UserEntity.class, WorkoutForm.class, Exercise.class, SavedTraining.class}, version =9, exportSchema = false)
@TypeConverters({TrainingConverter.class})
public abstract class AppDataBase extends RoomDatabase
{
    private static volatile AppDataBase INSTANCE;
    public abstract UserDao userDao();
    public abstract TrainingDao trainingDao();
    public abstract ExerciseDao exerciseDao();
    public abstract FormDao workoutFormDao();

    public static AppDataBase getInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDataBase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDataBase.class, "room-db")
                            .allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
