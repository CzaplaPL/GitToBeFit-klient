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

    @Query("SELECT token FROM EntityUser WHERE id = :myId")
    String getToken(int myId);

    @Query("SELECT email FROM EntityUser WHERE id = :myId")
    String getEmail(int myId);

    @Query("SELECT id FROM EntityUser WHERE email = :myEmail")
    int getID(String myEmail);

    @Query("UPDATE EntityUser SET token = :token WHERE id = :id")
    void setToken(String token, int id);

    @Query("UPDATE EntityUser SET id = :myId WHERE email = :myEmail")
    void setID(int myId, String myEmail);

    @Query("DELETE FROM EntityUser WHERE id = :userId")
    void deleteByUserId(int userId);

}
