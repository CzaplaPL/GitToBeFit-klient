package pl.gittobefit.database.dao;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

import pl.gittobefit.database.entity.training.SavedTraining;
import pl.gittobefit.database.entity.training.relation.TrainingWithForm;

@Dao
public interface ITrainingDao
{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addTraining(SavedTraining training);

   @Transaction
   @Query("SELECT * FROM WorkoutForm")
   public List<TrainingWithForm> getTraining();
}
