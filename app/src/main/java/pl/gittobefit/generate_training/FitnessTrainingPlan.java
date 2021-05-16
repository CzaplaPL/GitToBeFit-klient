package pl.gittobefit.generate_training;

import android.content.Context;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import pl.gittobefit.database.AppDataBase;
import pl.gittobefit.database.entity.training.Exercise;
import pl.gittobefit.database.entity.training.SavedTraining;
import pl.gittobefit.database.entity.training.WorkoutForm;
import pl.gittobefit.database.pojo.ExerciseExecutionPOJODB;

public class FitnessTrainingPlan implements TrainingPlanGenerator
{
    private static final String TRAINING_TYPE = "FITNESS";
    private static final int SINGLE_STEP = 3;
    private static final List<String> SPECIFIED_ARMS = List.of("SHOULDERS", "TRICEPS", "BICEPS");
    private static final List<String> SPECIFIED_LEGS = List.of("THIGHS", "CALVES");

    private AppDataBase base;
    private WorkoutForm trainingForm;

    public FitnessTrainingPlan(Context context)
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

        ThreadLocalRandom randomIndexGen = ThreadLocalRandom.current();
        List<Exercise> rolledExercises = new ArrayList<>();
        List<String> bodyPartsFromForm = trainingForm.getBodyParts();

        // na kazda partie ciala jest losowane jedno cwiczenie
        while(rolledExercises.size() < exercisesToGet)
        {
            long counter = 0;
            for(String bodyPart : bodyPartsFromForm)
            {
                // filtrowanie cwiczen na odpowiednia partie ciala (aktualnie obslugiwana)
                List<Exercise> exercisesForSpecifiedBodyPart = getExercisesForSpecifiedBodyPart(filteredListOfExercises, bodyPart);
                // sprawdzenie, czy na pewno cos jest
                if(!exercisesForSpecifiedBodyPart.isEmpty())
                {
                    // randomowa liczba z przedzialu 0 <= liczba < filtrowane.size
                    int random = randomIndexGen.nextInt(exercisesForSpecifiedBodyPart.size());
                    Exercise exercise = exercisesForSpecifiedBodyPart.get(random);
                    // sprawdzenie, czy takiego cwiczenia nie ma w srodku
                    if(!rolledExercises.contains(exercise))
                    {
                        rolledExercises.add(exercise);
                    }
                    // usuniecie wylosowanego cwiczenia zeby sie nie powtorzylo
                    filteredListOfExercises.remove(exercise);
                }else
                {
                    counter++;
                }
                if(rolledExercises.size() == exercisesToGet)
                    break;
            }
            // jezeli nie ma cwiczen to wykonaj akcje
            if(filteredListOfExercises.isEmpty())
            {
                break;
            }else if(counter == bodyPartsFromForm.size())
            {
                break;
            }
        }

        ArrayList<ExerciseExecutionPOJODB> exercisesExecutions = getExercisesExecutions(rolledExercises);
        Collections.shuffle(exercisesExecutions);

        SavedTraining training = new SavedTraining(
                this.trainingForm.checkIfScheduleTypeIsCircuit() ? DEFAULT_CIRCUIT_COUNT : NOT_APPLICABLE,
                DEFAULT_BREAK_TIME
        );
        ArrayList<ArrayList<ExerciseExecutionPOJODB>> planList = new ArrayList<>();
        planList.add(exercisesExecutions);
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
            for(ExerciseExecutionPOJODB exercisesExecution : exercisesExecutions)
            {
                Exercise exercise = base.exerciseDao().getExercise(exercisesExecution.getExerciseId());
                String bodyPartOfExercise = exercise.getBodyPart();
                if(!trainingForm.getBodyParts().contains(bodyPartOfExercise))
                    throw new NotValidTrainingException("wrong exercise");
            }
            ArrayList<String> bodyParts = mapToSpecifiedBodyParts(trainingForm.getBodyParts());

            for(ExerciseExecutionPOJODB exercisesExecution : exercisesExecutions)
            {
                Exercise exercise = base.exerciseDao().getExercise(exercisesExecution.getExerciseId());
                if(!bodyParts.contains(exercise.getBodyPart()))
                    throw new NotValidTrainingException("wrong exercise");
            }
        }
    }

    private List<Exercise> getExercisesForSpecifiedBodyPart(List<Exercise> exercises, String bodyPartName)
    {
        List<Exercise> filtered = new ArrayList<>();
        switch(bodyPartName)
        {
            case "LEGS":
            {
                for(String spec : SPECIFIED_LEGS)
                {
                    for(Exercise exercise : exercises)
                    {
                        if(exercise.getBodyPart().toUpperCase().equals(spec))
                        {
                            filtered.add(exercise);
                        }
                    }
                }
                return filtered;
            }
            case "ARMS":
            {
                for(String spec : SPECIFIED_ARMS)
                {
                    for(Exercise exercise : exercises)
                    {
                        if(exercise.getBodyPart().toUpperCase().equals(spec))
                        {
                            filtered.add(exercise);
                        }
                    }
                }
                return filtered;
            }
            default:
            {
                for(Exercise exercise : exercises)
                {
                    if(exercise.getBodyPart().toUpperCase().equals(bodyPartName))
                    {
                        filtered.add(exercise);
                    }
                }
                return filtered;
            }
        }
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

    private ArrayList<String> mapToSpecifiedBodyParts(ArrayList<String> bodyParts)
    {
        ArrayList<String> bodyPartsExtended = new ArrayList<>();
        for(String bodyPart : bodyParts)
        {
            if(bodyPart.equalsIgnoreCase("ARMS"))
            {
                bodyPartsExtended.addAll(SPECIFIED_ARMS);
            }else if(bodyPart.equalsIgnoreCase("LEGS"))
            {
                bodyPartsExtended.addAll(SPECIFIED_LEGS);
            }else
            {
                bodyPartsExtended.add(bodyPart);
            }
        }
        return bodyPartsExtended;
    }
}
