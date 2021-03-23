package pl.gittobefit.database.repository;

import java.util.ArrayList;

import pl.gittobefit.WorkoutDisplay.objects.Training;
import pl.gittobefit.WorkoutDisplay.objects.TrainingPlan;
import pl.gittobefit.database.AppDataBase;
import pl.gittobefit.database.entity.training.SavedTraining;



public class TrainingRepository
{
    public void add(Training training, AppDataBase base)
    {
        long idForm = base.workoutForm().addForm(training.getTrainingForm());
        saveExercise(training.getPlanList(), base);
        base.training().addTraining(new SavedTraining(idForm, training.getPlanList()));
    }

    public void saveExercise(ArrayList<TrainingPlan> planList, AppDataBase base)
    {
        for(int i = 0; i < planList.size(); i++)
        {
            TrainingPlan plan = planList.get(i);
            for(int j = 0; j < plan.getExercisesExecutions().size(); j++)
            {
                base.exercise().addExercise(plan.getExerciseExecution(j).getExercise());
            }
        }
    }
}
