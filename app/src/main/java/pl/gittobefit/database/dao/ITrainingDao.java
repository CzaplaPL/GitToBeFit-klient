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
import pl.gittobefit.database.pojo.ExerciseExecutionPOJODB;

@Dao
public interface ITrainingDao
{
    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long addTraining(SavedTraining training);

   @Transaction
   @Query("SELECT * FROM SavedTraining")
   public List<TrainingWithForm> getAllTrainingWitForm();

    @Transaction
    @Query("SELECT * FROM SavedTraining WHERE id = :id")
    public TrainingWithForm getTraining(long id);

    @Transaction
    @Query("SELECT * FROM SavedTraining ")
    public List<TrainingWithForm> getAllTrainings();

    @Transaction
    @Query("SELECT * FROM SavedTraining  WHERE idUser=:id ")
    List<TrainingWithForm> getAllTrainingForUser(String id);

    @Transaction
    @Query("UPDATE SavedTraining SET idUser = :id WHERE idUser=\"\" ")
    void addUserForTrainings(String id);

    @Transaction
    @Query("UPDATE SavedTraining SET planList = :exerciseExecutionPOJODBS WHERE id=:id")
    void updateTrainingPlan(ArrayList<ArrayList<ExerciseExecutionPOJODB>> exerciseExecutionPOJODBS, long id);

    @Query("DELETE FROM SavedTraining  WHERE id = :id")
    public void deleteTrainingInDataBase(long id);

    @Query("UPDATE SavedTraining SET trainingName = :newName WHERE id = :id")
    public void updateTrainingNameInDataBase(String newName, long id);


}
