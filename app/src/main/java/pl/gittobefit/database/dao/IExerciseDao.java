package pl.gittobefit.database.dao;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.ArrayList;
import java.util.List;

import pl.gittobefit.database.entity.equipment.Checksum;
import pl.gittobefit.database.entity.training.Exercise;
import pl.gittobefit.database.entity.training.relation.TrainingWithForm;
import pl.gittobefit.workoutforms.object.EquipmentItem;

@Dao
public interface IExerciseDao
{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addExercise(Exercise exercise);

    @Transaction
    @Query("SELECT * FROM Exercise WHERE id = :id")
    Exercise getExercise(long id);
}
