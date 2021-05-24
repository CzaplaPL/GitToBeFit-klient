package pl.gittobefit.database.dao;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.ArrayList;
import java.util.List;

import pl.gittobefit.database.entity.equipment.Checksum;
import pl.gittobefit.database.entity.equipment.EquipmentType;
import pl.gittobefit.database.entity.training.Exercise;
import pl.gittobefit.database.entity.training.relation.ExerciseToEquipment;
import pl.gittobefit.database.entity.training.relation.TrainingTypesToExercise;
import pl.gittobefit.database.entity.training.relation.TrainingWithForm;
import pl.gittobefit.workoutforms.object.EquipmentItem;

@Dao
public interface IExerciseDao
{
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void addExercise(Exercise exercise);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void addExerciseToEquipment(ExerciseToEquipment exerciseToEquipment);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void addTrainingTypesToExercise(TrainingTypesToExercise trainingTypesToExercise);

    @Transaction
    @Query("SELECT * FROM Exercise WHERE id = :id")
    Exercise getExercise(long id);

    @Transaction
    @Query("SELECT equipmentId FROM ExerciseToEquipment WHERE exerciseId = :id")
    List<Integer> getNeededEquipment(long id);

    @Transaction
    @Query("SELECT exerciseId FROM TrainingTypesToExercise WHERE trainingType = :trainingType")
    List<Integer> getAllByTrainingTypes_Name(String trainingType);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertExercise(ArrayList<Exercise> equipmentTypes);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertExerciseEquipment(ArrayList<ExerciseToEquipment> exerciseToEquipment);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertExerciseTypes(ArrayList<TrainingTypesToExercise> trainingTypesToExercise);
}
