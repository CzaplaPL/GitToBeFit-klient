package pl.gittobefit.generate_training;

import android.content.Context;

public class TrainingPlanFactory
{

    public TrainingPlanGenerator createPlan(String trainingType, Context context) throws IllegalArgumentException
    {
         switch (trainingType.toUpperCase()) {
            case "SPLIT" :
                return new SplitTrainingPlan(context);
                /* case "FBW" -> new FBWTrainingPlan();
            case "CARDIO" -> new CardioTrainingPlan();
            case "FITNESS" -> new FitnessTrainingPlan();*/
             default : throw new IllegalArgumentException("");
        }
    }
}
