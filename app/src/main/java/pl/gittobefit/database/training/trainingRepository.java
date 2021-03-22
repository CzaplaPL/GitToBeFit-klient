package pl.gittobefit.database.training;

import android.util.Log;

import androidx.room.Dao;

import pl.gittobefit.database.dao.ITrainingDao;
import pl.gittobefit.workoutforms.object.Training;


public abstract class trainingRepository
{
    public abstract ITrainingDao training();

    public void add(Training training)
    {

       Long idForm = training().addForm(training.getTrainingForm());
    }
}
