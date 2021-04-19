package pl.gittobefit.database.dao;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import pl.gittobefit.database.entity.UserEntity;
import pl.gittobefit.database.entity.training.WorkoutForm;

@Dao
public interface FormDao
{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long addForm(WorkoutForm form);

    @Query("SELECT * FROM WorkoutForm")
    List<WorkoutForm> getTrainingsFrom();

    @Query("DELETE FROM WorkoutForm  WHERE FormId = :id")
    public void deleteFormInDataBase(long id);

}
