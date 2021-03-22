package pl.gittobefit.workoutforms.object;


import java.util.ArrayList;

import pl.gittobefit.network.object.Training;

public class UserTrainings
{
    private static volatile UserTrainings INSTANCE;

    private ArrayList<Training> trainingArrayList = new ArrayList<>();

    static public UserTrainings getInstance()
    {
        if (INSTANCE == null)
        {
            synchronized (UserTrainings.class)
            {
                if (INSTANCE == null)
                {
                    INSTANCE = new UserTrainings();
                }
            }
        }
        return INSTANCE;
    }

    public void add(Training training)
    {
        trainingArrayList.add(training);
    }

    public ArrayList<Training> getTrainingArrayList() {
        return trainingArrayList;
    }

    public Training getTraining(int i)
    {
        return trainingArrayList.get(i);
    }

}
