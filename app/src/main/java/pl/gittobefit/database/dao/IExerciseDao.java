package pl.gittobefit.database.dao;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

import pl.gittobefit.database.entity.training.Exercise;

@Dao
public interface IExerciseDao
{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addExercise(Exercise exercise );



}
