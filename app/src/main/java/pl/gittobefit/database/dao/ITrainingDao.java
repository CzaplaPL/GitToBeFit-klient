package pl.gittobefit.database.dao;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.ArrayList;
import java.util.List;

import pl.gittobefit.database.entity.training.SavedTraining;
import pl.gittobefit.database.entity.training.relation.TrainingWithForm;

@Dao
public interface ITrainingDao
{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long addTraining(SavedTraining training);

   @Transaction
   @Query("SELECT * FROM SavedTraining")
   public List<TrainingWithForm> getAllTrainingWhitForm();

    @Transaction
    @Query("SELECT * FROM SavedTraining WHERE id = :id")
    public TrainingWithForm getTraining(long id);

    @Transaction
    @Query("SELECT * FROM SavedTraining ")
    public List<SavedTraining> getAllTraining();

    @Transaction
    @Query("SELECT id, idForm,generationDate,trainingName FROM SavedTraining ")
    public List<SavedTraining> getInfoForTrainingList();

    @Query("SELECT * FROM SavedTraining WHERE id = :id")
    public SavedTraining getOneTraining(long id);

    @Query("UPDATE SavedTraining SET trainingName = :newName WHERE id = :id")
    public void updateTrainingNameInDataBase(String newName ,long id);
}
