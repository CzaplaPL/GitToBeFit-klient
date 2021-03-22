package pl.gittobefit.database.training;

import java.util.ArrayList;

import pl.gittobefit.database.dao.IExerciseDao;
import pl.gittobefit.database.dao.ITrainingDao;
import pl.gittobefit.database.entity.training.SaveTraining;
import pl.gittobefit.network.object.Training;
import pl.gittobefit.workoutforms.object.TrainingPlan;


public abstract class trainingRepository
{
    public abstract ITrainingDao trainingDao();
    public abstract IExerciseDao exerciseDao();

    public void add(Training training)
    {

       Long idForm = trainingDao().addForm(training.getTrainingForm());
       saveExercise(training.getPlanList());
       trainingDao().addTraining(new SaveTraining(idForm,training.getPlanList()));
    }

    private void saveExercise(ArrayList<TrainingPlan> planList)
    {
        for (int i = 0; i < planList.size() ; i++)
        {
            TrainingPlan plan = planList.get(i);
            for (int j = 0; j < plan.getExercisesExecutions().size(); j++)
            {
                exerciseDao().addExercise(plan.getExerciseExecution(j).getExercise());
            }
        }
    }
}
