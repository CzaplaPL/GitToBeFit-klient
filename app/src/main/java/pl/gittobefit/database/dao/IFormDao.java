package pl.gittobefit.database.dao;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

import pl.gittobefit.database.entity.UserEntity;
import pl.gittobefit.database.entity.training.WorkoutForm;

@Dao
public interface IFormDao
{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long addForm(WorkoutForm form);

    @Query("SELECT * FROM WorkoutForm")
    List<WorkoutForm> getTrainingsFrom();

    @Query("DELETE FROM WorkoutForm  WHERE FormId = :id")
    public void deleteFormInDataBase(long id);

    @Transaction
    @Query("DELETE FROM WorkoutForm  WHERE FormId IN (SELECT idForm FROM SavedTraining  WHERE idUser=:userId)")
    public void deleteFormForUser(String userId);

}
