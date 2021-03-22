package pl.gittobefit.database.dao;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

import pl.gittobefit.database.entity.training.WorkoutForm;

@Dao
public interface ITrainingDao
{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long  addForm(WorkoutForm form );



}
