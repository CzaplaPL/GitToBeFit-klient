package pl.gittobefit.database.repository;

import java.util.ArrayList;

import pl.gittobefit.WorkoutDisplay.objects.Training;
import pl.gittobefit.WorkoutDisplay.objects.TrainingPlan;
import pl.gittobefit.database.AppDataBase;
import pl.gittobefit.database.entity.training.SavedTraining;
import pl.gittobefit.database.entity.training.relation.TrainingWithForm;


public class TrainingRepository
{
    private AppDataBase base;
    public TrainingRepository(AppDataBase base)
    {
        this.base = base;
    }
    public TrainingWithForm add(Training training)
    {
        long idForm = base.workoutForm().addForm(training.getTrainingForm());
        saveExercise(training.getPlanList());
        long idTraining = base.training().addTraining(new SavedTraining(idForm, training.getPlanList()));
        return base.training().getTraining(idTraining);
    }

    public TrainingWithForm getTraining(long id)
    {
        return base.training().getTraining(id);
    }

    public ArrayList<SavedTraining> getAllTraining()
    {
        return new ArrayList<>(base.training().getAllTraining());
    }
    private void saveExercise(ArrayList<TrainingPlan> planList)
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
