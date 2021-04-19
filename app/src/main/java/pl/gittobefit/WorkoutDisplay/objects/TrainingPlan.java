package pl.gittobefit.WorkoutDisplay.objects;

import java.util.ArrayList;

import pl.gittobefit.database.entity.training.Exercise;
import pl.gittobefit.database.pojo.ExerciseExecutionPOJODB;
import pl.gittobefit.database.repository.TrainingRepository;

public class TrainingPlan
{
    private int id;
    private int trainingId;
    private ArrayList<ExerciseExecution> exercisesExecutions = new ArrayList<>();

    public TrainingPlan()
    {
    }

    public TrainingPlan(ArrayList<ExerciseExecution> exerciseExecutions, int idPlanList, int idTraining)
    {
        this.exercisesExecutions = exerciseExecutions;
        this.id = idPlanList;
        this.trainingId = idTraining;
    }


    public TrainingPlan(ArrayList<ExerciseExecutionPOJODB> exerciseExecutionPOJODBS, ArrayList<Exercise> exercisesDB)
    {
        if(exerciseExecutionPOJODBS.size() != 0)
        {
            final ExerciseExecutionPOJODB exerciseExecutionPOJODB = exerciseExecutionPOJODBS.get(0);
            int id = exerciseExecutionPOJODB.getIdServerPlanList();
            int trainingId = exerciseExecutionPOJODB.getIdServerTraining();
            for(ExerciseExecutionPOJODB exercisePOJODB : exerciseExecutionPOJODBS)
            {
                for(Exercise exercise : exercisesDB)
                {
                    if(exercise.getId() == exercisePOJODB.getExerciseId())
                    {
                        exercisesExecutions.add(new ExerciseExecution(exercisePOJODB, exercise));
                        exercisesDB.remove(exercise);
                        break;
                    }
                }
            }
        }
    }

    public int getId()
    {
        return id;
    }

    public int getTrainingId()
    {
        return trainingId;
    }

    public ArrayList<ExerciseExecution> getExercisesExecutions()
    {
        return exercisesExecutions;
    }

    public ExerciseExecution getExerciseExecution(int i)
    {
        return exercisesExecutions.get(i);
    }

    public void setExercisesExecutions(ArrayList<ExerciseExecution> exercisesExecutions)
    {
        this.exercisesExecutions = exercisesExecutions;
    }
}
