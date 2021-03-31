package pl.gittobefit.running_training.viewmodel;

import android.content.Context;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import pl.gittobefit.database.entity.training.Exercise;
import pl.gittobefit.database.entity.training.relation.TrainingWithForm;
import pl.gittobefit.database.repository.TrainingRepository;

public class TrainingViewModel extends ViewModel {

    private TrainingWithForm trainingWithForm;
    private TrainingRepository trainingRepository;
    private ArrayList<Exercise> listExercises;
    private int indexExercise;

    public TrainingViewModel()
    {


        this.indexExercise = 0;
    }

    public ArrayList<Exercise> getListExercises() {
        return listExercises;
    }

    public void setListExercises(ArrayList<Exercise> listExercises) {
        this.listExercises = listExercises;
    }

    public int getIndexExercise() {
        return indexExercise;
    }

    public void setIndexExercise(int indexExercise) {
        this.indexExercise = indexExercise;
    }

    public void init(int displayToTraining, Context context)
    {
        this.trainingRepository = TrainingRepository.getInstance(context);
        this.trainingWithForm = trainingRepository.getTraining(displayToTraining);
        this.listExercises = trainingRepository.getExerciseForPlanList(trainingWithForm.training.getPlanList().get(trainingWithForm.training.getTrainingdays()));
    }

    public Exercise getExercise() {
        return listExercises.get(indexExercise);
    }
}
