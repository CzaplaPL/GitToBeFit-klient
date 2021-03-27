package pl.gittobefit.database.dao;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import pl.gittobefit.database.entity.UserEntity;
import pl.gittobefit.database.entity.training.Exercise;

@Dao
public interface IExerciseDao
{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addExercise(Exercise exercise);

    @Query("SELECT * FROM Exercise WHERE id = :id")
    Exercise getExerciseList(long id);



}
