package pl.gittobefit.workoutforms.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class InitiationTrainingDisplayLayoutViewModel extends ViewModel
{
    private MutableLiveData<Integer> numberOfClickedTraining = new MutableLiveData<>();

    public LiveData<Integer> getPosition() {

        return numberOfClickedTraining;
    }

    public void setNumberOfClickedTraining(Integer i) {
        numberOfClickedTraining.setValue(i);
    }
}
