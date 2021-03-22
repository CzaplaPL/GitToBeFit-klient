package pl.gittobefit.workoutforms.object;

import androidx.annotation.NonNull;

import java.util.ArrayList;

import pl.gittobefit.database.entity.training.WorkoutForm;

public class Training
{
    WorkoutForm trainingForm;
    ArrayList<TrainingPlan> planList = new ArrayList<>();

    @NonNull
    @Override
    public String toString() {
        return planList.get(0).exercisesExecutions.get(0).exercise.name;
    }

    public WorkoutForm getTrainingForm() {
        return trainingForm;
    }

    public ArrayList<TrainingPlan> getPlanList() {
        return planList;
    }

    public TrainingPlan getTrainingPlan(int i)
    {
        return planList.get(i);
    }

}
