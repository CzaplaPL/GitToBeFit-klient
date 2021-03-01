package pl.gittobefit.workoutforms.object;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class TrainingPlan
{
    int id;
    int trainingId;
    ArrayList<ExerciseExecution> exercisesExecutions = new ArrayList<>();

    @NonNull
    @Override
    public String toString() {
        return id +  " "  + trainingId + " " + exercisesExecutions.get(0).exercise.name;
    }
}
