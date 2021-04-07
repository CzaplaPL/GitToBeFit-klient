package pl.gittobefit.WorkoutDisplay.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import pl.gittobefit.database.entity.training.relation.TrainingWithForm;

public class InitiationTrainingDisplayLayoutViewModel extends ViewModel
{
    private MutableLiveData<Integer> numberOfClickedTraining = new MutableLiveData<>();
    private ArrayList<TrainingWithForm> trainingWithForms = new ArrayList<>();

    public LiveData<Integer> getPosition() {

        return numberOfClickedTraining;
    }

    public void setNumberOfClickedTraining(Integer i) {
        numberOfClickedTraining.setValue(i);
    }

    public ArrayList<TrainingWithForm> getTrainingWithForms() {
        return trainingWithForms;
    }

    public void setTrainingWithForms(ArrayList<TrainingWithForm> trainingWithForms) {
        this.trainingWithForms = trainingWithForms;
    }

    public void addTrainingWithForm(TrainingWithForm trainingWithForm)
    {
        trainingWithForms.add(trainingWithForm);
    }
}
