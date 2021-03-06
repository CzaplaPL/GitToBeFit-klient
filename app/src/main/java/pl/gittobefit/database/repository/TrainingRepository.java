package pl.gittobefit.database.repository;

import android.content.Context;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import pl.gittobefit.WorkoutDisplay.objects.ExerciseExecution;
import pl.gittobefit.WorkoutDisplay.objects.Training;
import pl.gittobefit.WorkoutDisplay.objects.TrainingPlan;
import pl.gittobefit.database.AppDataBase;
import pl.gittobefit.database.entity.training.Exercise;
import pl.gittobefit.database.entity.training.SavedTraining;
import pl.gittobefit.database.entity.training.WorkoutForm;
import pl.gittobefit.database.entity.training.relation.ExerciseToEquipment;
import pl.gittobefit.database.entity.training.relation.TrainingTypesToExercise;
import pl.gittobefit.database.entity.training.relation.TrainingWithForm;
import pl.gittobefit.database.pojo.ExerciseExecutionPOJODB;
import pl.gittobefit.network.WorkoutFormsServices;
import pl.gittobefit.user.User;
import pl.gittobefit.workoutforms.object.EquipmentItem;
import pl.gittobefit.workoutforms.object.exercise.TrainingTypes;


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
                        training.getGenerationDate(),
                        training.getDayOfTraining()
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

    private ArrayList<TrainingWithForm> getOfflineTrainingsWithoutUser(String idServer)
    {
        ArrayList<TrainingWithForm> loadTraining = new ArrayList<>(base.trainingDao().getAllTrainingWithoutUser(idServer));
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


    public ArrayList<Training> getTrainingToSendAfterChangesById(int id)
    {
        ArrayList<Training> trainingsToSend = new ArrayList<>();
        TrainingWithForm trainingWithForm = getTraining(id);
        ArrayList<TrainingWithForm> trainingsInDB = new ArrayList<>();
        trainingsInDB.add(trainingWithForm);
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
            for(ExerciseExecution exercisesExecution : plan.getExercisesExecutions())
            {
                for(EquipmentItem equipmentItem : exercisesExecution.getExercise().getEquipmentsNeeded())
                {
                    base.exerciseDao().addExerciseToEquipment(new ExerciseToEquipment(
                            equipmentItem.getId(),
                            exercisesExecution.getExercise().getId()
                    ));
                }
                for(TrainingTypes trainingType : exercisesExecution.getExercise().getTrainingTypes())
                {
                    base.exerciseDao().addTrainingTypesToExercise(new TrainingTypesToExercise(
                            trainingType.getName(),
                            exercisesExecution.getExercise().getId()
                    ));
                }
                base.exerciseDao().addExercise(new Exercise(exercisesExecution.getExercise()));
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

    public ArrayList<TrainingWithForm> loadTrainings()
    {
        User user = User.getInstance();
        ArrayList<TrainingWithForm> loadedTraining = new ArrayList<>();
        if(user.getLoggedBy() != User.WayOfLogin.NO_LOGIN)
        {
            loadedTraining.addAll(getAllTrainingsForUser(user.getIdServer()));
            loadedTraining.addAll(getOfflineTrainingsWithoutUser(user.getIdServer()));
        }else
        {
            loadedTraining.addAll(base.trainingDao().getOfflineTraining());
        }

        return loadedTraining;
    }


    public TrainingWithForm addOfflineTraining(SavedTraining training, WorkoutForm form)
    {
        long idForm = base.workoutFormDao().addForm(form);
        training.setIdForm(idForm);
        long idTraining = base.trainingDao().addTraining(training);
        TrainingWithForm savedTraining = base.trainingDao().getTraining(idTraining);
        loadedTrainingWithForm.put((long) savedTraining.training.getId(), savedTraining);
        return savedTraining;
    }
}
