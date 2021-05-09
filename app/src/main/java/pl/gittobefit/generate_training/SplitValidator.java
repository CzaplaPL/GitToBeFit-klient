package pl.gittobefit.generate_training;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pl.gittobefit.WorkoutDisplay.objects.Training;
import pl.gittobefit.database.AppDataBase;
import pl.gittobefit.database.entity.training.Exercise;
import pl.gittobefit.database.entity.training.SavedTraining;
import pl.gittobefit.database.entity.training.WorkoutForm;
import pl.gittobefit.database.pojo.ExerciseExecutionPOJODB;

public class SplitValidator
{
    private final List<String> SMALL_BODY_PARTS = List.of("SIXPACK", "CALVES", "BICEPS", "TRICEPS", "SHOULDERS");
    private final List<String> BIG_BODY_PARTS = List.of("CHEST", "BACK", "THIGHS");
    private final int AMOUNT_FOR_SMALL = 3;
    private final int AMOUNT_FOR_BIG = 4;
    private AppDataBase base;

    public void validateTraining(SavedTraining trainingPlan, WorkoutForm trainingForm, AppDataBase base)
    {
        this.base = base;
        validateDaysCount(trainingForm);
        validateAmountOfExercises(prepare(trainingPlan));
    }

    private List<Map<String, List<ExerciseExecutionPOJODB>>> prepare(SavedTraining plan)
    {
        List<Map<String, List<ExerciseExecutionPOJODB>>> listToReturn = new ArrayList<>();

        for(ArrayList<ExerciseExecutionPOJODB> exerciseExecutions : plan.getPlanList())
        {
            Map<String, List<ExerciseExecutionPOJODB>> map = new HashMap<>();
            for(ExerciseExecutionPOJODB exerciseExecution : exerciseExecutions)
            {
                Exercise exercise = base.exerciseDao().getExercise(exerciseExecution.getExerciseId());
                map.put(exercise.getBodyPart(), new ArrayList<>());
            }
            for(ExerciseExecutionPOJODB exerciseExecution : exerciseExecutions)
            {
                Exercise exercise = base.exerciseDao().getExercise(exerciseExecution.getExerciseId());
                String name = exercise.getBodyPart();
                map.get(name).add(exerciseExecution);
            }
            listToReturn.add(map);
        }
        return listToReturn;
    }

    private void validateAmountOfExercises(List<Map<String, List<ExerciseExecutionPOJODB>>> trainingList)
    {
        Map<String, List<ExerciseExecutionPOJODB>> map = new HashMap<>();
        for(Map<String, List<ExerciseExecutionPOJODB>> stringListMap : trainingList)
        {
            map.putAll(stringListMap);
        }
        List<String> errors = new ArrayList<>();

        for(String bodyPart : map.keySet())
        {
            if(map.get(bodyPart).size() != getAmountOfExercisesForBodyPart(bodyPart))
            {
                errors.add(bodyPart);
            }
        }
        if(!errors.isEmpty())
        {
            throw new NotValidTrainingException(String.format("not enough exercises for: %s", errors.toString()));
        }
    }

    private int getAmountOfExercisesForBodyPart(String bodyPart)
    {
        if(SMALL_BODY_PARTS.contains(bodyPart))
        {
            return AMOUNT_FOR_SMALL;
        }else
            return AMOUNT_FOR_BIG;
    }

    private void validateDaysCount(WorkoutForm trainingForm)
    {
        if(trainingForm.getDaysCount() > trainingForm.getBodyParts().size())
        {
            throw new NotValidTrainingException("not enough days for set body parts");
        }
    }
}
