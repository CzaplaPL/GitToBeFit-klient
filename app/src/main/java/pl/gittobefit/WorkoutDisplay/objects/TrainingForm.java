package pl.gittobefit.WorkoutDisplay.objects;

import java.util.ArrayList;

public class TrainingForm
{
    private ArrayList<Integer> equipmentIDs;
    private String trainingType;
    private ArrayList<String> bodyParts;
    private int daysCount;
    private String scheduleType;
    private int duration;

    public ArrayList<Integer> getEquipmentIDs()
    {
        return equipmentIDs;
    }

    public String getTrainingType()
    {
        return trainingType;
    }

    public ArrayList<String> getBodyParts()
    {
        return bodyParts;
    }

    public int getDaysCount()
    {
        return daysCount;
    }

    public String getScheduleType()
    {
        return scheduleType;
    }

    public int getDuration()
    {
        return duration;
    }

    public TrainingForm(ArrayList<Integer> equipmentIDs, String trainingType, ArrayList<String> bodyParts, int daysCount, String scheduleType, int duration)
    {
        this.equipmentIDs = equipmentIDs;
        this.trainingType = trainingType;
        this.bodyParts = bodyParts;
        this.daysCount = daysCount;
        this.scheduleType = scheduleType;
        this.duration = duration;
    }
}