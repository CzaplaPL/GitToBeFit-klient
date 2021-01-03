package pl.gittobefit.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import pl.gittobefit.database.dao.DaoUser;
import pl.gittobefit.database.entity.EntityUser;

/**
 * klasa bazy danych
 */
@Database(entities = {EntityUser.class}, version = 1)
public abstract class AppDataBase extends RoomDatabase
{
    private static volatile AppDataBase INSTANCE;

    /**
     * daje dostep do dao usera
     * @return funkcje dao encji user
     */
    public abstract DaoUser user();

    /**
     * zwraca instancje bazy danych
     * @return instancja bazy danych
     */
    public static AppDataBase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDataBase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDataBase.class, "room-db")
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}