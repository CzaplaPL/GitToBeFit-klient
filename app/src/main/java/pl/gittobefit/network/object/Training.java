package pl.gittobefit.network.object;

import androidx.annotation.NonNull;

import java.util.ArrayList;

import pl.gittobefit.database.entity.training.WorkoutForm;
import pl.gittobefit.workoutforms.object.TrainingPlan;

public class Training
{
    private WorkoutForm trainingForm;
    private ArrayList<TrainingPlan> planList = new ArrayList<>();

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
