package pl.gittobefit.WorkoutDisplay.objects;

import java.util.ArrayList;

public class TrainingPlan
{
   private int id;
   private int trainingId;
   private ArrayList<ExerciseExecution> exercisesExecutions = new ArrayList<>();

    public TrainingPlan() {}

    public TrainingPlan(ArrayList<ExerciseExecution> exerciseExecutions, int idPlanList, int idTraining)
    {
        this.exercisesExecutions =exerciseExecutions;
        this.id=idPlanList;
        this.trainingId=idTraining;
    }


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
