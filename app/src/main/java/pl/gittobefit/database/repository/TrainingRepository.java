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
    private Map<Integer, Exercise> loadedExercises = new HashMap<Integer, Exercise>();
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
        long idTraining = base.trainingDao().addTraining(
                new SavedTraining(
                        training.getId(),
                        idForm,
                        training.getPlanList(),
                        training.getTrainingName(),
                        training.getGenerationDate()
                )
        );
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

    public Exercise getExercise(long id)
    {
        return base.exerciseDao().getExercise(id);
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

    public ArrayList<TrainingWithForm> getAllTrainings()
    {
        ArrayList<TrainingWithForm> loadTraining = new ArrayList<>(base.trainingDao().getAllTrainings());
        for(TrainingWithForm training : loadTraining)
        {
            loadedTrainingWithForm.put((long) training.training.getId(), training);
        }
        return loadTraining;
    }

    public ArrayList<Training> getTrainingsToSend()
    {
        ArrayList<Training> trainingsToSend = new ArrayList<>();
        ArrayList<TrainingWithForm> trainingsInDB = getAllTrainingsForUser("");
        for(TrainingWithForm trainingDB : trainingsInDB)
        {
            trainingsToSend.add(new Training(trainingDB, this));
        }
        return trainingsToSend;
    }

    public ArrayList<Training> getTrainingsToSendAfterChanges()
    {
        ArrayList<Training> trainingsToSend = new ArrayList<>();
        ArrayList<TrainingWithForm> trainingsInDB = getAllTrainings();
        for(TrainingWithForm trainingDB : trainingsInDB)
        {
            trainingsToSend.add(new Training(trainingDB, this));
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

    public void deleteAllTrainingsForUser(String id)
    {
        base.runInTransaction(() ->
        {
            base.workoutFormDao().deleteFormForUser(id);
            base.trainingDao().deleteTrainingForUser(id);
        });
    }
}
