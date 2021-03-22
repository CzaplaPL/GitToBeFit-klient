package pl.gittobefit.workoutforms.object;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class TrainingPlan
{
    int id;
    int trainingId;
    private ArrayList<ExerciseExecution> exercisesExecutions = new ArrayList<>();



    public int getId() {
        return id;
    }

    public int getTrainingId() {
        return trainingId;
    }

    public ArrayList<ExerciseExecution> getExercisesExecutions() {
        return exercisesExecutions;
    }

    public ExerciseExecution getExerciseExecution(int i)
    {
        return exercisesExecutions.get(i);
    }
}
