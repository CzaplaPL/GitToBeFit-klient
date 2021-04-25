package pl.gittobefit.WorkoutDisplay.viewmodel;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import pl.gittobefit.database.entity.training.relation.TrainingWithForm;
import pl.gittobefit.user.User;

public class InitiationTrainingDisplayLayoutViewModel extends ViewModel
{
    private MutableLiveData<Integer> numberOfClickedTraining = new MutableLiveData<>();
    private ArrayList<TrainingWithForm> trainingWithForms = new ArrayList<>();
    private ArrayList<Boolean> states = new ArrayList<Boolean>();
    private MutableLiveData<String> trainingName;
    private MutableLiveData<Integer> trainingTime;
    private MutableLiveData<Integer> trainingSeries;
    private MutableLiveData<Integer> trainingCount;
    private int lastIndex = -1;

    public InitiationTrainingDisplayLayoutViewModel()
    {
    }

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
        this.trainingWithForms.clear();
        this.trainingWithForms.addAll(trainingWithForms);
    }

    public void addTrainingWithForm(TrainingWithForm trainingWithForm)
    {
        trainingWithForms.add(trainingWithForm);
    }

    public void setStatesExerciseLists(int count)
    {
        states.clear();
        for (int i = 0; i < count; i++)
        {
            states.add(false);
        }
    }
    public void setState(int index)
    {
        states.set(index, !states.get(index));
    }

    public ArrayList<Boolean> getStates() {
        return states;
    }

    public int getLastIndex() {
        return lastIndex;
    }

    public void setLastIndex(int lastIndex) {
        this.lastIndex = lastIndex;
    }

    public MutableLiveData<String> getCurrentName() {
        if (trainingName == null) {
            trainingName = new MutableLiveData<String>();
        }
        return trainingName;
    }
    public MutableLiveData<Integer> getCurrentTime() {
        if (trainingTime == null) {
            trainingTime = new MutableLiveData<Integer>();
        }
        return trainingTime;
    }
    public MutableLiveData<Integer> getCurrentCount() {
        if (trainingCount == null) {
            trainingCount = new MutableLiveData<Integer>();
        }
        return trainingCount;
    }
    public MutableLiveData<Integer> getCurrentSeries() {
        if (trainingSeries == null) {
            trainingSeries = new MutableLiveData<Integer>();
        }
        return trainingSeries;
    }

}
