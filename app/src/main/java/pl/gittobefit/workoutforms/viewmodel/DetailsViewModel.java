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
    private ArrayList<BodyParts> splitList;
    private ArrayList<BodyParts> fitnessList;

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
        trainingDetails.setDuration(i + " minut");
        select(trainingDetails);
    }


    public ArrayList<BodyParts> getSplitList() {
        if (splitList == null)
        {
            splitList = new ArrayList<>();

            splitList.add(new BodyParts("Klatka piersiowa"));
            splitList.add(new BodyParts("Brzuch"));
            splitList.add(new BodyParts("Plecy"));
            splitList.add(new BodyParts("Uda"));
            splitList.add(new BodyParts("Łydki"));
            splitList.add(new BodyParts("Biceps"));
            splitList.add(new BodyParts("Triceps"));
            splitList.add(new BodyParts("Ramiona"));
        }

        return splitList;
    }

    public void setSplitList(ArrayList<BodyParts> splitList) {
        this.splitList = splitList;
    }

    public ArrayList<BodyParts> getFitnessList() {
        if (fitnessList == null)
        {
            fitnessList = new ArrayList<>();

            fitnessList.add(new BodyParts("Klatka piersiowa"));
            fitnessList.add(new BodyParts("Brzuch"));
            fitnessList.add(new BodyParts("Plecy"));
            fitnessList.add(new BodyParts("Nogi"));
            fitnessList.add(new BodyParts("Ramiona"));
        }
        return fitnessList;
    }

    public void setFitnessList(ArrayList<BodyParts> fitnessList) {
        this.fitnessList = fitnessList;
    }

    public void clear()
    {
        setPositionSpinner1(0);
        setPositionSpinner2(0);
        setPositionSpinner3(0);

        fitnessList = new ArrayList<>();
        fitnessList.add(new BodyParts("Klatka piersiowa"));
        fitnessList.add(new BodyParts("Brzuch"));
        fitnessList.add(new BodyParts("Plecy"));
        fitnessList.add(new BodyParts("Nogi"));
        fitnessList.add(new BodyParts("Ramiona"));
        setFitnessList(fitnessList);

        splitList = new ArrayList<>();
        splitList.add(new BodyParts("Klatka piersiowa"));
        splitList.add(new BodyParts("Brzuch"));
        splitList.add(new BodyParts("Plecy"));
        splitList.add(new BodyParts("Uda"));
        splitList.add(new BodyParts("Łydki"));
        splitList.add(new BodyParts("Biceps"));
        splitList.add(new BodyParts("Triceps"));
        splitList.add(new BodyParts("Ramiona"));
        setSplitList(splitList);


    }

}
