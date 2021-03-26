package pl.gittobefit.WorkoutDisplay.objects;

import java.util.ArrayList;

public class TrainingPlan
{
   private int id;
   private int trainingId;
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

    public void setExercisesExecutions(ArrayList<ExerciseExecution> exercisesExecutions) {
        this.exercisesExecutions = exercisesExecutions;
    }
}
