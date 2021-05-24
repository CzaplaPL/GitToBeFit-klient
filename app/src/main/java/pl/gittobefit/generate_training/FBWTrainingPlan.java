package pl.gittobefit.generate_training;

import android.content.Context;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import pl.gittobefit.database.AppDataBase;
import pl.gittobefit.database.entity.training.Exercise;
import pl.gittobefit.database.entity.training.SavedTraining;
import pl.gittobefit.database.entity.training.WorkoutForm;
import pl.gittobefit.database.pojo.ExerciseExecutionPOJODB;

public class FBWTrainingPlan implements TrainingPlanGenerator
{
    private static final String TRAINING_TYPE = "FBW";
    private static final List<String> bodyPartsList = List.of("SIXPACK", "CALVES", "BICEPS", "TRICEPS", "SHOULDERS", "CHEST", "BACK", "THIGHS");
    private static final int ONE_DAY = 1;

    private List<Exercise> exercisesWithEquipment = new ArrayList<>();
    private WorkoutForm trainingForm;
    private WorkoutForm localTrainingForm;

    private AppDataBase base;

    public FBWTrainingPlan(Context context)
    {
        base = AppDataBase.getInstance(context);
    }

    private void initialize(WorkoutForm trainingForm)
    {
        this.trainingForm = trainingForm;
        this.localTrainingForm = trainingForm;

        List<Integer> exerciseIds = base.exerciseDao().getAllByTrainingTypes_Name(TRAINING_TYPE);

        ArrayList<Exercise> exerciseListFilteredByTrainingType = new ArrayList<>();
        for(Integer exerciseId : exerciseIds)
        {
            exerciseListFilteredByTrainingType.add(base.exerciseDao().getExercise(exerciseId));
        }

        exercisesWithEquipment = filterAllByAvailableEquipment(exerciseListFilteredByTrainingType, trainingForm.getEquipmentIDs(), base);

        if(trainingForm.getScheduleType().equals("REPETITIVE"))
            localTrainingForm.setDaysCount(ONE_DAY);
    }

    @Override
    public SavedTraining create(WorkoutForm trainingForm)
    {
        initialize(trainingForm);

        SavedTraining savedTraining = new SavedTraining(
                NOT_APPLICABLE,
                DEFAULT_BREAK_TIME
        );
        savedTraining.setPlanList(assignExercisesToBodyPart(exercisesWithEquipment));
        return savedTraining;
    }

    @Override
    public void validate(SavedTraining trainingPlan, WorkoutForm trainingForm) throws NotValidTrainingException
    {
        List<String> bodyPartsListCopy = new ArrayList<>(bodyPartsList);
        ArrayList<String> bodyParts = new ArrayList<>();
        for( ArrayList<ExerciseExecutionPOJODB> trainingDay : trainingPlan.getPlanList())
        {
            for(ExerciseExecutionPOJODB exerciseExecution : trainingDay)
            {
                Exercise exercise = base.exerciseDao().getExercise(exerciseExecution.getExerciseId());
                bodyParts.add(exercise.getBodyPart());
            }

            bodyPartsListCopy.removeAll(bodyParts);
            if(bodyPartsListCopy.size() != 0)
            {
                throw new NotValidTrainingException(String.format("not enough exercises for: %s",bodyPartsListCopy.toString()));
            }
        }
    }

    private ArrayList<ArrayList<ExerciseExecutionPOJODB>> assignExercisesToBodyPart(List<Exercise> exercisesWithEquipment)
    {

        ArrayList<ArrayList<ExerciseExecutionPOJODB>> planList = new ArrayList<>();

        Map<String, List<ExerciseExecutionPOJODB>> mapOfExercisesByBodyPartForDaysCount = getBodyPartExercisesForDays(exercisesWithEquipment);

        for(int i = 0; i < localTrainingForm.getDaysCount(); i++)
        {
            ArrayList<ExerciseExecutionPOJODB> exerciseExecutionList = new ArrayList<>();

            for(String bodyPart : bodyPartsList)
            {
                List<ExerciseExecutionPOJODB> exerciseExecutionsForBodyPart = mapOfExercisesByBodyPartForDaysCount.get(bodyPart);
                if(exerciseExecutionsForBodyPart.size() != 0)
                    exerciseExecutionList.add(exerciseExecutionsForBodyPart.get(i));
            }
            planList.add(exerciseExecutionList);
         
        }
        return planList;
    }

    private Map<String, List<ExerciseExecutionPOJODB>> getBodyPartExercisesForDays(List<Exercise> exercisesWithEquipment)
    {
        Map<String, List<ExerciseExecutionPOJODB>> exerciseExecutionMap = new HashMap<>();

        for(String bodyPart : bodyPartsList)
        {
            List<ExerciseExecutionPOJODB> exerciseExecutionList = new ArrayList<>();

            List<Exercise> exercisesWithEquipmentFilteredByBodyPart = getExercisesFilteredByBodyPart(exercisesWithEquipment, bodyPart);
            //making some randomizing
            Collections.shuffle(exercisesWithEquipmentFilteredByBodyPart);

            for(int i = 0; i < localTrainingForm.getDaysCount(); i++)
            {
                if(exercisesWithEquipmentFilteredByBodyPart.size() != 0)
                {
                    Exercise exercise;
                    if(isEnough(exercisesWithEquipmentFilteredByBodyPart))
                        exercise = exercisesWithEquipmentFilteredByBodyPart.remove(i);
                    else
                        exercise = exercisesWithEquipmentFilteredByBodyPart.get(
                                i % exercisesWithEquipmentFilteredByBodyPart.size()
                        );

                    exerciseExecutionList.add(getExactExerciseExecution(
                            exercise,
                            this.trainingForm
                    ));
                }
            }
            exerciseExecutionMap.put(bodyPart, exerciseExecutionList);
        }
        return exerciseExecutionMap;
    }

    private List<Exercise> getExercisesFilteredByBodyPart(List<Exercise> exercises, String bodyPart)
    {
        List<Exercise> toReturn = new ArrayList<>();
        for(Exercise exercise : exercises)
        {
            if(exercise.getBodyPart().equals(bodyPart))
                toReturn.add(exercise);
        }
        return toReturn;
    }

    private boolean isEnough(List<Exercise> exercises)
    {
        return exercises.size() >= localTrainingForm.getDaysCount();
    }
}
