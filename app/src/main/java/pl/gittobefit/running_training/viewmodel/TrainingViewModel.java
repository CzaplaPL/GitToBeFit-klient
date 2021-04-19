package pl.gittobefit.running_training.viewmodel;

import android.content.Context;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import pl.gittobefit.database.entity.training.Exercise;
import pl.gittobefit.database.entity.training.SavedTraining;
import pl.gittobefit.database.entity.training.relation.TrainingWithForm;
import pl.gittobefit.database.pojo.ExerciseExecutionPOJODB;
import pl.gittobefit.database.repository.TrainingRepository;

public class TrainingViewModel extends ViewModel
{

    private TrainingWithForm trainingWithForm;
    private TrainingRepository trainingRepository;
    private ArrayList<Exercise> listExercises;
    private ArrayList<ExerciseExecutionPOJODB> planList;
    private int indexExercise;
    private int numberOfSeries;
    private final int FIRST_EXERCISE_INDEX = 0;
    private final int FIRST_SERIES = 1;

    public TrainingViewModel()
    {
        this.numberOfSeries = FIRST_SERIES;
        this.indexExercise = FIRST_EXERCISE_INDEX;
    }

    public ArrayList<Exercise> getListExercises()
    {
        return listExercises;
    }

    public void setListExercises(ArrayList<Exercise> listExercises)
    {
        this.listExercises = listExercises;
    }

    public int getIndexExercise()
    {
        return indexExercise;
    }

    public void setIndexExercise(int indexExercise)
    {
        this.indexExercise = indexExercise;
    }

    public void init(int displayToTraining, Context context)
    {
        this.trainingRepository = TrainingRepository.getInstance(context);
        this.trainingWithForm = trainingRepository.getTraining(displayToTraining);
        SavedTraining training = trainingWithForm.training;
        this.listExercises = trainingRepository.getExercisesForPlanList(
                training.getPlanList().get(training.getTrainingDay())
        );
        this.planList = training.getPlanList().get(training.getTrainingDay());
    }

    public Exercise getExercise()
    {
        return listExercises.get(indexExercise);
    }

    public ExerciseExecutionPOJODB getExerciseExecution()
    {
        return planList.get(indexExercise);
    }

    public TrainingWithForm getTrainingWithForm()
    {
        return trainingWithForm;
    }

    public void setTrainingWithForm(TrainingWithForm trainingWithForm)
    {
        this.trainingWithForm = trainingWithForm;
    }

    public int getNumberOfSeries()
    {
        return numberOfSeries;
    }

    public void setNumberOfSeries(int numberOfSeries)
    {
        this.numberOfSeries = numberOfSeries;
    }

    public ArrayList<ExerciseExecutionPOJODB> getPlanList()
    {
        return planList;
    }

    public void setPlanList(ArrayList<ExerciseExecutionPOJODB> planList)
    {
        this.planList = planList;
    }

    public boolean nextExercise()
    {
        if(indexExercise < listExercises.size() - 1)
        {
            indexExercise++;
            numberOfSeries = 1;
        }else
        {
            return false;
        }
        return true;
    }

    public void nextSeries()
    {
        numberOfSeries++;
    }

    public boolean nextExerciseForCircuit()
    {
        if(indexExercise < listExercises.size() - 1)
        {
            indexExercise++;
        }else if(numberOfSeries < getExerciseExecution().getSeries())
        {
            indexExercise = 0;
            numberOfSeries++;
        }else
        {
            return false;
        }
        return true;
    }

    public void endTraining()
    {
        if(trainingWithForm.training.isNextDay())
        {
            trainingRepository.setNextDay(
                    trainingWithForm.training.getTrainingDay() + 1,
                    trainingWithForm.training.getId()
            );
        }else
        {
            trainingRepository.setNextDay(0, trainingWithForm.training.getId());
        }
    }
}
