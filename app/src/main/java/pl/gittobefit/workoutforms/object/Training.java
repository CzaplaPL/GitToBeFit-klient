package pl.gittobefit.workoutforms.object;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class Training
{
    TrainingForm trainingForm;
    ArrayList<TrainingPlan> planList = new ArrayList<>();
    private String generationDate;

    @NonNull
    @Override
    public String toString() {
        return planList.get(0).exercisesExecutions.get(0).exercise.name;
    }

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
