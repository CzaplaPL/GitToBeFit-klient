package pl.gittobefit.workoutforms.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import pl.gittobefit.workoutforms.object.BodyParts;
import pl.gittobefit.workoutforms.object.TrainingDetails;

public class DetailsViewModel extends ViewModel
{
    private int position1;
    private int position2;
    private int position3;
    private ArrayList<BodyParts> list;

    private TrainingDetails trainingDetails;

    private final MutableLiveData<TrainingDetails> selected = new MutableLiveData<>();
    public void select(TrainingDetails item) {
        trainingDetails = item;
        selected.setValue(trainingDetails);
    }

    public LiveData<TrainingDetails> getSelected() {
        return selected;
    }

    public int getPositionSpinner1()
    {
        return position1;
    }

    public void setPositionSpinner1(int value)
    {
        position1 = value;
    }

    public int getPositionSpinner2()
    {
        return position2;
    }

    public void setPositionSpinner2(int value)
    {
        position2 = value;
    }

    public int getPositionSpinner3()
    {
        return position3;
    }

    public void setPositionSpinner3(int value)
    {
        position3 = value;
    }

    public void setTrainingDetails(String type, String subType, String duration)
    {
        trainingDetails.setTrainingType(type);
        trainingDetails.setDuration(duration);
        trainingDetails.setSubType(subType);
        select(trainingDetails);
    }

    public void setTrainingDetails(String subType)
    {
        trainingDetails.setSubType(subType);
        select(trainingDetails);
    }
    public void setTrainingDetails(String duration, int i)
    {
        i = 0;
        trainingDetails.setDuration(duration);
        select(trainingDetails);
    }
    public void setTrainingDetails(int i)
    {
        i = i*3 + 9;
        trainingDetails.setDuration(String.valueOf(i) + " minut");
        select(trainingDetails);
    }



    public ArrayList<BodyParts> getList() {
        if (list == null)
        {
            list = new ArrayList<>();


        }

        return list;
    }

    public void setList(ArrayList<BodyParts> list) {
        this.list = list;
    }


}
