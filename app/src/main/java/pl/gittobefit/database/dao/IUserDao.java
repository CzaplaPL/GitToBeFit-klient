package pl.gittobefit.database.dao;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import pl.gittobefit.database.entity.UserEntity;

/**
 * Dao dla encji uzytkownnika
 */
@Dao
public interface IUserDao
{
    /**
     * umieszczanie uzytkownika w bazie
     * @param user encja uzytkownika
     * @author czapla
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addUser(UserEntity user );

    /**
     * zwraca uzytkownikow z bazy
     * @return encje uzytkownika
     * @author czapla
     */
    @Query("SELECT * FROM UserEntity")
    List<UserEntity> getUser();

    @Query("UPDATE UserEntity SET token = :token WHERE id = :id")
    void setToken(String token, int id);

    @Query("DELETE FROM UserEntity WHERE id = :userId")
    void deleteByUserId(int userId);

}
