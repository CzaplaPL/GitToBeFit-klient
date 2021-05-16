package pl.gittobefit.generate_training;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import pl.gittobefit.database.AppDataBase;
import pl.gittobefit.database.entity.training.Exercise;
import pl.gittobefit.database.entity.training.SavedTraining;
import pl.gittobefit.database.entity.training.WorkoutForm;
import pl.gittobefit.database.pojo.ExerciseExecutionPOJODB;

public class CardioTrainingPlan implements TrainingPlanGenerator
{
    private final String TRAINING_TYPE = "CARDIO";
    private final static int SINGLE_STEP = 3;
    private final static int MAX_RAND_TRIES = 100;
    private WorkoutForm trainingForm;

    private AppDataBase base;

    public CardioTrainingPlan(Context context)
    {
        this.base = AppDataBase.getInstance(context);
    }

    @Override
    public SavedTraining create(WorkoutForm trainingForm)
    {
        List<Integer> allExercisesId = base.exerciseDao().getAllByTrainingTypes_Name(TRAINING_TYPE);
        ArrayList<Exercise> allExercises = new ArrayList<>();
        for(Integer exerciseId : allExercisesId)
        {
            allExercises.add(base.exerciseDao().getExercise(exerciseId));
        }
        List<Exercise> filteredListOfExercises = filterAllByAvailableEquipment(allExercises, trainingForm.getEquipmentIDs(), base);
        this.trainingForm = trainingForm;
        int duration = trainingForm.getDuration();
        int exercisesToGet = duration / SINGLE_STEP;
        List<Exercise> rolledExercises = new ArrayList<>();

        ThreadLocalRandom randomIndexGen = ThreadLocalRandom.current();
        // w przypadku, gdy lista cwiczen jest mniejsza niz wymagana to dodaje wszystko do listy
        int counter = 0;
        while(rolledExercises.size() < exercisesToGet)
        {
            if(!filteredListOfExercises.isEmpty())
            {
                int actualRandom = randomIndexGen.nextInt(filteredListOfExercises.size());
                // sciaganie cwiczenia
                Exercise exercise = filteredListOfExercises.get(actualRandom);
                // test, czy na dana partie ciala nie ma za duzo cwiczen

                if(counter < MAX_RAND_TRIES)
                {
                    if(checkIfBodyPartIsNotOverloaded(rolledExercises, exercise))
                    {
                        // jesli nie jest to dodajemy cwiczenie
                        if(!rolledExercises.contains(exercise))
                            rolledExercises.add(exercise);
                        // usuwamy z listy, zeby nie moglo byc wiecej pobrane
                        filteredListOfExercises.remove(exercise);
                    }else
                    {
                        counter++;
                    }
                }else
                {
                    if(!rolledExercises.contains(exercise))
                        rolledExercises.add(exercise);
                    // usuwamy z listy, zeby nie moglo byc wiecej pobrane
                    filteredListOfExercises.remove(exercise);
                }
            }else
            {
                break;
            }
        }

        ArrayList<ExerciseExecutionPOJODB> exerciseExecutions = getExercisesExecutions(rolledExercises);
        SavedTraining training = new SavedTraining(
                this.trainingForm.checkIfScheduleTypeIsCircuit() ? DEFAULT_CIRCUIT_COUNT : NOT_APPLICABLE,
                DEFAULT_BREAK_TIME
        );
        ArrayList<ArrayList<ExerciseExecutionPOJODB>> planList = new ArrayList<>();
        planList.add(exerciseExecutions);
        training.setPlanList(planList);
        return training;

    }

    @Override
    public void validate(SavedTraining trainingPlan, WorkoutForm trainingForm)
    {
        for(ArrayList<ExerciseExecutionPOJODB> exercisesExecutions : trainingPlan.getPlanList())
        {
            // walidacja czasu
            int size = exercisesExecutions.size();
            if(size * 3 != trainingForm.getDuration())
                throw new NotValidTrainingException("wrong exercises count");
        }
    }

    private boolean checkIfBodyPartIsNotOverloaded(List<Exercise> rolledExercises, Exercise exercise)
    {
        String bodyPartToFind = exercise.getBodyPart();
        for(Exercise rolledExercise : rolledExercises)
        {
            if(rolledExercise.getBodyPart().equals(bodyPartToFind)) return false;
        }
        return true;
    }

    private ArrayList<ExerciseExecutionPOJODB> getExercisesExecutions(List<Exercise> rolledExercises)
    {
        ArrayList<ExerciseExecutionPOJODB> execList = new ArrayList<>();
        for(Exercise exercise : rolledExercises)
        {
            ExerciseExecutionPOJODB exerciseExecution = getExactExerciseExecution(exercise, this.trainingForm);
            execList.add(exerciseExecution);
        }
        return execList;
    }
}
