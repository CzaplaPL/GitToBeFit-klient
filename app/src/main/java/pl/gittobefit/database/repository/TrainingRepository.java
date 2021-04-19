package pl.gittobefit.database.repository;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
    private Map<Long, TrainingWithForm> loadedTrainingWithForm = new HashMap<Long, TrainingWithForm>();
    ;
    private Map<Integer, Exercise> loadedExercises = new HashMap<Integer, Exercise>();
    ;
    private static volatile TrainingRepository INSTANCE;

    private TrainingRepository(Context context)
    {
        this.base = AppDataBase.getInstance(context);
    }

    public static TrainingRepository getInstance(final Context context)
    {
        if(INSTANCE == null)
        {
            synchronized(TrainingRepository.class)
            {
                if(INSTANCE == null)
                {
                    INSTANCE = new TrainingRepository(context);
                }
            }
        }
        return INSTANCE;
    }

    public TrainingWithForm add(Training training)
    {
        long idForm = base.workoutFormDao().addForm(training.getTrainingForm());
        saveExercise(training.getPlanList());
        long idTraining = base.trainingDao().addTraining(new SavedTraining(idForm, training.getPlanList()));
        TrainingWithForm savedTraining = base.trainingDao().getTraining(idTraining);
        loadedTrainingWithForm.put((long) savedTraining.training.getId(), savedTraining);
        return savedTraining;
    }

    public TrainingWithForm getTraining(long id)
    {
        if(loadedTrainingWithForm.get(id) == null)
        {
            loadedTrainingWithForm.put(id, base.trainingDao().getTraining(id));
        }
        return loadedTrainingWithForm.get(id);
    }

    public ArrayList<TrainingWithForm> getAllTrainingsForUser(String id)
    {
        ArrayList<TrainingWithForm> loadTraining = new ArrayList<>(base.trainingDao().getAllTrainingForUser(id));
        for(TrainingWithForm training : loadTraining)
        {
            loadedTrainingWithForm.put((long) training.training.getId(), training);
        }
        return loadTraining;
    }

    public ArrayList<Training> getTrainingsToSend()
    {
        ArrayList<Training> trainingsToSend = new ArrayList<>();
        ArrayList<TrainingWithForm> trainingsDB = getAllTrainingsForUser(User.getInstance().getIdServer());
        for(TrainingWithForm trainingDB : trainingsDB)
        {
            ArrayList<ArrayList<ExerciseExecutionPOJODB>> planListDB = trainingDB.training.getPlanList();
            ArrayList<TrainingPlan> trainingPlansServer = new ArrayList<>();
            for(int i = 0; i < planListDB.size(); i++)
            {
                ArrayList<ExerciseExecution> exerciseExecutionsServer = new ArrayList<>();
                ArrayList<Exercise> exercisesDB = getExercisesForPlanList(planListDB.get(i));
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
                ArrayList<Exercise> exercisesDB = getExercisesForPlanList(planListDB.get(i));
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
        base.trainingDao().addUserForTrainings(User.getInstance().getIdServer());
    }

    public ArrayList<Exercise> getExercisesForPlanList(ArrayList<ExerciseExecutionPOJODB> planList)
    {
        ArrayList<Exercise> toReturn = new ArrayList<>();
        for(ExerciseExecutionPOJODB plan : planList)
        {
            if(loadedExercises.get(plan.getExerciseId()) == null)
            {
                loadedExercises.put(plan.getExerciseId(), base.exerciseDao().getExercise(plan.getExerciseId()));
            }
            toReturn.add(loadedExercises.get(plan.getExerciseId()));
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

    public void setNextDay(int day, int id)
    {
        base.trainingDao().setTrainingDay(day, id);
    }
}
