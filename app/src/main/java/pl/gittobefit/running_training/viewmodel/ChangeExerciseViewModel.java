package pl.gittobefit.running_training.viewmodel;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import pl.gittobefit.WorkoutDisplay.objects.Training;
import pl.gittobefit.database.entity.training.Exercise;
import pl.gittobefit.database.repository.TrainingRepository;

public class ChangeExerciseViewModel extends ViewModel {
    private ArrayList<Exercise> listExercises = new ArrayList<>();
    private int indexExercise;
    private TrainingRepository trainingRepository;
    private MutableLiveData<Integer> indexChange;

    public ChangeExerciseViewModel() { }

    public ArrayList<Exercise> getListExercises() { return listExercises; }

    public void setListExercises(ArrayList<Exercise> listExercises) {
        this.listExercises = listExercises;
    }

    public long getIndexExercise() { return indexExercise; }

    public void setIndexExercise(int indexExercise) {
        this.indexExercise = indexExercise;
    }

    public void init(int displayToExercise, Context context){
        this.trainingRepository = TrainingRepository.getInstance(context);
        this.listExercises.add(this.trainingRepository.getExercise(displayToExercise));
    }

    public void addTrainingToList(Exercise exercise)
    {
        listExercises.add(exercise);
    }

    public Exercise getExercise()
    {
        return listExercises.get(indexExercise);
    }

    public MutableLiveData<Integer> getIndexChange() {
        if (indexChange == null) {
            indexChange = new MutableLiveData<Integer>();
        }
        return indexChange;
    }
}
