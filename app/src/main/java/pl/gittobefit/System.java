package pl.gittobefit;
import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.sqlite.db.SupportSQLiteOpenHelper;


import pl.gittobefit.database.dao.DaoUser;
import pl.gittobefit.network.Connection;
import pl.gittobefit.user.User;

/***
 * klasa do usunięcia
 * bedzie dosepna do konca springu po nowym roku
 * miej więcej do 11.01.2021
 * @author Czapla
 * @deprecated
 */
public class System
{
    /**
     * @deprecated
     */
    public final static Connection conect = new Connection("https://77.55.236.227:8443");
    /**
     * @deprecated
     */
    public final static User user = new User();
}
