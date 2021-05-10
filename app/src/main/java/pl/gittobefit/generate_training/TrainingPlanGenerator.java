package pl.gittobefit.generate_training;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import pl.gittobefit.WorkoutDisplay.objects.Training;
import pl.gittobefit.database.AppDataBase;
import pl.gittobefit.database.entity.training.Exercise;
import pl.gittobefit.database.entity.training.SavedTraining;
import pl.gittobefit.database.entity.training.WorkoutForm;
import pl.gittobefit.database.pojo.ExerciseExecutionPOJODB;
import pl.gittobefit.workoutforms.adapters.WorkoutFormAdapter;
import pl.gittobefit.workoutforms.object.exercise.ExerciseItem;

public interface TrainingPlanGenerator
{
    int DEFAULT_BREAK_TIME = 30;
    int DEFAULT_CIRCUIT_COUNT = 3;
    int DEFAULT_SERIES_COUNT = 3;
    int DEFAULT_COUNT_OF_REPEATITIONS = 8;
    int DEFAULT_EXERCISE_TIME_EXECUTION = 30;
    int NOT_APPLICABLE = 0;

    SavedTraining create(WorkoutForm trainingForm);

    void validate(SavedTraining trainingPlan, WorkoutForm trainingForm) throws NotValidTrainingException;

    default List<Exercise> filterAllByAvailableEquipment(ArrayList<Exercise> exercises, ArrayList<Integer> availableEquipments, AppDataBase base)
    {
        ArrayList<Exercise> toReturn = new ArrayList<>();
        for(Exercise exercise : exercises)
        {
            List<Integer> needEquipment = base.exerciseDao().getNeededEquipment(exercise.getId());
            if(availableEquipments.containsAll(needEquipment))
            {
                toReturn.add(exercise);
            }
        }
        return toReturn;
    }

    default ExerciseExecutionPOJODB getExactExerciseExecution(Exercise exercise, WorkoutForm trainingForm) {
        String scheduleType = exercise.getScheduleType();
        return new ExerciseExecutionPOJODB(
                exercise,
                scheduleType.equalsIgnoreCase("REPEAT") ? NOT_APPLICABLE : DEFAULT_EXERCISE_TIME_EXECUTION,
                trainingForm.checkIfScheduleTypeIsCircuit() ? NOT_APPLICABLE : DEFAULT_SERIES_COUNT,
                scheduleType.equalsIgnoreCase("REPEAT") ? DEFAULT_COUNT_OF_REPEATITIONS : NOT_APPLICABLE
        );
    }
}
