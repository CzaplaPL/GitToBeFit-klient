package pl.gittobefit.database.dao;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import pl.gittobefit.database.entity.EntityUser;

@Dao
public interface DaoUser
{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addUser(EntityUser user );

    @Query("SELECT * FROM EntityUser")
    List<EntityUser> getUser();
}
