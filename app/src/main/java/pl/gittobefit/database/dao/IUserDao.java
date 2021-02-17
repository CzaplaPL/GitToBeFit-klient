package pl.gittobefit.database.dao;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import pl.gittobefit.database.entity.EntityUser;

/**
 * Dao dla encji uzytkownnika
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

    @Query("UPDATE EntityUser SET token = :token WHERE id = :id")
    void setToken(String token, int id);

    @Query("DELETE FROM EntityUser WHERE id = :userId")
    void deleteByUserId(int userId);

}
