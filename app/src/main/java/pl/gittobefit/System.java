package pl.gittobefit;
import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.sqlite.db.SupportSQLiteOpenHelper;


import pl.gittobefit.database.dao.DaoUser;
import pl.gittobefit.network.Conection;
import pl.gittobefit.user.User;


public class System
{
    public final static Conection conect = new Conection("http://77.55.236.227:8080");
    public final static User user = new User();
}
