package pl.gittobefit.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import pl.gittobefit.database.conventer.TrainingConverter;
import pl.gittobefit.database.dao.IUserDao;
import pl.gittobefit.database.entity.UserEntity;
import pl.gittobefit.database.entity.training.WorkoutForm;
import pl.gittobefit.database.training.trainingRepository;

/**
 * klasa bazy danych
 */
@Database(entities = {UserEntity.class, WorkoutForm.class}, version = 4, exportSchema = false)
@TypeConverters({TrainingConverter.class})
public abstract class AppDataBase extends RoomDatabase
{
    private static volatile AppDataBase INSTANCE;

    /**
     * daje dostep do dao usera
     * @return funkcje dao encji user
     */
    public abstract IUserDao user();
    public  trainingRepository training;

    /**
     * zwraca instancje bazy danych
     * @return instancja bazy danych
     */
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
