package pl.gittobefit.workoutforms.object;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class Training
{
    private TrainingForm trainingForm;
    private ArrayList<TrainingPlan> planList = new ArrayList<>();
    private String generationDate;


    public TrainingForm getTrainingForm() {
        return trainingForm;
    }

    public ArrayList<TrainingPlan> getPlanList() {
        return planList;
    }

    public TrainingPlan getTrainingPlan(int i)
    {
        return planList.get(i);
    }

    public String getGenerationDate() {
        return generationDate;
    }

    public void setGenerationDate(String generationDate) {
        this.generationDate = generationDate;
    }
}
