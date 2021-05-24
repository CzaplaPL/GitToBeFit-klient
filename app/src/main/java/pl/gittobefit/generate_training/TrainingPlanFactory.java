package pl.gittobefit.generate_training;

import android.content.Context;

public class TrainingPlanFactory
{

    public TrainingPlanGenerator createPlan(String trainingType, Context context) throws IllegalArgumentException
    {
        switch(trainingType.toUpperCase())
        {
            case "SPLIT":
                return new SplitTrainingPlan(context);
            case "FBW":
                return new FBWTrainingPlan(context);
            case "CARDIO":
                return new CardioTrainingPlan(context);
            case "FITNESS":
                return new FitnessTrainingPlan(context);
            default:
                throw new IllegalArgumentException("");
        }
    }
}
