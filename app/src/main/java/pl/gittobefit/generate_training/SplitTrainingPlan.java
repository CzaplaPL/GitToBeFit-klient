package pl.gittobefit.generate_training;

import android.content.Context;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import pl.gittobefit.database.AppDataBase;
import pl.gittobefit.database.entity.training.Exercise;
import pl.gittobefit.database.entity.training.SavedTraining;
import pl.gittobefit.database.entity.training.WorkoutForm;
import pl.gittobefit.database.pojo.ExerciseExecutionPOJODB;

public class SplitTrainingPlan implements TrainingPlanGenerator
{
    private final String TRAINING_TYPE = "SPLIT";
    private final List<String> SMALL_BODY_PARTS = List.of("SIXPACK", "CALVES", "BICEPS", "TRICEPS", "SHOULDERS");
    private final List<String> BIG_BODY_PARTS = List.of("CHEST", "BACK", "THIGHS");
    private final int AMOUNT_FOR_SMALL = 3;
    private final int AMOUNT_FOR_BIG = 4;

    private List<Exercise> exercisesWithEquipment = new ArrayList<>();
    private WorkoutForm trainingForm;

    private AppDataBase base;

    public SplitTrainingPlan(Context context)
    {
        this.base = AppDataBase.getInstance(context);
    }

    @Override
    public SavedTraining create(WorkoutForm trainingForm)
    {
        this.initialize(trainingForm);

        Map<String, List<ExerciseExecutionPOJODB>> trainingForBodyPart = assignExercisesToBodyPart();
        List<Map<String, List<ExerciseExecutionPOJODB>>> trainingList = divideTrainingIntoDays(trainingForBodyPart);

        return normalize(trainingList);
    }

    @Override
    public void validate(SavedTraining trainingPlan, WorkoutForm trainingForm) throws NotValidTrainingException
    {
        SplitValidator splitValidator = new SplitValidator();
        splitValidator.validateTraining(trainingPlan, trainingForm, base);
    }

    private void initialize(WorkoutForm trainingForm)
    {
        this.trainingForm = trainingForm;
        List<Integer> exerciseIds = base.exerciseDao().getAllByTrainingTypes_Name(TRAINING_TYPE);

        ArrayList<Exercise> exerciseListFilteredByTrainingType = new ArrayList<>();
        for(Integer exerciseId : exerciseIds)
        {
            exerciseListFilteredByTrainingType.add(base.exerciseDao().getExercise(exerciseId));
        }

        exercisesWithEquipment = filterAllByAvailableEquipment(exerciseListFilteredByTrainingType, trainingForm.getEquipmentIDs(), base);
    }

    private Map<String, List<ExerciseExecutionPOJODB>> assignExercisesToBodyPart()
    {
        ArrayList<ExerciseExecutionPOJODB> exerciseExecutionList = new ArrayList<>();
        HashMap<String, List<ExerciseExecutionPOJODB>> exercisesForBodyPart = new HashMap<>();

        ArrayList<String> trainingFormBodyParts = trainingForm.getBodyParts();

        for(String bodyPart : trainingFormBodyParts)
        {
            ArrayList<Exercise> exercisesWithEquipmentFilteredByBodyPart = getExercisesFilteredByBodyPart(exercisesWithEquipment, bodyPart);
            int amountOfExercises = SMALL_BODY_PARTS.contains(bodyPart) ? AMOUNT_FOR_SMALL : AMOUNT_FOR_BIG;
            for(int i = 0; i < amountOfExercises; i++)
            {
                try
                {
                    ExerciseExecutionPOJODB exerciseExecution = getUniqueExercise(exercisesWithEquipmentFilteredByBodyPart);
                    exerciseExecutionList.add(exerciseExecution);
                }catch(IllegalStateException ignore)
                {
                }
            }
            exercisesForBodyPart.put(bodyPart, exerciseExecutionList);
            exerciseExecutionList = new ArrayList<>();
        }
        return exercisesForBodyPart;
    }

    private ArrayList<Exercise> getExercisesFilteredByBodyPart(List<Exercise> exercises, String bodyPart)
    {
        ArrayList<Exercise> toReturn = new ArrayList<>();
        for(Exercise exercise : exercises)
        {
            if(exercise.getBodyPart().equals(bodyPart))
            {
                toReturn.add(exercise);
            }
        }
        return toReturn;
    }

    private ExerciseExecutionPOJODB getUniqueExercise(List<Exercise> exercisesWithEquipmentFilteredByBodyPart) throws IllegalStateException
    {
        Random random = new Random();
        int randomExerciseIndex;
        Exercise exercise;

        if(isEnough(exercisesWithEquipmentFilteredByBodyPart))
        {
            randomExerciseIndex = random.nextInt(exercisesWithEquipmentFilteredByBodyPart.size());
            exercise = exercisesWithEquipmentFilteredByBodyPart.remove(randomExerciseIndex);
        }else
        {
            throw new IllegalStateException();
        }

        return getExactExerciseExecution(exercise, this.trainingForm);
    }

    private boolean isEnough(List<Exercise> exercises)
    {
        return exercises.size() != 0;
    }

    private List<Map<String, List<ExerciseExecutionPOJODB>>> divideTrainingIntoDays(Map<String, List<ExerciseExecutionPOJODB>> exercisesForBodyPart)
    {
        List<Map<String, List<ExerciseExecutionPOJODB>>> trainingList = new ArrayList<>();

        if(trainingForm.getBodyParts().size() == 0)
            return Collections.emptyList();

        switch(trainingForm.getDaysCount())
        {
            case 1:
                trainingList.add(getMapOfBodyPartsExercisesForDay(exercisesForBodyPart, "THIGHS", "TRICEPS", "SHOULDERS0", "CALVES",
                        "CHEST", "BICEPS", "SIXPACK", "BACK"));
                break;
            case 2:
                trainingList.add(getMapOfBodyPartsExercisesForDay(exercisesForBodyPart, "CHEST", "BICEPS", "SIXPACK", "BACK"));
                trainingList.add(getMapOfBodyPartsExercisesForDay(exercisesForBodyPart, "THIGHS", "TRICEPS", "SHOULDERS0", "CALVES"));
                break;
            case 3:
                trainingList.add(getMapOfBodyPartsExercisesForDay(exercisesForBodyPart, "CHEST", "BICEPS", "SIXPACK"));
                trainingList.add(getMapOfBodyPartsExercisesForDay(exercisesForBodyPart, "BACK", "CALVES"));
                trainingList.add(getMapOfBodyPartsExercisesForDay(exercisesForBodyPart, "THIGHS", "TRICEPS", "SHOULDERS"));
                break;
            case 4:
                trainingList.add(getMapOfBodyPartsExercisesForDay(exercisesForBodyPart, "CHEST", "BICEPS"));
                trainingList.add(getMapOfBodyPartsExercisesForDay(exercisesForBodyPart, "BACK", "CALVES"));
                trainingList.add(getMapOfBodyPartsExercisesForDay(exercisesForBodyPart, "THIGHS", "TRICEPS"));
                trainingList.add(getMapOfBodyPartsExercisesForDay(exercisesForBodyPart, "SIXPACK", "SHOULDERS"));
                break;
            case 5:
                trainingList.add(getMapOfBodyPartsExercisesForDay(exercisesForBodyPart, "CHEST", "BICEPS"));
                trainingList.add(getMapOfBodyPartsExercisesForDay(exercisesForBodyPart, "BACK"));
                trainingList.add(getMapOfBodyPartsExercisesForDay(exercisesForBodyPart, "THIGHS"));
                trainingList.add(getMapOfBodyPartsExercisesForDay(exercisesForBodyPart, "SIXPACK", "SHOULDERS"));
                trainingList.add(getMapOfBodyPartsExercisesForDay(exercisesForBodyPart, "CALVES", "TRICEPS"));
                break;
        }
        return trainingList;
    }

    private Map<String, List<ExerciseExecutionPOJODB>> getMapOfBodyPartsExercisesForDay(Map<String, List<ExerciseExecutionPOJODB>> trainingForBodyPart, String... bodyParts)
    {
        Map<String, List<ExerciseExecutionPOJODB>> map = new HashMap<>();
        for(String part : bodyParts)
            if(trainingForBodyPart.containsKey(part))
                map.put(part, trainingForBodyPart.get(part));

        return map;
    }

    public SavedTraining normalize(List<Map<String, List<ExerciseExecutionPOJODB>>> list)
    {
        Random random = new Random();
        int maxIndex, minIndex, min, max;

        do
        {

            ArrayList<Integer> listOfMapsSize = new ArrayList<>();
            for(Map<String, List<ExerciseExecutionPOJODB>> stringListMap : list)
            {
                listOfMapsSize.add(stringListMap.keySet().size());
            }
            maxIndex = 0;
            minIndex = 0;
            min = list.get(0).size();
            max = list.get(0).size();
            for(int i = 1; i < listOfMapsSize.size(); i++)
            {
                if(listOfMapsSize.get(i) > max)
                {
                    maxIndex = i;
                    max = listOfMapsSize.get(i);
                }
                if(listOfMapsSize.get(i) < min)
                {
                    minIndex = i;
                    min = listOfMapsSize.get(i);
                }
            }

            if(max - min > 1)
            {
                String randomBodyPart = (String) list.get(maxIndex)
                        .keySet()
                        .toArray()[random.nextInt(listOfMapsSize.get(maxIndex))];
                List<ExerciseExecutionPOJODB> exerciseExecutions = list.get(maxIndex).get(randomBodyPart);
                list.get(maxIndex).remove(randomBodyPart);
                list.get(minIndex).put(randomBodyPart, exerciseExecutions);
            }
        }while(max - min > 1);


        return parseMapOfExercisesToListOfExercises(list);
    }

    private SavedTraining parseMapOfExercisesToListOfExercises(List<Map<String, List<ExerciseExecutionPOJODB>>> list)
    {
        SavedTraining training = new SavedTraining(NOT_APPLICABLE, DEFAULT_BREAK_TIME);

        for(Map<String, List<ExerciseExecutionPOJODB>> trainingDay : list)
        {
            training.addDay(getTrainingForDay(trainingDay));
        }

        return training;
    }

    private ArrayList<ExerciseExecutionPOJODB> getTrainingForDay(Map<String, List<ExerciseExecutionPOJODB>> trainingDay)
    {
        Collection<List<ExerciseExecutionPOJODB>> exerciseExecutionCollection = trainingDay.values();
        ArrayList<ExerciseExecutionPOJODB> exerciseExecutionList = new ArrayList<>();
        for(List<ExerciseExecutionPOJODB> exerciseExecutionPOJODBS : exerciseExecutionCollection)
        {
            exerciseExecutionList.addAll(exerciseExecutionPOJODBS);
        }
        return exerciseExecutionList;
    }
}
