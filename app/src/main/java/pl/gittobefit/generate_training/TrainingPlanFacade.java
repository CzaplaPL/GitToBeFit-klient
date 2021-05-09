package pl.gittobefit.generate_training;

import android.content.Context;

import pl.gittobefit.database.entity.training.SavedTraining;
import pl.gittobefit.database.entity.training.WorkoutForm;

public class TrainingPlanFacade {

    static public SavedTraining createTrainingPlan(WorkoutForm trainingForm,Context context) throws IllegalArgumentException, NotValidTrainingException, EquipmentCountException
    {
        TrainingPlanFactory trainingPlanFactory = new TrainingPlanFactory();
        if (trainingForm.getBodyParts().isEmpty()) throw new IllegalArgumentException("Body parts cannot be empty");

        TrainingPlanGenerator trainingPlanGenerator = trainingPlanFactory.createPlan(trainingForm.getTrainingType(),context);

        SavedTraining trainingPlan = trainingPlanGenerator.create(trainingForm);
        trainingPlan.setInfo(trainingForm.getTrainingType());
        trainingPlanGenerator.validate(trainingPlan,trainingForm);

        return trainingPlan;
    }
}