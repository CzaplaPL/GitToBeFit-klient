package pl.gittobefit.database.repository;

import java.util.ArrayList;

import pl.gittobefit.WorkoutDisplay.objects.ExerciseExecution;
import pl.gittobefit.WorkoutDisplay.objects.Training;
import pl.gittobefit.WorkoutDisplay.objects.TrainingPlan;
import pl.gittobefit.database.AppDataBase;
import pl.gittobefit.database.entity.training.Exercise;
import pl.gittobefit.database.entity.training.SavedTraining;
import pl.gittobefit.database.entity.training.relation.TrainingWithForm;
import pl.gittobefit.database.pojo.ExerciseExecutionPOJODB;
import pl.gittobefit.user.User;


public class TrainingRepository
{
    private final AppDataBase base;

    public TrainingRepository(AppDataBase base)
    {
        this.base = base;
    }

    public TrainingWithForm add(Training training)
    {
        long idForm = base.workoutFormDao().addForm(training.getTrainingForm());
        saveExercise(training.getPlanList());
        long idTraining = base.trainingDao().addTraining(new SavedTraining(idForm, training.getPlanList()));
        return base.trainingDao().getTraining(idTraining);
    }

    public TrainingWithForm getTraining(long id)
    {
        return base.trainingDao().getTraining(id);
    }

    public ArrayList<SavedTraining> getAllTrainings()
    {
        return new ArrayList<>(base.trainingDao().getAllTrainings());
    }

    public ArrayList<TrainingWithForm> getAllTrainingsForUser(String id)
    {
        return new ArrayList<>(base.trainingDao().getAllTrainingForUser(id));
    }

    public ArrayList<Training> getTrainingsToSend()
    {
        ArrayList<Training> trainingsToSend = new ArrayList<>();
        ArrayList<TrainingWithForm> trainingsDB = getAllTrainingsForUser(User.getInstance().getIdSerwer());
        for(TrainingWithForm trainingDB : trainingsDB)
        {
            ArrayList<ArrayList<ExerciseExecutionPOJODB>> planListDB = trainingDB.training.getPlanList();
            ArrayList<TrainingPlan> trainingPlansServer = new ArrayList<>();
            for(int i = 0; i < planListDB.size(); i++)
            {
                ArrayList<ExerciseExecution> exerciseExecutionsServer = new ArrayList<>();
                ArrayList<Exercise> exercisesDB = getExerciseForPlanList(planListDB.get(i));
                for(ExerciseExecutionPOJODB exercise : planListDB.get(i))
                {
                    for(int j = 0; j < exercisesDB.size(); j++)
                    {
                        if(exercise.getExerciseId() == exercisesDB.get(j).getId())
                        {
                            exerciseExecutionsServer.add(new ExerciseExecution(exercise, exercisesDB.get(j)));
                            exercisesDB.remove(j);
                            break;
                        }
                    }
                }
                trainingPlansServer.add(new TrainingPlan(
                        exerciseExecutionsServer,
                        planListDB.get(i).get(0).getIdServerPlanList(),
                        planListDB.get(i).get(0).getIdServerTraining()));
            }
            trainingsToSend.add(new Training(trainingDB.form, trainingPlansServer));
        }
        trainingsDB = getAllTrainingsForUser("");
        for(TrainingWithForm trainingDB : trainingsDB)
        {
            ArrayList<ArrayList<ExerciseExecutionPOJODB>> planListDB = trainingDB.training.getPlanList();
            ArrayList<TrainingPlan> trainingPlansServer = new ArrayList<>();
            for(int i = 0; i < planListDB.size(); i++)
            {
                ArrayList<ExerciseExecution> exerciseExecutionsServer = new ArrayList<>();
                ArrayList<Exercise> exercisesDB = getExerciseForPlanList(planListDB.get(i));
                for(ExerciseExecutionPOJODB exercise : planListDB.get(i))
                {
                    for(int j = 0; j < exercisesDB.size(); j++)
                    {
                        if(exercise.getExerciseId() == exercisesDB.get(j).getId())
                        {
                            exerciseExecutionsServer.add(new ExerciseExecution(exercise, exercisesDB.get(j)));
                            exercisesDB.remove(j);
                            break;
                        }
                    }

                }
                trainingPlansServer.add(new TrainingPlan(exerciseExecutionsServer, planListDB.get(i).get(0).getIdServerPlanList(), planListDB.get(i).get(0).getIdServerTraining()));
            }
            trainingsToSend.add(new Training(trainingDB.form, trainingPlansServer));
        }
        return trainingsToSend;
    }

    public void synchroniseUser()
    {
        base.trainingDao().addUserForTrainings(User.getInstance().getIdSerwer());
    }

    private ArrayList<Exercise> getExerciseForPlanList(ArrayList<ExerciseExecutionPOJODB> planList)
    {
        ArrayList<Exercise> toReturn = new ArrayList<>();
        for(ExerciseExecutionPOJODB plan : planList)
        {
            toReturn.add(base.exerciseDao().getExercise(plan.getExerciseId()));
        }
        return toReturn;
    }

    private void saveExercise(ArrayList<TrainingPlan> planList)
    {
        for(int i = 0; i < planList.size(); i++)
        {
            TrainingPlan plan = planList.get(i);
            for(int j = 0; j < plan.getExercisesExecutions().size(); j++)
            {
                base.exerciseDao().addExercise(plan.getExerciseExecution(j).getExercise());
            }
        }
    }
}
