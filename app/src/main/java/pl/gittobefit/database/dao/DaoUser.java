package pl.gittobefit.database.dao;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import pl.gittobefit.database.entity.EntityUser;

/**
 * Dao dla encji uzytkownnika
 * @deprecated bedzie zmiana przetrzymywania automatycznego logowania
 */
@Dao
public interface DaoUser
{
    /**
     * umieszczanie uzytkownika w bazie
     * @param user encja uzytkownika
     * @author czapla
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addUser(EntityUser user );

    /**
     * zwraca uzytkownikow z bazy
     * @return encje uzytkownika
     * @author czapla
     */
    @Query("SELECT * FROM EntityUser")
    List<EntityUser> getUser();
}
